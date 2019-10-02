package com.itsfive.back.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itsfive.back.config.PusherConfig;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.Task;
import com.itsfive.back.model.TaskActivity;
import com.itsfive.back.model.User;
import com.itsfive.back.repository.TaskActivityRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class TaskActivityService {
	
	@Autowired
	private TaskActivityRepository taskActivityRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	ObjectMapper objectMapper = new ObjectMapper();
	JavaTimeModule module = new JavaTimeModule();
	
	public TaskActivity addTaskActivity(Long taskId,User user,String description) throws JsonProcessingException {  
		objectMapper.registerModule(module);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));
		Task task = taskRepository.findById(taskId).get();
		
		TaskActivity taskActivity = new TaskActivity(description,user,task);
		taskActivityRepository.save(taskActivity);
 		PusherConfig.setObj().trigger("task_"+taskId, "new_activity",objectMapper.writeValueAsString(taskActivity));
		return taskActivity;
	}
	
	public List<TaskActivity> getTaskActivities(Long taskId) {
		return taskActivityRepository.findAllByTaskId(taskId); 
	}
}
