package com.itsfive.back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itsfive.back.config.PusherConfig;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.GroupInvite;
import com.itsfive.back.model.GroupInviteKey;
import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.GetGroupAdminResponse;
import com.itsfive.back.payload.GetGroupMembersResponse;
import com.itsfive.back.payload.GetUserResponse;
import com.itsfive.back.repository.GroupInviteRepository;
import com.itsfive.back.repository.GroupMemberRepository;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.payload.ManageMembersRequest;
import com.itsfive.back.repository.UserRepository;

@Service
public class GroupMemberService {
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	ObjectMapper objectMapper = new ObjectMapper();
	JavaTimeModule module = new JavaTimeModule();
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserNotificationService userNotificationService;
	
	@Autowired
	private GroupInviteRepository groupInviteRepository;
	
	@Autowired
	private GroupActivityService groupActivityService;
	
    @Autowired
    private MailSenderService senderService;
    
	public GroupMember addMemberByToken(String itoken,long userId) throws JsonProcessingException {
		objectMapper.registerModule(module);
		GroupInvite invite = groupInviteRepository.findByIdItoken(itoken);
		GroupMembersKey mkey = new GroupMembersKey(userId,invite.getId().getGroupId());
		GroupMember groupMember = new GroupMember(mkey);
		groupMember.setRole("member");
		groupMemberRepository.save(groupMember);
		groupInviteRepository.delete(invite);
		User user = userRepository.findById(mkey.getUserId()).get();
		
		GroupActivity act = groupActivityService.addGroupActivity(invite.getId().getGroupId(),user,"<b>"+user.getFName()+"</b> was added to group <b>"+
		groupRepository.findById(mkey.getGroupId()).get().getName()+"</b>"); 
		userNotificationService.createUserNotificationsForGroupMembers(invite.getId().getGroupId(), act);
		PusherConfig.setObj().trigger("group_"+mkey.getGroupId(), "new_activity",objectMapper.writeValueAsString(act));
		return groupMember;
	}
	
	public void addMember(GroupMember member, long addedById) throws JsonProcessingException {
		objectMapper.registerModule(module);
		User user = userRepository.findById(member.getId().getUserId()).get(); 
		User addedBy = userRepository.findById(addedById).get();
		member.setRole("member"); 
		groupMemberRepository.save(member);
		
		GroupActivity act = groupActivityService.addGroupActivity(member.getId().getGroupId(),addedBy,"<b>"+addedBy.getFName()+"</b> added <b>"+user.getFName()+"</b> to group <b>"+
		groupRepository.findById(member.getId().getGroupId()).get().getName()+"</b>"); 
		userNotificationService.createUserNotificationsForGroupMembers(member.getId().getGroupId(), act);
		userNotificationService.createUserNotification(user, act);
		PusherConfig.setObj().trigger("user_"+member.getId().getUserId(), "group_add",objectMapper.writeValueAsString(act));
		PusherConfig.setObj().trigger("group_"+member.getId().getGroupId(), "new_activity",objectMapper.writeValueAsString(act));
	}
	
	public void addAdmin(GroupMember admin) {
		groupMemberRepository.save(admin);
	}
	
	public void setMemberAdmin(ManageMembersRequest memberKey) throws JsonProcessingException { 
		GroupMember member = groupMemberRepository.findByUserIdAndGroupId(memberKey.getUserId(),memberKey.getGroupId()).get();
		member.setRole("admin");
		groupMemberRepository.save(member);
		User user = userRepository.findById(memberKey.getUserId()).get();
		User addedBy = userRepository.findById(memberKey.getAddedById()).get();
		String description = "<b>"+addedBy.getFName()+"</b> made <b>"+user.getFName()+"</b> an admin";
		groupActivityService.addGroupActivity(memberKey.getGroupId(), addedBy, description);
	}
	
	public void removeMemberAdmin(ManageMembersRequest memberKey) throws JsonProcessingException {
		GroupMember member = groupMemberRepository.findByUserIdAndGroupId(memberKey.getUserId(),memberKey.getGroupId()).get();
		member.setRole("member");
		groupMemberRepository.save(member); 
		User user = userRepository.findById(memberKey.getUserId()).get();
		User addedBy = userRepository.findById(memberKey.getAddedById()).get();
		String description = "<b>"+user.getFName()+"</b>'s admin privileges were revoked";
		groupActivityService.addGroupActivity(memberKey.getGroupId(), addedBy, description);
	}
	
