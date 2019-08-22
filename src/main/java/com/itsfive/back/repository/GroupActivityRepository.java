package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.GroupActivity;

@Repository
public interface GroupActivityRepository extends CrudRepository<GroupActivity, Long>{
	public List<GroupActivity> findByGroupId(long groupId);
}
