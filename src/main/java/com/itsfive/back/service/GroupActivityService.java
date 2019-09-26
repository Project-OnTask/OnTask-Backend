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
	
	ObjectMapper objectMapper = new ObjectMapper();
	JavaTimeModule module = new JavaTimeModule();
	
	public  GroupActivity addGroupActivity(Long groupId,User user,String description) throws JsonProcessingException { 
		objectMapper.registerModule(module);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));
		Group group = groupRepository.findById(groupId).get();
		GroupActivity groupActivity = new GroupActivity(description,user,group);
		GroupActivity activity = groupActivityRepository.save(groupActivity);
 		PusherConfig.setObj().trigger("group_"+group.getId(), "new_activity",objectMapper.writeValueAsString(activity));
 		return activity;
	}
	
	public List<GroupActivity> getGroupActivity(Long groupId) {
		return groupActivityRepository.findByGroupId(groupId); 
	}
}