	public List<GroupMember> getGroupsByMember(Long userId) {
		return groupMemberRepository.findAllByUserId(userId);
	}
	
	public boolean isMemberAnAdmin(Long userId,Long groupId) {
		GroupMember member = groupMemberRepository.findByUserIdAndGroupId(userId, groupId).get();
		return member.getRole().equals("admin");
	}
	
	public Stream<Object> getGroupAdmins(long groupId){
		return groupMemberRepository.findAllByGroupIdAndRole(groupId,"admin").stream().map(
			member -> new GetGroupAdminResponse(member.getUser().getId(),member.getUser().getFName(),member.getUser().getLname(),member.getUser().getEmailHash(),member.getUser().getProPicURL())
				);
	}
	
	public Stream<Object> getGroupMembers(long groupId){
		return groupMemberRepository.findAllByGroupIdAndRole(groupId,"member").stream().map(
			member -> new GetGroupAdminResponse(member.getUser().getId(),member.getUser().getFName(),member.getUser().getLname(),member.getUser().getEmailHash(),member.getUser().getProPicURL())
				);
	}
	
	public Stream<Object> getAllGroupMembers(long groupId){
		return groupMemberRepository.findAllByGroupId(groupId).stream().map(
			member -> new GetGroupMembersResponse(member.getUser().getId(),member.getUser().getFName(),member.getUser().getLname(),member.getUser().getProPicURL(),member.getRole())
				);
	}
	
	public List<User> getGroupMembersAsUsers(long groupId){
		List<GroupMember> members =  groupMemberRepository.findAllByGroupId(groupId);
		List <User> users = new ArrayList<User>();
		for(GroupMember member: members) {
			users.add(member.getUser());
		}
		return users;
	}
	
	public List<GroupMember>getGroupMembersForNotifications(long groupId){
		return groupMemberRepository.findAllByGroupId(groupId);
	}
	
	public List<GroupMember>getGroupAdminsForNotifications(long groupId){
		return groupMemberRepository.findAllByGroupIdAndRole(groupId,"admin");
	}
	
	public GroupInvite createInviteLink(long userId,long groupId) {
		User createdBy = userRepository.findById(userId).get();
		String token = UUID.randomUUID().toString();
		GroupInviteKey inv = new GroupInviteKey(groupId,token);
		GroupInvite invite = new GroupInvite(inv,createdBy);
		groupInviteRepository.save(invite); 
		return invite;
	}
	
	public void removeMember(long userId,long groupId,long deletedById) throws JsonProcessingException {
		GroupMember member = groupMemberRepository.findByUserIdAndGroupId(userId, groupId).get();
		groupMemberRepository.delete(member); 
		User user = userRepository.findById(userId).get();
		Group group = groupRepository.findById(groupId).get();
		User deletedBy = userRepository.findById(deletedById).get();
		String description = "<b>"+deletedBy.getFName()+"</b> removed <b>"+user.getFName()+"</b> in group <b>"+group.getName()+"</b>";
		groupActivityService.addGroupActivity(groupId, deletedBy, description);
	}
	
	public Stream<Object> searchGroupMembers(long groupId,String query){
		 List<GroupMember> matches = groupMemberRepository.findAllByGroupId(groupId);
		 List<User> musers = new ArrayList<>();
		 
		 for(GroupMember match:matches) {
			 User usr = userRepository.findById(match.getId().getUserId()).get();
			 musers.add(usr);
		 }
		 List<User> fil1 = userRepository.findByEmailContaining(query); 
		 List<User> fil2 = userRepository.findByMobileContaining(query);
		 fil1.addAll(fil2); 
		 musers.retainAll(fil1);
		 return musers.stream().map(user -> new GetUserResponse(
				 user.getId(),
				  user.getFName(), 
				  user.getLname(),
				 user.getEmail(),
				 user.getProPicURL(),
				 user.getEmailHash()));
	}

}
