package com.convo.restmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageNotificationRequest {
	private String toUserId;
	private String fromUserId;
	private String message;
}
