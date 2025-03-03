package com.tweets.consumer;

import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tweets.communicator.UserServiceCommunicator;
import com.tweets.datamodel.Tweet;
import com.tweets.datamodel.User;
import com.tweets.handler.NotificationHandler;
import com.tweets.util.GsonUtils;

@Service
public class TweetPersistedNotificationConsumer {

	@Autowired
	private NotificationHandler notificationHandler;

	@Autowired
	UserServiceCommunicator userServiceComm;

	@KafkaListener(topics = "tweets.persisted")
	private void sendNotification(List<ConsumerRecord<String, String>> recordsList) {
		recordsList.forEach(record -> {
			Tweet tweet = GsonUtils.getGson().fromJson(record.value(), Tweet.class);
			notificationHandler.sendEmail(tweet.getUserId(), "Tweet Posted!", generateTweetPostedEmailBody(tweet));
		});
	}

	private String generateTweetPostedEmailBody(Tweet tweet) {
		User user = userServiceComm.getUsersDetails(Arrays.asList(tweet.getUserId())).get(0);
		String message = "<div>Hey %s,</div>" + 
						"<br>" + 
						"<div>The tweet is posted successfully -</div>" +
						"<br>" +
						"<div>%s</div>" +
						"<br>" +
						"<div>Regards</div>" +
						"<br>" +
						"<div><b>Twitter<b></div>";
		return String.format(message, user.getUsername(), tweet.getTweet());
	}
}
