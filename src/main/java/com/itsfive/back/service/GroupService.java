package com.itsfive.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.User;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class GroupService {
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupActivityService groupActivityService;
	
	@Autowired
	private UserNotificationService userNotificationService;
	
	//create group
	public Group createGroup(Group group,User createdBy) throws JsonProcessingException {
		Group t = groupRepository.save(group);
		groupActivityService.addGroupActivity(t.getId(),createdBy,"<b>"+createdBy.getFName()+"</b>" + " created group " + "<b>"+t.getName()+"</b>"  );
		return t;
	}
	
    //edit group description
	public void editGroupDescription(Long id,Long editedBy,String description) throws JsonProcessingException {  
		Group group = groupRepository.findById(id).get();
		User user = userRepository.findById(editedBy).get();
		group.setDescription(description);
		groupRepository.save(group);
		GroupActivity ga = groupActivityService.addGroupActivity(group.getId(),user,"<b>"+user.getFName() + "</b> edited group description");
		userNotificationService.createUserNotificationsForGroupMembers(id, ga); 
	}
	
	public void editGroupData(Long id,Long editedBy,String name,String description,boolean isPrivate) throws JsonProcessingException {  
		Group group = groupRepository.findById(id).get();
		User user = userRepository.findById(editedBy).get();
		if(description != null) {
			group.setDescription(description);
		}
		if(name != null ) {
			group.setName(name);
		}
		
		if(group.isPrivate() != isPrivate) {
			group.setPrivate(isPrivate); 
		}
		
		groupRepository.save(group);
		if( (name != null) || (description != null) ) {
			GroupActivity ga = groupActivityService.addGroupActivity(group.getId(),user,"<b>"+user.getFName() + "</b> edited group data");
			userNotificationService.createUserNotificationsForGroupMembers(id, ga); 
		}
	}
	
    //get all groups in which a user is a member
	public List<Group> getGroupsByUser(Long id){
		return groupRepository.findGroupById(id);
	}	
    
	//Set group status
	public boolean setGroupPrivacy(long groupId,boolean status,long changedById) throws JsonProcessingException {
    	Group g = groupRepository.findById(groupId).get();
    	boolean prevStatus = g.isPrivate();
    	User changedBy = userRepository.findById(changedById).get();
    	if(status != prevStatus) {
    		g.setPrivate(status);
        	groupRepository.save(g);
        	String description = "<b>"+changedBy.getFName()+"</b> changed group privacy from <b>"+
        	(prevStatus ? "Private</b>" : "Public</b>")+ " to <b>"+(status ? "Private</b>" : "Public</b>")
        			+ "in group <b>"+ g.getName() +"</b>";
        	groupActivityService.addGroupActivity(groupId, changedBy, description);
    	} 
    	return groupRepository.findById(groupId).get().isPrivate();
    }
	
    //edit group cover photo
	public String getCoverURL(Long id){
		return groupRepository.findById(id).get().getCoverPhoto();
	}
	
    //getGroupById
	private Optional<Group> getGroupById(Long id) {
		return groupRepository.findById(id);
	}
	
	public Optional<Group> getGroup(Long id) {
		return groupRepository.findById(id);
	}
}
