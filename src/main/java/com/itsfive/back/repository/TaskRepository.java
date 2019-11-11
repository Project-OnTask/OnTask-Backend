package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.UserTask;

@Repository
public interface TaskRepository extends CrudRepository<UserTask, Long>{
	public List<UserTask> findAllByGroupId(Long groupId);
}
