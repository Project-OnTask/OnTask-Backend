package com.itsfive.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsfive.back.payload.UnseenNotification;
import com.itsfive.back.service.UserNotificationService;

@RestController
@RequestMapping("/api")
public class UserNotificationController {
    @Autowired
    private UserNotificationService userNotificationService;
    
	 @GetMapping("/user/{userId}/u_notifications")
	 public List<UnseenNotification> getUnseenNotifications(@PathVariable long userId) { 
		 return userNotificationService.getUnseenNotifications(userId); 
	 }
	 
	 @PostMapping("/notifications/{id}/seen")
	 public void markNotificationAsSeen(@PathVariable long id) {
		 userNotificationService.markAsSeen(id);
	 }
}
