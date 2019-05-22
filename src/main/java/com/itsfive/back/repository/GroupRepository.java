package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long>{
	
	public List<Group> findGroupById(Long Id);
}
