package com.itsfive.back.repository;

import org.springframework.data.repository.CrudRepository;

import com.itsfive.back.model.GroupMember;

public interface GroupMemberRepository  extends CrudRepository<GroupMember, Long>{

}
