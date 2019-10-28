package com.itsfive.back.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsfive.back.exception.AppException;
import com.itsfive.back.exception.BadRequestException;
import com.itsfive.back.exception.UserNotFoundException;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.HTMLMail;
import com.itsfive.back.model.Task;
import com.itsfive.back.model.TaskAsignee;
import com.itsfive.back.model.User;
import com.itsfive.back.model.UserEducation;
import com.itsfive.back.model.UserWork;
import com.itsfive.back.payload.AddEducationRequest;
import com.itsfive.back.payload.AddWorkplaceRequest;
import com.itsfive.back.payload.BasicUserInfoUpdateReq;
import com.itsfive.back.payload.GetProPicResponse;
import com.itsfive.back.payload.GetUserResponse;
import com.itsfive.back.payload.PasswordResetRequest;
import com.itsfive.back.payload.TaskAsigneeResponse;
import com.itsfive.back.payload.UpdateEmailRequest;
import com.itsfive.back.payload.UpdatePasswordRequest;
import com.itsfive.back.payload.UploadFileResponse;
import com.itsfive.back.payload.UserContactUpdateRequest;
import com.itsfive.back.payload.UserWebPresenceUpdateReq;
import com.itsfive.back.payload.VerifyMobileRequest;
import com.itsfive.back.repository.GroupMemberRepository;
import com.itsfive.back.repository.TaskAsigneeRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.UserEducationRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.repository.UserWorkRepository;
import com.itsfive.back.security.CurrentUser;
import com.itsfive.back.security.UserPrincipal;
import com.itsfive.back.service.FileService;
import com.itsfive.back.service.GroupMemberService;
import com.itsfive.back.service.MailSenderService;
import com.itsfive.back.service.UserNotificationService;
import com.itsfive.back.service.UserService;
import com.nexmo.client.NexmoClientException;

