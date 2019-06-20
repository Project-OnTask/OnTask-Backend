package com.itsfive.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
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
	
	public List<GroupMember> getGroupsByMember(Long userId) {
		return groupMemberRepository.findAllByUserId(userId);
	}
	
	public boolean isMemberAnAdmin(Long userId,Long groupId) {
		GroupMember member = groupMemberRepository.findByUserIdAndGroupId(userId, groupId).get();
		return member.getRole().equals("admin");
	}
}
