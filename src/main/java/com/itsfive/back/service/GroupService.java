package com.itsfive.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itsfive.back.exception.BadRequestException;
import com.itsfive.back.model.Group;
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
	
	//create group
	public Group createGroup(Group group,User createdBy) {
		Group t = groupRepository.save(group);
		groupActivityService.addGroupActivity(t.getId(),createdBy,createdBy.getFName() + " created group " + t.getName()  );
		return t;
	}
	
    //delete group
	public void deleteGroupById(Long id) {
		groupRepository.deleteById(id);
	}
	
    //edit group description
	public void editGroupDescription(Long id,Long editedBy,String description) { 
		Group group = groupRepository.findById(id).get();
		User user = userRepository.findById(editedBy).get();
		group.setDescription(description);
		groupRepository.save(group);
		groupActivityService.addGroupActivity(group.getId(),user,user.getFName() + " edited group description");
	}
	
    //get all groups in which a user is a member
	public List<Group> getGroupsByUser(Long id){
		return groupRepository.findGroupById(id);
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
