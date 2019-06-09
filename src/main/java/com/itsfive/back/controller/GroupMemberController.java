package com.itsfive.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
import com.itsfive.back.service.GroupMemberService;

@RestController
@RequestMapping("/api")
public class GroupMemberController {
	@Autowired
	private GroupMemberService groupMemberService;
	
    @PostMapping("/member")
	public void createMember(@RequestBody GroupMembersKey groupMembersKey ) {
    	groupMemberService.addMember(new GroupMember(groupMembersKey));
	}
    
    @PostMapping("/member/admin")
	public void setMemberAdmin(@RequestBody GroupMember groupMember ) {
    	groupMemberService.setMemberAdmin(groupMember);
	}
    
    @PostMapping("/member/member")
	public void removeMemberAdmin(@RequestBody GroupMember groupMember ) {
    	groupMemberService.removeMemberAdmin(groupMember);
	}
    
    
}
