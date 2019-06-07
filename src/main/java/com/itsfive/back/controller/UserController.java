package com.itsfive.back.controller;

import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsfive.back.exception.UserNotFoundException;
import com.itsfive.back.model.HTMLMail;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.PasswordResetRequest;
import com.itsfive.back.payload.UpdateEmailRequest;
import com.itsfive.back.payload.UpdatePasswordRequest;
import com.itsfive.back.security.CurrentUser;
import com.itsfive.back.security.UserPrincipal;
import com.itsfive.back.service.MailSenderService;
import com.itsfive.back.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    public UserService userService;
    
    @Autowired
    private MailSenderService senderService;
    
	@GetMapping("/auth/user/me")
	public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
			User CurrentUserSummary = new User();
	    	if(currentUser.getId() ==  null) {
	    		throw new UserNotFoundException("user");
	    	}
	    	else {
	    		CurrentUserSummary.setId(currentUser.getId());  
		        CurrentUserSummary.setFName(currentUser.getFName());
	    	}
	        return CurrentUserSummary;
	  }
	 
	@PostMapping("/auth/{email}/genToken")
	public void generateResetToken( @PathVariable String email) throws MessagingException {
	    	Optional<User> user = userService.getUserByEmail(email);
	    	if(!user.isPresent()) {
	    		throw new UserNotFoundException(email);
	    	}
	    	 String token = UUID.randomUUID().toString();
	    	 userService.createResetTokenForUser(user.get(), token);
	    	 
	    	 String content = "<html>" +
	                 "<body>" +
	                 "<p>Hello "+ user.get().getFName()+",</p>" +
	                 "<p>Click <a href=\"http://localhost:3000/reset-password/?token="+token+"\">here</a> to reset your password</p>" +
	             "</body>" +
	         "</html>";
	    	 
	    	 HTMLMail htmlMail = new HTMLMail(user.get().getEmail(),"OnTask - reset password",content);
	    	 
	    	 senderService.sendHTMLMail(htmlMail);
	    }
	 
	 @PostMapping("/auth/check-token/{token}")
	 public void checkToken(@PathVariable String token) {
		 userService.ifResetTokenValid(token);
	 }
	 
	 @PostMapping("/auth/reset-pwd")
	 public void resetPwd(@RequestBody PasswordResetRequest resetReq) {
		 userService.resetPassword(resetReq);;
	 }
	 
	 @PostMapping("/user/change-email")
	 public void updateEmail(@RequestBody UpdateEmailRequest updateEmailReq) {
		userService.updateEmail(updateEmailReq);
	 }
	 
	 @PostMapping("/user/change-pwd")
	 public void updatePassword(@RequestBody UpdatePasswordRequest updatePwdReq) {
		userService.updatePassword(updatePwdReq);
	 }
}
