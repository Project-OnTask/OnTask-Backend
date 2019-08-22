package com.itsfive.back.repository;

import org.springframework.data.repository.CrudRepository;

import com.itsfive.back.model.GroupInvite;

public interface GroupInviteRepository extends CrudRepository<GroupInvite, Long> {
	public GroupInvite findByIdItoken(String itoken); 
}
