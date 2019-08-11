package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.TaskAsignee;

@Repository
public interface TaskAsigneeRepository extends CrudRepository<TaskAsignee, Long> {
	public List<TaskAsignee> findAllById_taskId(long taskId);
}
