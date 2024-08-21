package com.convo.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.convo.communicator.UserServiceCommunicator;
import com.convo.datamodel.User;
import com.convo.restmodel.SendMessageNotificationRequest;

@Component
public class NotificationHandler {

	@Autowired
	private UserServiceCommunicator userServiceCommunicator;

	public void sendNewMessageNotification(SendMessageNotificationRequest request) {
		List<User> userList = userServiceCommunicator
				.getUsersDetails(Arrays.asList(request.getToUserId(), request.getFromUserId()));
		User toUser = userList.stream().filter(user -> user.getUserId().equals(request.getToUserId())).findFirst()
				.orElse(null);
		User fromUser = userList.stream().filter(user -> user.getUserId().equals(request.getFromUserId())).findFirst()
				.orElse(null);
		// TODO: Send notification to user 
	}
}
