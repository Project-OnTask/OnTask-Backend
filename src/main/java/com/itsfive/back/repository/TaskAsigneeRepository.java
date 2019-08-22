package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.TaskAsignee;
import com.itsfive.back.model.TaskAsigneeKey;

@Repository
public interface TaskAsigneeRepository extends CrudRepository<TaskAsignee, Long> {
	public List<TaskAsignee> findAllById_taskId(long taskId);
	
	public TaskAsignee findById(TaskAsigneeKey key);
	
	public List<TaskAsignee> findAllByIdUserId(long userId);
}
