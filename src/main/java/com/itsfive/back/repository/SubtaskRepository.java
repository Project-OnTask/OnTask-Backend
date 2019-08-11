package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.SubTask;
import com.itsfive.back.model.Task;

@Repository
public interface SubtaskRepository extends CrudRepository<SubTask, Long> {
	public List<SubTask> findAllByTask(Task task);
}
