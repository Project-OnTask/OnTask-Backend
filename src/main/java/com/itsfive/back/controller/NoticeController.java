package com.itsfive.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsfive.back.model.Notice;
import com.itsfive.back.payload.AddNoticeRequest;
import com.itsfive.back.payload.GetNoticesResponse;
import com.itsfive.back.service.NoticeService;

@RestController
@RequestMapping("/api")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@PostMapping("/notices")
	public void addNotice(@RequestBody AddNoticeRequest addNoteReq) {
		noticeService.addNotice(addNoteReq);
	}
	
	@GetMapping("/notices/group/{groupId}")
	public List<GetNoticesResponse> getNoticesByGroup(@PathVariable long groupId) {
		return noticeService.getNoticesByGroup(groupId);
	}
	
	@GetMapping("/notices/{Id}")
	public Notice getNoticeById(@PathVariable long Id) {
		return noticeService.getNoticeById(Id);
	}
}
