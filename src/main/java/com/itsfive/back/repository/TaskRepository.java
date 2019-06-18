package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{
	public List<Task> findAllByGroupId(Long groupId);
}
