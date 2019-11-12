package com.itsfive.back.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itsfive.back.config.PusherConfig;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.Notice;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.AddNoticeRequest;
import com.itsfive.back.payload.GetNoticesResponse;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.NoticeRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class NoticeService {
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupActivityService groupActivityService;
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private UserNotificationService userNotificationService;
	
	public void addNotice(AddNoticeRequest addNoteReq) throws JsonProcessingException { 
		Notice note = new Notice();
		User createdBy = userRepository.findById(addNoteReq.getUserId()).get();
		Group group =groupRepository.findById(addNoteReq.getGroupId()).get();
		note.setGroup(group);
		note.setCreatedBy(createdBy);
		note.setTitle(addNoteReq.getTitle());
		note.setContent(addNoteReq.getContent());
		noticeRepository.save(note);
		GroupActivity act = groupActivityService.addGroupActivity(group.getId(),createdBy,"<b>"+createdBy.getFName() + "</b> posted announcement <b>" + addNoteReq.getTitle()+"</b> in group <b>"+group.getName()+"</b>"  );
		userNotificationService.createUserNotificationsForGroupMembers(addNoteReq.getGroupId(), act);
		userNotificationService.publishToGroupActivity(addNoteReq.getGroupId(), act); 
	}

	public List<GetNoticesResponse> getNoticesByGroup(long groupId) {
		return noticeRepository.findAllByGroupId(groupId).stream()
				.map(elt -> new GetNoticesResponse(elt.getId(),elt.getTitle(),elt.getCreatedAt(),elt.getCreatedBy().getFName()))
				.collect(Collectors.toList());
	}
	
	public Notice getNoticeById(long id) {
		return noticeRepository.findById(id).get();
	}
}
