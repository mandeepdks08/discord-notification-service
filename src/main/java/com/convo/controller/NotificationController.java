package com.convo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.convo.handler.NotificationHandler;
import com.convo.restmodel.BaseResponse;
import com.convo.restmodel.SendMessageNotificationRequest;

@RestController
@RequestMapping("/notification/v1")
public class NotificationController {

	@Autowired
	private NotificationHandler notificationHandler;

	@RequestMapping(value = "/message/new", method = RequestMethod.POST)
	protected ResponseEntity<BaseResponse> sendNewMessageNotification(
			@RequestBody SendMessageNotificationRequest request) {
		BaseResponse baseResponse = BaseResponse.builder().build();
		HttpStatus httpStatus = null;
		try {
			notificationHandler.sendNewMessageNotification(request);
			baseResponse.setMessage("Notification sent successfully!");
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(baseResponse, httpStatus);
	}
}
