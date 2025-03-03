package com.tweets.restmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSendRequest {
	private String toUserId;
	private String fromUserId;
	private String message;
}
