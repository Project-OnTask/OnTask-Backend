package com.itsfive.back.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itsfive.back.exception.AppException;
import com.itsfive.back.model.HTMLMail;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.ApiResponse;
import com.itsfive.back.payload.JwtAuthenticationResponse;
import com.itsfive.back.payload.LoginRequest;
import com.itsfive.back.payload.MobileSignupRequest;
import com.itsfive.back.payload.MobileSignupResponse;
import com.itsfive.back.payload.SignUpRequest;
import com.itsfive.back.repository.RoleRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.security.JwtTokenProvider;
import com.itsfive.back.service.MailSenderService;
import com.itsfive.back.service.UserService;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.verify.VerifyClient;
import com.nexmo.client.verify.VerifyRequest;
import com.nexmo.client.verify.VerifyResponse;
import com.nexmo.client.verify.VerifyStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;
	
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    public UserService userService;
    
    @Autowired
    public MailSenderService mailSenderService;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
    private MailSenderService senderService;

    @PostMapping("/auth/signin")
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
    
    @PostMapping("/auth/signin/mobile")
    public ResponseEntity<?> authenticateMobileUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateTokenForMobile(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/auth/signup")
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
        
        String token = UUID.randomUUID().toString();
        user.setConfirmMailToken(token);
        User result = userRepository.save(user);
     
   	 String content = "<html>" +
                "<body>" +
                "<p>Hello "+ user.getFName()+",</p>" +
                "<p>Click <a href=\"http://localhost:3000/login/?token="+token+"\">here</a> to confirm your email.</p>" +
            "</body>" +
        "</html>";
   	 
   	 HTMLMail htmlMail = new HTMLMail(user.getEmail(),"OnTask - Confirm email",content);
   	 
   	 senderService.sendHTMLMail(htmlMail);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    @PostMapping("/auth/signup/mobile")
    public ResponseEntity<?> registerMobileUser(@Valid @RequestBody MobileSignupRequest mobileSignupRequest) throws IOException, NexmoClientException {
        if(userRepository.existsByMobile(mobileSignupRequest.getMobile())) {
            return new ResponseEntity(new ApiResponse(false, "This mobile number is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

//        NexmoClient client = NexmoClient.builder()
//        		  .apiKey("0ff50012")
//        		  .apiSecret("egcSxdEkwH9Vgcdf")
//        		  .build();
//        
//        VerifyClient verifyClient = client.getVerifyClient();
//        
//        VerifyRequest request = new VerifyRequest(mobileSignupRequest.getMobile(), "Nexmo");
//        request.setLength(6);
//
//        VerifyResponse response = verifyClient.verify(request);
        
   	 User user = new User(mobileSignupRequest.getFName(), mobileSignupRequest.getMobile());
   	 
//        if (response.getStatus() == VerifyStatus.OK) {
//      
//        } 
//        else {
//        	throw new AppException("Error occured with Nexmo");
//        }
   	 	user.setPassword(passwordEncoder.encode(mobileSignupRequest.getMobile()));
   	 	user.setEmail(mobileSignupRequest.getMobile());
        User result = userRepository.save(user);  
        		
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{mobile}")
                .buildAndExpand(result.getMobile()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
        //return ResponseEntity.created(location).body(new MobileSignupResponse(result.getId(),response.getRequestId(),true, "User registered successfully"));
    }
    
    @GetMapping("/auth/mobile/signin/{text}")
    public byte[] getQRCodeImage(@PathVariable String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100);
        
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray(); 
        return pngData;
    }
    
}
