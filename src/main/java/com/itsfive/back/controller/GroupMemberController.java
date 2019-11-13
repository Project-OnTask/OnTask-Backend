package com.itsfive.back.controller;

import java.util.List;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsfive.back.model.GroupInvite;
import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.GetGroupAdminResponse;
import com.itsfive.back.payload.GetGroupMembersResponse;
import com.itsfive.back.payload.GetUserResponse;
import com.itsfive.back.repository.GroupMemberRepository;
import com.itsfive.back.payload.ManageMembersRequest;
import com.itsfive.back.payload.SendInvitationEmailRequest;
import com.itsfive.back.service.GroupMemberService;

@RestController
@RequestMapping("/api")
public class GroupMemberController {
	@Autowired
	private GroupMemberService groupMemberService;
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	//Add member to a group using inviting URL
    @PostMapping("/member/{itoken}")
	public GroupMember createMember(@PathVariable String itoken,@RequestParam("user_id") long userId ) throws JsonProcessingException {
    	return groupMemberService.addMemberByToken(itoken,userId);
	}
    
    //Add member to a group
    @PostMapping("/members")
    public void createMember(@RequestParam long addedBy,@RequestBody GroupMembersKey groupMember )throws JsonProcessingException {
    	groupMemberService.addMember(new GroupMember(new GroupMembersKey(groupMember.getUserId(),groupMember.getGroupId())),addedBy);
	}
    
    //Make a group member an admin of a group
    @PostMapping("/member/admin")
	public void setMemberAdmin(@RequestBody ManageMembersRequest groupMember ) throws JsonProcessingException {
    	groupMemberService.setMemberAdmin(groupMember);
	}
    
    //Get group admins of a group
    @GetMapping("/member/{groupId}/admin")
	public Stream<Object> getGroupAdmins(@PathVariable long groupId ) {
    	return groupMemberService.getGroupAdmins(groupId);
	}
    
    //Get group Members( excluding admins) of a group
    @GetMapping("/member/{groupId}")
	public Stream<Object> getGroupMembers(@PathVariable long groupId ) {
    	return groupMemberService.getGroupMembers(groupId);
	}
    
    //Make an admin a member
    @PostMapping("/member/member")
	public void removeMemberAdmin(@RequestBody ManageMembersRequest groupMember ) throws JsonProcessingException {
    	groupMemberService.removeMemberAdmin(groupMember);
	}
    
    //Remove a member from a group
    @DeleteMapping("/member/{groupId}/remove/{memberId}")
	public void removeMember(@PathVariable("groupId") long groupId,@RequestParam("delBy") long deletedById,@PathVariable("memberId") long userId ) throws JsonProcessingException {
    	groupMemberService.removeMember(userId,groupId,deletedById);
	}
    
    //Check of given user is an admin of the group
    @GetMapping("/member/{groupId}/is-admin/{userId}")
	public boolean isAdmin(@PathVariable Long groupId,@PathVariable Long userId ) {
    	if(groupMemberRepository.findByUserIdAndGroupId(userId, groupId).get().getRole().equals( "admin")) {
    		return true;
    	}
    	else {
    		return false;
    	}
	}
    
    //Create invitation link
    @PostMapping("/member/{groupId}/join")
    public GroupInvite createInviteLink(@RequestParam("created_by") long createdBy,@PathVariable Long groupId) throws MessagingException {
    	return groupMemberService.createInviteLink(createdBy,groupId);
    }
    
    //Send email invitations to selected users
    @PostMapping("/member/{groupId}/join-email")
    public void InviteUsersViaEmail(@RequestBody SendInvitationEmailRequest req) throws MessagingException {
    	groupMemberService.createAndSendInviteLink(req.getcreatedById(),req.getGroupId(),req.getReceiverId());
    }
    
    //Search group members of a group
    @GetMapping("/member/{groupId}/search/{query}")
	 public Stream<Object> searchGroupMembers(@PathVariable long groupId,@PathVariable String query){ 
		return groupMemberService.searchGroupMembers(groupId, query);
	 }
}
