package com.itsfive.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupResource;
import com.itsfive.back.model.UserTask;
import com.itsfive.back.model.User;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.GroupResourceRepository;

@Service
public class GroupResourceService {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private TaskRepository taskRepository;
	 
	 @Autowired
	 private TaskActivityService taskActivityService;
	 
	 @Autowired
	 private GroupRepository groupRepository;
	 
	 @Autowired
	 private GroupResourceRepository groupResRepository;
	  
	public void addResource(long userId,long taskId,String url) throws JsonProcessingException {	 
		User addedBy = userRepository.findById(userId).get();
		UserTask task = taskRepository.findById(taskId).get();
		Group group = taskRepository.findById(taskId).get().getGroup();
		
		GroupResource groupResource = new GroupResource(
				addedBy,
				task,
				group,
				url
		);
		
		GroupResource rs = groupResRepository.save(groupResource);
		String description = "<b>"+addedBy.getFName()+"</b> added a resource <b>"+url.split("/")[5]+"</b>"; 
		taskActivityService.addTaskActivity(taskId, addedBy, description);
	}
}
