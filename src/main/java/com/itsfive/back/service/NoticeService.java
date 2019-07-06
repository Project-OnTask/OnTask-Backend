package com.itsfive.back.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.model.Notice;
import com.itsfive.back.payload.AddNoticeRequest;
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
	private NoticeRepository noticeRepository;
	
	public void addNotice(AddNoticeRequest addNoteReq) {
		Notice note = new Notice();
		note.setGroup(groupRepository.findById(addNoteReq.getGroupId()).get());
		note.setCreatedBy(userRepository.findById(addNoteReq.getUserId()).get());
		note.setTitle(addNoteReq.getTitle());
		note.setContent(addNoteReq.getContent());
		noticeRepository.save(note);
	}

	public List<Notice> getNoticesByGroup(long groupId) {
		return noticeRepository.findAllByGroupId(groupId).stream()
				.map(elt -> new Notice(elt.getId(),elt.getTitle()))
				.collect(Collectors.toList());
	}
	
	public Notice getNoticeById(long id) {
		return noticeRepository.findById(id).get();
	}
}
