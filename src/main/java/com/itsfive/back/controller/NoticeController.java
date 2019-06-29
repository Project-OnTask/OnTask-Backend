package com.itsfive.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsfive.back.payload.AddNoticeRequest;
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
}
