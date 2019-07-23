package com.itsfive.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupResource;
import com.itsfive.back.model.Task;
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
	 private GroupRepository groupRepository;
	 
	 @Autowired
	 private GroupResourceRepository groupResRepository;
	  
//	public void addResource(AddTaskResourceRequest addResReq,String url) {
	public void addResource(long userId,long taskId,String url) {
		//User addedBy = userRepository.findById(addResReq.getAddedBy()).get();
		//Task task = taskRepository.findById(addResReq.getTaskId()).get();
		
		User addedBy = userRepository.findById(userId).get();
		Task task = taskRepository.findById(taskId).get();
		Group group = taskRepository.findById(taskId).get().getGroup();
		
		GroupResource groupResource = new GroupResource(
				addedBy,
				task,
				group,
				url
		);
		
		groupResRepository.save(groupResource);
	}
}
