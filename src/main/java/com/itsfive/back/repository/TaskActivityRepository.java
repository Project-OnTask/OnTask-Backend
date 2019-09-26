package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.itsfive.back.model.TaskActivity;

public interface TaskActivityRepository extends CrudRepository<TaskActivity, Long>{
	public List<TaskActivity> findByTaskId(long taskId);
}
