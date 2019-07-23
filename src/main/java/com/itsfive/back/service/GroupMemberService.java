package com.itsfive.back.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.GroupInvite;
import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
import com.itsfive.back.model.HTMLMail;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.GetGroupAdminResponse;
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
    
	public void addMember(String itoken) {
		GroupInvite invite = groupInviteRepository.findByItoken(itoken);
		GroupMember groupMember = new GroupMember(invite.getId());
		groupMember.setRole("member");
		groupMemberRepository.save(groupMember);
		groupInviteRepository.delete(invite);
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
		return groupMemberRepository.findByGroupIdAndRole(groupId,"member").stream().map(
			member -> new GetGroupAdminResponse(member.getUser().getFName(),member.getUser().getLname(),member.getUser().getProPicURL())
				);
	}
	
	public void sendJoinInvitationMail(Long groupId,String email) throws MessagingException {
		User user = userRepository.findByEmail(email).get();
		GroupMembersKey key = new GroupMembersKey(user.getId(),groupId);
		
		String groupName = groupRepository.findById(groupId).get().getName();
		
		String token = UUID.randomUUID().toString();
		
		GroupInvite groupInvite = new GroupInvite(key);
		groupInvite.setIToken(token);
		groupInviteRepository.save(groupInvite);
		
		String content = "<html>" +
                "<body>" +
                "<p>Hello "+ user.getFName()+",</p>" +
                "<p>Click <a href=\"http://localhost:3000/groups/"+groupId+"/?itoken="+token+"\">here</a> to accept invitation</p>" +
            "</body>" +
        "</html>";
   	 
   	 HTMLMail htmlMail = new HTMLMail(user.getEmail(),"OnTask - Group Invitation for "+groupName,content);
   	 
   	 senderService.sendHTMLMail(htmlMail);
		
	}
}
