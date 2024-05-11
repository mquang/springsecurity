package com.mquang.securitydemo.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mquang.securitydemo.model.Notice;
import com.mquang.securitydemo.repository.NoticeRepository;

@RestController
//@CrossOrigin(origins = "http://localhost:4200") //solution 1 to handle CORS
public class NoticesController {

	@Autowired
	private NoticeRepository noticeRepository;

	/*
	 * Access to XMLHttpRequest at 'http://localhost:8080/notices' from origin
	 * 'http://localhost:4200' has been blocked by CORS policy: Response to
	 * preflight request doesn't pass access control check: No
	 * 'Access-Control-Allow-Origin' header is present on the requested resource.
	 */
	@GetMapping("/notices")
	public ResponseEntity<List<Notice>> getNotices() {
		List<Notice> notices = noticeRepository.findAllActiveNotices();
		if (notices != null) {
			return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(notices);
		} else {
			return null;
		}
	}
}
