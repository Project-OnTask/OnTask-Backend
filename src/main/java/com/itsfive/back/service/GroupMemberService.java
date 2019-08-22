package com.itsfive.back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.exception.AppException;
import com.itsfive.back.model.GroupInvite;
import com.itsfive.back.model.GroupInviteKey;
import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
import com.itsfive.back.model.HTMLMail;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.GetGroupAdminResponse;
import com.itsfive.back.payload.GetGroupMembersResponse;
import com.itsfive.back.payload.GetUserResponse;
import com.itsfive.back.repository.GroupInviteRepository;
import com.itsfive.back.repository.GroupMemberRepository;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class GroupMemberService {
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private GroupInviteRepository groupInviteRepository;
	
    @Autowired
    private MailSenderService senderService;
    
	public GroupMember addMember(String itoken,long userId) {
		GroupInvite invite = groupInviteRepository.findByIdItoken(itoken);
		GroupMembersKey mkey = new GroupMembersKey(userId,invite.getId().getGroupId());
		GroupMember groupMember = new GroupMember(mkey);
		groupMember.setRole("member");
		groupMemberRepository.save(groupMember);
		groupInviteRepository.delete(invite);
		return groupMember;
	}
	
	public void addMemberAtGroupInit(GroupMember member) {
		groupMemberRepository.save(member);
	}
	
	public void addAdmin(GroupMember admin) {
		groupMemberRepository.save(admin);
	}
	
	public void setMemberAdmin(GroupMember groupMember) {
		groupMember.setRole("admin");
		groupMemberRepository.save(groupMember);
	}
	
	public void removeMemberAdmin(GroupMember groupMember) {
		groupMember.setRole("member");
		groupMemberRepository.save(groupMember);
	}
	
	public List<GroupMember> getGroupsByMember(Long userId) {
		return groupMemberRepository.findAllByUserId(userId);
	}
	
	public boolean isMemberAnAdmin(Long userId,Long groupId) {
		GroupMember member = groupMemberRepository.findByUserIdAndGroupId(userId, groupId).get();
		return member.getRole().equals("admin");
	}
	
	public Stream<Object> getGroupAdmins(long groupId){
		return groupMemberRepository.findByGroupIdAndRole(groupId,"admin").stream().map(
			member -> new GetGroupAdminResponse(member.getUser().getFName(),member.getUser().getLname(),member.getUser().getProPicURL())
				);
	}
	
	public Stream<Object> getGroupMembers(long groupId){
		return groupMemberRepository.findAllByGroupId(groupId).stream().map(
			member -> new GetGroupMembersResponse(member.getUser().getId(),member.getUser().getFName(),member.getUser().getLname(),member.getUser().getProPicURL(),member.getRole())
				);
	}
	
	public GroupInvite createInviteLink(long userId,long groupId) {
		User createdBy = userRepository.findById(userId).get();
		String token = UUID.randomUUID().toString();
		GroupInviteKey inv = new GroupInviteKey(groupId,token);
		GroupInvite invite = new GroupInvite(inv,createdBy);
		groupInviteRepository.save(invite); 
		return invite;
	}
	
	public Stream<Object> searchGroupMembers(long groupId,String query){
		 List<GroupMember> matches = groupMemberRepository.findAllByGroupId(groupId);
		 if(matches.isEmpty()) {
			 throw new AppException(" ");
		 }
		 Stream<Object> users =  matches.stream().map(member -> 
		 	userRepository.findById(member.getId().getUserId()).get() );
		 List<User> fil1 = userRepository.findByEmailContaining(query); 
		 List<User> fil2 = userRepository.findByMobileContaining(query);
		 fil1.addAll(fil2); 
		 fil1.retainAll(users.collect(Collectors.toList())); 
		 
		 return fil1.stream().map(user -> new GetUserResponse(
				 ((User) user).getId(),
				 ((User) user).getFName(), 
				 ((User) user).getLname(),
				 ((User) user).getEmail(),
				 ((User) user).getProPicURL()));
	}

}
