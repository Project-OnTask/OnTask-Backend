package com.itsfive.back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.itsfive.back.model.GroupMember;

public interface GroupMemberRepository  extends CrudRepository<GroupMember, Long>{
	public List<GroupMember> findAllByUserId(Long id);
	
	public List<GroupMember> findAllByGroupId(Long id);
	
	public Optional<GroupMember> findByUserIdAndGroupId(Long userId,Long groupId); 
	
	public List<GroupMember> findByGroupIdAndRole(Long groupId,String role);
}
