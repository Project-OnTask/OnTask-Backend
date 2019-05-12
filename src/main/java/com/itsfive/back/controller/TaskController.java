package com.itsfive.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsfive.back.model.Task;
import com.itsfive.back.service.TaskService;

@RestController
//@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("/task")
	public boolean addTask(@RequestBody Task task) {
		return taskService.addTask(task);
	}
}
