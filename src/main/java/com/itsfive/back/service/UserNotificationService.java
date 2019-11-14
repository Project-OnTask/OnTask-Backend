package com.itsfive.back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itsfive.back.config.PusherConfig;
import com.itsfive.back.exception.UserNotFoundException;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.User;
import com.itsfive.back.model.UserNotification;
import com.itsfive.back.payload.UnseenNotification;
import com.itsfive.back.repository.UserNotificationRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.security.CurrentUser;
import com.itsfive.back.security.UserPrincipal;

@Service
public class UserNotificationService {
	@Autowired
	private GroupMemberService groupMemberService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserNotificationRepository userNotificationRepository;
	
	ObjectMapper objectMapper = new ObjectMapper();
	JavaTimeModule module = new JavaTimeModule();
	
	public void createUserNotificationsForGroupMembers(Long groupId,GroupActivity activity) throws JsonProcessingException {
		List<GroupMember> members = groupMemberService.getGroupMembersForNotifications(groupId);
		for (GroupMember member : members)  
        { 
			if(member.getUser() != null && !member.getUser().equals(activity.getUser())  ) {
				UserNotification notification = new UserNotification(activity,member.getUser());
				userNotificationRepository.save(notification);
				PusherConfig.setObj().trigger("user_"+member.getUser().getId(), "user_notification",objectMapper.writeValueAsString(activity));
			}
        } 
	}
	
	public void createUserNotificationsForGroupAdmins(Long groupId,GroupActivity activity) throws JsonProcessingException {
		List<GroupMember> admins = groupMemberService.getGroupAdminsForNotifications(groupId);
		for (GroupMember member : admins)  
        { 
			if(member.getUser() != null && member.getUser() != activity.getUser()) {
				UserNotification notification = new UserNotification(activity,member.getUser());
				userNotificationRepository.save(notification);
				PusherConfig.setObj().trigger("user_"+member.getUser().getId(), "user_notification",objectMapper.writeValueAsString(activity));
			}
        } 
	}
	
	public List<UnseenNotification> getUnseenNotifications(Long userId) {
		List<UserNotification> notis =  userNotificationRepository.findByUserAndSeen(userRepository.findById(userId).get(), false); 
		List<UnseenNotification> notifications = new ArrayList<UnseenNotification>();
		for(UserNotification notification: notis) {
			notifications.add(new UnseenNotification(notification.getId(),notification.getGroupActivity())); 
		}
		return notifications;
	}
	
	public void markAsSeen(Long nid) {
		UserNotification note = userNotificationRepository.findById(nid).get();
		note.setSeen(true); 
		userNotificationRepository.save(note);
	}
	
	public void createUserNotification(User user,GroupActivity activity) {
		UserNotification notification = new UserNotification(activity,user);
		userNotificationRepository.save(notification);
	}
	
	public void publishToGroupActivity(long groupId,GroupActivity act) throws JsonProcessingException {
		PusherConfig.setObj().trigger("group_"+groupId, "new_activity",objectMapper.writeValueAsString(act));
	}
}
