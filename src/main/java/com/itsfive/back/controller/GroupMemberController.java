package com.itsfive.back.controller;

import java.util.List;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
import com.itsfive.back.payload.GetGroupAdminResponse;
import com.itsfive.back.repository.GroupMemberRepository;
import com.itsfive.back.service.GroupMemberService;

@RestController
@RequestMapping("/api")
public class GroupMemberController {
	@Autowired
	private GroupMemberService groupMemberService;
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
    @PostMapping("/member/{itoken}")
	public void createMember(@PathVariable String itoken ) {
    	groupMemberService.addMember(itoken);
	}
    
    @PostMapping("/member/admin")
	public void setMemberAdmin(@RequestBody GroupMember groupMember ) {
    	groupMemberService.setMemberAdmin(groupMember);
	}
    
    @GetMapping("/member/{groupId}/admin")
	public Stream<Object> getGroupAdmins(@PathVariable long groupId ) {
    	return groupMemberService.getGroupAdmins(groupId);
	}
    
    @GetMapping("/member/{groupId}")
	public Stream<Object> getGroupMembers(@PathVariable long groupId ) {
    	return groupMemberService.getGroupMembers(groupId);
	}
    
    @PostMapping("/member/member")
	public void removeMemberAdmin(@RequestBody GroupMember groupMember ) {
    	groupMemberService.removeMemberAdmin(groupMember);
	}
    
    @GetMapping("/member/{groupId}/is-admin/{userId}")
	public boolean isAdmin(@PathVariable Long groupId,@PathVariable Long userId ) {
    	if(groupMemberRepository.findByUserIdAndGroupId(userId, groupId).get().getRole().equals( "admin")) {
    		return true;
    	}
    	else {
    		return false;
    	}
	}
    
    @PostMapping("/member/{email}/join/{groupId}")
    public void sendJoinInvitationMail(@PathVariable String email,@PathVariable Long groupId) throws MessagingException {
    	groupMemberService.sendJoinInvitationMail(groupId,email);
    }
}
