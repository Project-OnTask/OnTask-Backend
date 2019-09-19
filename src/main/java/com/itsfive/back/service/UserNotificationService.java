package com.itsfive.back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.User;
import com.itsfive.back.model.UserNotification;
import com.itsfive.back.payload.UnseenNotification;
import com.itsfive.back.repository.UserNotificationRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class UserNotificationService {
	@Autowired
	private GroupMemberService groupMemberService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserNotificationRepository userNotificationRepository;
	
	public void createUserNotificationsForGroupMembers(Long groupId,GroupActivity activity) {
		List<GroupMember> members = groupMemberService.getGroupMembersForNotifications(groupId);
		for (GroupMember member : members)  
        { 
			if(member.getUser() != null) {
				UserNotification notification = new UserNotification(activity,member.getUser());
				userNotificationRepository.save(notification);
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
}
