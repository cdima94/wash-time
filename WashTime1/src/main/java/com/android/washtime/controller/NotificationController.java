package com.android.washtime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.android.washtime.entity.Notification;
import com.android.washtime.repository.NotificationRepository;

@Controller
public class NotificationController {

	@Autowired
	NotificationRepository notRepo;
	
	@ResponseBody
	@RequestMapping(value = "/getNotificationList/{userName}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Notification> getNotificationList(@PathVariable String userName) {		
		return notRepo.findByStudentUserName(userName);
	}
}
