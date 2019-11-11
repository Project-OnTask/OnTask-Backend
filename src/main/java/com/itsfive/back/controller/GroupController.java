package com.itsfive.back.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsfive.back.exception.BadRequestException;
import com.itsfive.back.exception.UserNotFoundException;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.CreateGroupRequest;
import com.itsfive.back.payload.EditGroupDataRequest;
import com.itsfive.back.payload.GetAllGroupsResponse;
import com.itsfive.back.payload.LoginRequest;
import com.itsfive.back.payload.UploadFileResponse;
import com.itsfive.back.repository.GroupActivityRepository;
import com.itsfive.back.repository.GroupMemberRepository;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.service.FileService;
import com.itsfive.back.service.GroupActivityService;
import com.itsfive.back.service.GroupMemberService;
import com.itsfive.back.service.GroupService;
import com.itsfive.back.service.MailSenderService;
import com.itsfive.back.service.UserService;

@RestController
@RequestMapping("/api")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupActivityService groupActivityService;
	
	@Autowired
	private GroupActivityRepository groupActivityRepository;
	
	@Autowired
	private GroupMemberService groupMemberService;
	
	@Autowired
	private FileService fileService;
	
    @Autowired
    private MailSenderService senderService;
	
	//create group
    @PostMapping("/groups")
    public Long createGroup(@RequestBody CreateGroupRequest createGroupRequest) throws JsonProcessingException {
    	Optional<User> createdBy = userService.getUserById(createGroupRequest.getUserId());
    	if(!createdBy.isPresent()) {
    		throw new BadRequestException("There is no user for provided id");
    	}
    	Group group = new Group(createGroupRequest.getName(),createGroupRequest.getDescription(),createdBy.get());
        Long groupId = groupService.createGroup(group,createdBy.get()).getId();   
    	GroupMembersKey AdminKey = new GroupMembersKey(createdBy.get().getId(),groupId);
    	GroupMember Admin = new GroupMember(AdminKey); 	
    	Admin.setRole("admin");
    	groupMemberService.addAdmin(Admin);
        if(createGroupRequest.getMembers() != null) {
        	for(int i=0;i<createGroupRequest.getMembers().length;i++) { 
            	GroupMembersKey key = new GroupMembersKey(createGroupRequest.getMembers()[i],group.getId());
            	GroupMember member = new GroupMember(key);
            	member.setRole("member");
            	groupMemberService.addMember(member,createdBy.get().getId());
            }
        }
        return groupId;
    }
    
    //Get all groups of a specified user
    @GetMapping("/{userId}/groups")
    public List<GetAllGroupsResponse> getGroups(@PathVariable Long userId) {
    	List<GroupMember> memRecords = groupMemberService.getGroupsByMember(userId);
    	List<GetAllGroupsResponse> res = new ArrayList<>();
    	if(memRecords != null) {
    		for(int i=0;i<memRecords.size();i++) {
    			List<GroupActivity> activities = groupActivityRepository.findByGroupId(memRecords.get(i).getId().getGroupId());
    			GetAllGroupsResponse group = new GetAllGroupsResponse(
    					memRecords.get(i).getId().getGroupId(),
    					memRecords.get(i).getGroup().getName(),
    					memRecords.get(i).getRole(),
    					activities.get(activities.size() - 1).getDescription()
    			);
    			res.add(group);
    		}
    	}
    	return res;
    }
    
    //Get the number of groups in which the user is a member of
    @GetMapping("/{userId}/groups/count")
    public int getGroupsCount(@PathVariable Long userId) {
    	List<GroupMember> memRecords = groupMemberService.getGroupsByMember(userId);
    	return memRecords.size();
    }
    
    //Check if a group exists for provided group id
    @GetMapping("/exists/group/{groupId}")
    public Group isGroup(@PathVariable Long groupId) {
    	if(groupService.getGroup(groupId) == null) {
    		throw new BadRequestException("Bad request");
    	}
    	return groupService.getGroup(groupId).get();
    }
    
    //Edit group description
    @PostMapping("/groups/{groupId}/edit-desc")
    public void editGroupDescription(@PathVariable long groupId,@RequestParam("editedBy") Long editedBy,@RequestParam("desc") String description) throws JsonProcessingException {	
    	groupService.editGroupDescription(groupId,editedBy,description);
    }
    
    //Edit group details
    @PutMapping("/groups/{groupId}")
    public void editGroupData(@PathVariable long groupId,@RequestBody EditGroupDataRequest req) throws JsonProcessingException {	
    	groupService.editGroupData(groupId,req.getEditedBy(),req.getName(),req.getDescription(),req.isPrivate());
    }
    
    @GetMapping("/groups/{groupId}")
    public Group getGroup(@PathVariable long groupId) {
    	return groupRepository.findById(groupId).get();
    }
    
    @GetMapping("/groups/{groupId}/desc")
    public String getGroupDescription(@PathVariable long groupId) {
    	return groupRepository.findById(groupId).get().getDescription();
    }
    
    //Get group activity of a specified group
    @GetMapping("/groups/{groupId}/activity")
    public List<GroupActivity> getGroupActivity(@PathVariable long groupId) { 
    	return groupActivityRepository.findByGroupId(groupId);
    }
    
    @GetMapping("/groups/{groupId}/itoken")
    public List<GroupActivity> getGroupInvitationToken(@PathVariable long groupId) { 
    	return groupActivityRepository.findByGroupId(groupId);
    }
    
}
