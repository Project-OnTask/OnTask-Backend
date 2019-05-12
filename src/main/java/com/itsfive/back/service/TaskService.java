package com.itsfive.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.Task;
import com.itsfive.back.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	public boolean addTask(Task task) {
		Task t = taskRepository.save(task);
		return t.getId() >= 1 ? true : false;
	}
}
