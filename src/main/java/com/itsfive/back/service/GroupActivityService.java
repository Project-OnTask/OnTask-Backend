package com.itsfive.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.User;
import com.itsfive.back.repository.GroupActivityRepository;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class GroupActivityService {
	
	@Autowired
	private GroupActivityRepository groupActivityRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void addGroupActivity(Long groupId,User user,String description) {
		Group group = groupRepository.findById(groupId).get();
		GroupActivity groupActivity = new GroupActivity(description,user,group);
		groupActivityRepository.save(groupActivity);
	}
	
	public List<GroupActivity> getGroupActivity(Long groupId) {
		return groupActivityRepository.findByGroupId(groupId); 
	}
}
