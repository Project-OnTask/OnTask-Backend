package com.itsfive.back.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itsfive.back.config.PusherConfig;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.Task;
import com.itsfive.back.model.TaskAsignee;
import com.itsfive.back.model.TaskAsigneeKey;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.AddTaskAsigneeRequest;
import com.itsfive.back.payload.RemoveTaskAsigneeRequest;
import com.itsfive.back.payload.TaskAsigneeResponse;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.TaskAsigneeRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.service.GroupActivityService;
import com.itsfive.back.service.TaskActivityService;
import com.itsfive.back.service.UserNotificationService;

@RestController
@RequestMapping("/api")
public class TaskAsigneeController {
	
	@Autowired
	private TaskAsigneeRepository taskAsRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	ObjectMapper objectMapper = new ObjectMapper();
	JavaTimeModule module = new JavaTimeModule();
	
	@Autowired
	private GroupActivityService groupActivityService;
	
	@Autowired
	private TaskActivityService taskActivityService;
	
	@Autowired
	private UserNotificationService userNotificationService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/task-asignee")
	public void addTaskAssignee(@RequestBody AddTaskAsigneeRequest addAsReq) throws JsonProcessingException {
		objectMapper.registerModule(module);
		User user = userRepository.findById(addAsReq.getUserId()).get();
		Group group = groupRepository.findById(addAsReq.getGroupId()).get();
		Task task = taskRepository.findById(addAsReq.getTaskId()).get(); 
	
		TaskAsignee asignee = new TaskAsignee(new TaskAsigneeKey(user.getId(),addAsReq.getTaskId()));
		User addedBy = userRepository.findById(addAsReq.getAddedById()).get();
		asignee.setAddedBy(addedBy);
		taskAsRepository.save(asignee);
		
		String description = "<b>"+user.getFName()+"</b> was assigned to task <b>"+
				task.getName() + "</b> in group <b>"+group.getName()+"</b>";
		
		GroupActivity act = groupActivityService.addGroupActivity(addAsReq.getGroupId(),user,description); 
		taskActivityService.addTaskActivity(addAsReq.getTaskId(), addedBy, description);
		userNotificationService.createUserNotificationsForGroupMembers(addAsReq.getGroupId(), act);
		PusherConfig.setObj().trigger("group_"+addAsReq.getGroupId(), "new_activity",objectMapper.writeValueAsString(act));
	}
	
	@PostMapping("/task-asignee/remove/{userId}")
	public void removeTaskAssignee(@RequestBody RemoveTaskAsigneeRequest req,@PathVariable long userId) throws JsonProcessingException {
		TaskAsigneeKey tak = new TaskAsigneeKey(userId,req.getTaskId());
		TaskAsignee ts = taskAsRepository.findById(tak);
		taskAsRepository.delete(ts);
		
		User user = userRepository.findById(userId).get();
		User removedBy = userRepository.findById(req.getRemovedBy()).get();
		Task task = taskRepository.findById(req.getTaskId()).get();
		
		String description = "<b>"+user.getFName()+"</b> 's access to task <b>"+
				task.getName() + "</b> was revoked in group <b>"+task.getGroup().getName()+"</b>";
		
		GroupActivity act = groupActivityService.addGroupActivity(task.getGroup().getId(),removedBy,description); 
		taskActivityService.addTaskActivity(req.getTaskId(), removedBy, description);
		userNotificationService.createUserNotificationsForGroupMembers(task.getGroup().getId(), act);
		PusherConfig.setObj().trigger("group_"+task.getGroup().getId(), "new_activity",objectMapper.writeValueAsString(act));
	}
	
	@GetMapping("/task-asignee/{taskId}")
	public Stream<Object> getTaskAsignees(@PathVariable long taskId) {
		return taskAsRepository.findAllById_taskId(taskId).stream().map(
				asignee -> {
					User user = userRepository.findById(asignee.getId().getUserId()).get();
					return new TaskAsigneeResponse(user.getId(),user.getFName(),user.getProPicURL(),user.getEmailHash(),user.getLname(),user.getId());
				}
		);	
	}
}
