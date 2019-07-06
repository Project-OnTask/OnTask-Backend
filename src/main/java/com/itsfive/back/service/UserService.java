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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itsfive.back.payload.VerifyMobileRequest;
import com.itsfive.back.exception.BadRequestException;
import com.itsfive.back.model.PasswordResetToken;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.PasswordResetRequest;
import com.itsfive.back.payload.UpdateEmailRequest;
import com.itsfive.back.payload.UpdatePasswordRequest;
import com.itsfive.back.payload.UploadFileResponse;
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
    public FileService fileService;
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    
	@Value("${ACCOUNT_SID}")
	private String ACCOUNT_SID;
	
	@Value("${AUTH_TOKEN}")
	private String AUTH_TOKEN;
	
	@Value("${VERIFY_SERVICE_SID}")
	private String SERVICE_SID;

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

	 public void editProPic(MultipartFile file,long userId) {
		 String fileName = fileService.storeFile(file);
	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                 .path("/api/downloadFile/")
	                 .path(fileName)
	                 .toUriString();

	         UploadFileResponse response =  new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
	         User user = userRepository.findById(userId).get();
	         user.setProPicURL(response.getFileDownloadUri());
	         userRepository.save(user);
	 }
	 
	 public String getProPicURL(Long id){
			return userRepository.findById(id).get().getProPicURL();
	 }
	 
	 public void editCover(MultipartFile file,long userId) {
		 String fileName = fileService.storeFile(file);
	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                 .path("/api/downloadFile/")
	                 .path(fileName)
	                 .toUriString();

	         UploadFileResponse response =  new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
	         User user = userRepository.findById(userId).get();
	         user.setCoverURL(response.getFileDownloadUri());
	         userRepository.save(user);
	 }
	 
	 public String getCoverURL(Long id){
			return userRepository.findById(id).get().getCoverURL();
	 }
}