import io.jsonwebtoken.lang.Collections;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    public UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GroupMemberService groupMemberService;
    
    
    @Autowired
    private TaskAsigneeRepository taskAsRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private MailSenderService senderService;
    
    @Autowired
    private UserWorkRepository userWorkRepository;
    
    @Autowired
    private UserEducationRepository userEducationRepository;
    
	@GetMapping("/auth/user/me")
	public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
			User CurrentUserSummary = new User();
	    	if(currentUser == null) {
	    		throw new UserNotFoundException("Not logged");
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
	 public boolean checkToken(@PathVariable String token) {
		 return userService.ifResetTokenValid(token);
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
	 
	 @PostMapping("/user/{userId}/change-propic")
	 public void updateProfilePic(@PathVariable long userId,@RequestParam("file") MultipartFile file) {
		userService.editProPic(file, userId); 
	 }
	 
	 @GetMapping("/user/{id}/pro-pic")
	 public GetProPicResponse getProPic(@PathVariable long id) {
	    return userService.getProPic(id);
	 }
	 
	 @PostMapping("/auth/verify/mobile")
	 public void verifyMobile(@RequestBody VerifyMobileRequest verifyPhoneReq) throws IOException, NexmoClientException {
		 userService.verifyMobile(verifyPhoneReq);
	 }
	 
	 @PostMapping("/auth/verify/email/{token}")
	 public void verifyEmail(@PathVariable String token) {
		 Optional<User> user = userRepository.findByConfirmMailToken(token);
		 if(!user.isPresent()) {
				throw new BadRequestException("Invalid request");
		 }
		 User usr = user.get();
		 usr.setMailEnabled(true);
		 usr.setConfirmMailToken("");
		 userRepository.save(usr);
	 }
	 
	 //Get users who are not a specific group
	 @GetMapping("/users/search/{groupId}/{query}")
	 public Stream<Object> searchUsers(@PathVariable("groupId") long groupId,@PathVariable("query") String query){
		 List<User> matches = userRepository.findByEmailContaining(query);
		 List<User> m_matches = userRepository.findByMobileContaining(query);
		 List<User> groupmembers = groupMemberService.getGroupMembersAsUsers(groupId);
		 
		 matches.addAll(m_matches);
		 
		 matches = matches.stream()
         .filter(match -> !groupmembers.contains(match))
         .collect(Collectors.toList());
		 
		 return matches.stream().map(user -> new GetUserResponse(
				 user.getId(),
				 user.getFName(),
				 user.getLname(),
				 user.getEmail(),
				 user.getProPicURL(),
				 user.getEmailHash()));
	 }
	 
	 @GetMapping("/users/{userId}")
	 public User getUser(@PathVariable long userId) {
		 return userRepository.findById(userId).get();
	 }
	 
	 @GetMapping("/users/exist/{username}")
	 public boolean isUsernameExists(@PathVariable String username) {
		 return !userRepository.existsByUsername(username);
	 }
	 
	 @GetMapping("/user/{userId}/tasks")
	    public List<Task> getAsignedTasks(@PathVariable Long userId) throws JsonProcessingException{ 
	    	List<TaskAsignee> asTasks = taskAsRepository.findAllByIdUserId(userId);
	    	List<Task> ids = new ArrayList<Task>();
	    	for(int i=0;i<asTasks.size();i++){  
	            Long tid = asTasks.get(i).getId().getTaskId();
	            Task task = taskRepository.findById(tid).get();
	            ids.add(task);
	        }  
	    	return ids;
	    }
	 
	 @PostMapping("/user/{userId}/basic-info")
	 public void updateBasicUserInfo(@PathVariable Long userId,@RequestBody BasicUserInfoUpdateReq upReq){ 
		 User user = userRepository.findById(userId).get();
		 if(upReq.getFname() != null) {
			 user.setFName(upReq.getFname());
		 }
		 if(upReq.getLname() != null) {
			 user.setLname(upReq.getLname());
		 }
		 if(upReq.getUsername() != null && !userRepository.existsByUsername(upReq.getUsername())) {
			 user.setUsername(upReq.getUsername()); 
		 }
		 if(upReq.getBio() != null) {
			 user.setBio(upReq.getBio());
		 }
		 userRepository.save(user); 
	 }
	 
	 @PostMapping("/user/{userId}/web-presence")
	 public void updateUserWebPresence(@PathVariable Long userId,@RequestBody UserWebPresenceUpdateReq upReq){ 
		 User user = userRepository.findById(userId).get();
		 if(user == null) {
			 throw new AppException("No user");
		 }
		 
		 if(upReq.getWebsiteLink() != null) {
			 user.setWebsiteLink(upReq.getWebsiteLink());
		 }
		 if(upReq.getTwitterLink() != null) {
			 user.setTwitterLink(upReq.getTwitterLink());
		 }
		 if(upReq.getStackOverflowLink() != null) {
			 user.setStackOverflowLink(upReq.getStackOverflowLink()); 
		 }
		 if(upReq.getGithubLink() != null) {
			 user.setGithubLink(upReq.getGithubLink());
		 }
		 if(upReq.getLinkedInLink() != null) {
			 user.setLinkedInLink(upReq.getLinkedInLink());
		 }
		 
		 userRepository.save(user); 
	 }

	 @PostMapping("/user/contact")
	 public void updateContactInfo(@RequestBody UserContactUpdateRequest req) {
		 User user = userRepository.findById(req.getUserId()).get();
		 if(req.getMobile() != null) {
			 user.setMobile(req.getMobile());
		 }
		 if(req.getEmail() != null) {
			 user.setEmail(req.getEmail()); 
		 }
		 userRepository.save(user);
	 }
	 
	 @PostMapping("/user/work")
	 public void addNewWork(@RequestBody AddWorkplaceRequest addWorkReq) {
		 User user = userRepository.findById(addWorkReq.getUserId()).get();
		 UserWork work = new UserWork(user,addWorkReq.getTitle(),addWorkReq.getCompany(),addWorkReq.getFrom(),addWorkReq.getTo(),addWorkReq.getDescription(),addWorkReq.isWorking());
		 userWorkRepository.save(work);
	 }
	 
	 @PostMapping("/user/education")
	 public void addNewEducation(@RequestBody AddEducationRequest addEduReq) {
		 User user = userRepository.findById(addEduReq.getUserId()).get();
		 UserEducation edu = new UserEducation(user,addEduReq.getInstitute(),addEduReq.getFrom(),addEduReq.getTo(),addEduReq.isStudying());
		 userEducationRepository.save(edu);
	 }
	 
	 @GetMapping("/users/{userId}/education")
	 public List<UserEducation> getUserEducation(@PathVariable long userId) {
		 User user = userRepository.findById(userId).get();
		 return userEducationRepository.findByUser(user);
	 }
	 
	 @GetMapping("/users/{userId}/work")
	 public List<UserWork> getUserWork(@PathVariable long userId) {
		 User user = userRepository.findById(userId).get();
		 return userWorkRepository.findByUser(user);
	 }
}
