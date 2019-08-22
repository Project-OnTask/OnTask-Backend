package com.itsfive.back.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.Group;
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
	
	public void addNotice(AddNoticeRequest addNoteReq) {
		Notice note = new Notice();
		User createdBy = userRepository.findById(addNoteReq.getUserId()).get();
		Group group =groupRepository.findById(addNoteReq.getGroupId()).get();
		note.setGroup(group);
		note.setCreatedBy(createdBy);
		note.setTitle(addNoteReq.getTitle());
		note.setContent(addNoteReq.getContent());
		noticeRepository.save(note);
		groupActivityService.addGroupActivity(group.getId(),createdBy,createdBy.getFName() + " posted announcement " + addNoteReq.getTitle()  );
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
