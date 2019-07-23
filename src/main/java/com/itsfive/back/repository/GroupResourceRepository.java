package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.GroupResource;

@Repository
public interface GroupResourceRepository extends CrudRepository<GroupResource, Long>{
	public List<GroupResource> findByTaskId(long taskId);
}
