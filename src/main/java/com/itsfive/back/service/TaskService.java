package com.itsfive.back.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.config.PusherConfig;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.Task;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.CreateTaskRequest;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.UserRepository;
import com.pusher.rest.Pusher;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private GroupActivityService groupActivityService;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void createTaskForGroup(CreateTaskRequest createTaskRequest) {
		Task task = new Task(createTaskRequest.getName(),createTaskRequest.getDescription(),createTaskRequest.getDueDate());
		Group group = groupRepository.findById(createTaskRequest.getGroupId()).get();
		task.setGroup(group);
		User user = userRepository.findById(createTaskRequest.getCreatedBy()).get();
		task.setCreatedBy(user);
		taskRepository.save(task);
		groupActivityService.addGroupActivity(group.getId(), user, user.getFName() + " added a new task "+task.getName()); 
		//PusherConfig.setObj().trigger("group_"+group.getId(), "new_task", Collections.singletonMap("message", "hello world"));
	}
	
	public List<Task> getAllTasksOfGroup(Long groupId) {
		return taskRepository.findAllByGroupId(groupId);
	}
	
	public Task getTaskById(long id) {
		return taskRepository.findById(id).get();
	}
	
	public void editTaskDescription(long taskId,String description) {
		Task task = taskRepository.findById(taskId).get();
		task.setDescription(description);
		taskRepository.save(task);
	}
}
