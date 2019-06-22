package com.itsfive.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.Group;
import com.itsfive.back.model.Task;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.CreateTaskRequest;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
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
	}
	
	public List<Task> getAllTasksOfGroup(Long groupId) {
		return taskRepository.findAllByGroupId(groupId);
	}
	
	public Task getTaskById(long id) {
		return taskRepository.findById(id).get();
	}
}
