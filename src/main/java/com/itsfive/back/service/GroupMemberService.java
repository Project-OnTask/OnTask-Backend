package com.itsfive.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.GroupMember;
import com.itsfive.back.repository.GroupMemberRepository;

@Service
public class GroupMemberService {
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	public void addMember(GroupMember groupMember) {
		groupMemberRepository.save(groupMember);
	}
	
	public void setMemberAdmin(GroupMember groupMember) {
		groupMember.setRole("admin");
		groupMemberRepository.save(groupMember);
	}
	
	public void removeMemberAdmin(GroupMember groupMember) {
		groupMember.setRole("member");
		groupMemberRepository.save(groupMember);
	}
}
