package com.itsfive.back.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itsfive.back.exception.BadRequestException;
import com.itsfive.back.model.PasswordResetToken;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.PasswordResetRequest;
import com.itsfive.back.payload.UpdateEmailRequest;
import com.itsfive.back.payload.UpdatePasswordRequest;
import com.itsfive.back.repository.PasswordResetTokenRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private Environment env;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
	
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void createResetTokenForUser(User user,String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
		passwordResetTokenRepository.save(passwordResetToken);
	}
	
	public boolean ifResetTokenValid(String token) {
		Optional<PasswordResetToken> tokenRecord = passwordResetTokenRepository.findByToken(token);
		if(!tokenRecord.isPresent()) {
			throw new BadRequestException("Invalid request");
		}
		Date today = new Date();
		if( today.after(tokenRecord.get().getExpiryDate()) ) {
			throw new BadRequestException("This token has expired");
		}
		return true;
	}
	
	public void resetPassword(PasswordResetRequest pwdResetRequest) {
		if(ifResetTokenValid(pwdResetRequest.getToken())) {
			Optional<PasswordResetToken> tokenRecord = passwordResetTokenRepository.findByToken(pwdResetRequest.getToken());
			User user = tokenRecord.get().getUser();
			user.setPassword(passwordEncoder.encode(pwdResetRequest.getPassword()));
			userRepository.save(user);
		}
	}
	
	public void updateEmail(UpdateEmailRequest updateEmailReq) {
		Optional<User> user = userRepository.findById(updateEmailReq.getId());
		if(!user.isPresent()) {
			throw new BadRequestException("Specified User was not found");
		}
		
		User updatedUser = user.get();
		updatedUser.setEmail(updateEmailReq.getEmail());
		userRepository.save(updatedUser);
	}

	public void updatePassword(UpdatePasswordRequest updatePwdReq) {
		Optional<User> user = userRepository.findById(updatePwdReq.getUserId());
		if(!user.isPresent()) {
			throw new BadRequestException("There is no user for the provided id");
		}
		if(!passwordEncoder.matches(updatePwdReq.getPassword(), user.get().getPassword())) {
			throw new BadRequestException("Password is incorrect");
		}
		User updatedUser = user.get();
 		updatedUser.setPassword(passwordEncoder.encode(updatePwdReq.getNewPassword()));
 		userRepository.save(updatedUser);
	}
}
