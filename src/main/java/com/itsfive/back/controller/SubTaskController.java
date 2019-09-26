package com.itsfive.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsfive.back.model.SubTask;
import com.itsfive.back.model.Task;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.addSubtaskRequest;
import com.itsfive.back.repository.SubtaskRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.security.CurrentUser;
import com.itsfive.back.service.TaskActivityService;
import com.itsfive.back.service.UserNotificationService;

@RestController
@RequestMapping("/api")
public class SubTaskController {
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SubtaskRepository subTaskRepository;
	
	@Autowired
	private UserNotificationService userNotificationService;
	
	@Autowired
	private TaskActivityService taskActivityService;
	
	@PostMapping("/subtasks")
	public void addSubTask(@RequestBody addSubtaskRequest addSubReq) throws JsonProcessingException {
		Task task = taskRepository.findById(addSubReq.getTaskId()).get();
		SubTask subtask = new SubTask(task,addSubReq.getName());
		subTaskRepository.save(subtask);
		User user =  userRepository.findById(addSubReq.getAddedBy()).get();
		String description = "<b>"+user.getFName()+"</b> added a subtask <b>"+addSubReq.getName()+"</b>";
		taskActivityService.addTaskActivity(addSubReq.getTaskId(), user, description);
	}
	 
	@GetMapping("/subtasks/task/{taskId}")
	public List<SubTask> getSubtasks(@PathVariable Long taskId) {
		return subTaskRepository.findAllByTask(taskRepository.findById(taskId).get());
	}
	
	@PostMapping("/subtasks/{subtaskId}/{isComplete}")
	public void markSubtaskAsCompleted(@PathVariable long subtaskId,@PathVariable boolean isComplete) {
		SubTask subtsk = subTaskRepository.findById(subtaskId).get();
		subtsk.setCompleted(isComplete);
		subTaskRepository.save(subtsk);
	}
}
