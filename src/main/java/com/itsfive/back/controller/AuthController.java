package com.itsfive.back.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itsfive.back.exception.AppException;
import com.itsfive.back.model.Roles;
import com.itsfive.back.model.RoleName;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.ApiResponse;
import com.itsfive.back.payload.JwtAuthenticationResponse;
import com.itsfive.back.payload.LoginRequest;
import com.itsfive.back.payload.MobileSignupRequest;
import com.itsfive.back.payload.SignUpRequest;
import com.itsfive.back.repository.RoleRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.security.CurrentUser;
import com.itsfive.back.security.JwtTokenProvider;
import com.itsfive.back.security.UserPrincipal;
import com.itsfive.back.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    public UserService userService;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws AddressException, MessagingException, IOException {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getFName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        /*Roles userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole)); */
        
        User result = userRepository.save(user);
        userService.sendmail(signUpRequest.getEmail(),"Confirm your email");
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    @PostMapping("/signup/mobile")
    public ResponseEntity<?> registerMobileUser(@Valid @RequestBody MobileSignupRequest mobileSignupRequest) throws IOException {
        if(userRepository.existsByMobile(mobileSignupRequest.getMobile())) {
            return new ResponseEntity(new ApiResponse(false, "This mobile is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(mobileSignupRequest.getFName(), mobileSignupRequest.getMobile());
        
        User result = userRepository.save(user);
// Sending sms to provided mobile number
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{mobile}")
                .buildAndExpand(result.getMobile()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    @RequestMapping("/mobile/signin/{text}")
    public byte[] getQRCodeImage(@PathVariable String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100);
        
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray(); 
        return pngData;
    }

    @GetMapping("/user/me")
    public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User userSummary = new User(currentUser.getId(),currentUser.getFName());
        return userSummary;
    }
}
