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
import com.itsfive.back.payload.TaskAsigneeResponse;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.TaskAsigneeRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.service.GroupActivityService;
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
		
		GroupActivity act = groupActivityService.addGroupActivity(addAsReq.getGroupId(),user,"<b>"+user.getFName()+"</b> was assigned to task <b>"+
		task.getName() + "</b> in <b>"+group.getName()+"</b>"); 
		userNotificationService.createUserNotificationsForGroupMembers(addAsReq.getGroupId(), act);
		PusherConfig.setObj().trigger("group_"+addAsReq.getGroupId(), "new_activity",objectMapper.writeValueAsString(act));
	}
	
	@DeleteMapping("/task-asignee/{userId}")
	public void removeTaskAssignee(@RequestParam("task_id") long taskId,@PathVariable long userId) {
		TaskAsigneeKey tak = new TaskAsigneeKey(userId,taskId);
		TaskAsignee ts = taskAsRepository.findById(tak);
		taskAsRepository.delete(ts);
	}
	
	@GetMapping("/task-asignee/{taskId}")
	public Stream<Object> getTaskAsignees(@PathVariable long taskId) {
		return taskAsRepository.findAllById_taskId(taskId).stream().map(
				asignee -> {
					User user = userRepository.findById(asignee.getId().getUserId()).get();
					return new TaskAsigneeResponse(user.getFName(),user.getProPicURL(),user.getLname(),user.getId());
				}
		);	
	}
}
