package com.tweets.datamodel;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {
	protected Long id;
	private String userId;
	private String tweet;
	protected LocalDateTime createdOn;
	protected LocalDateTime processedOn;
	
	@Override
	public String toString() {
		return "Tweet [userId=" + userId + ", tweet=" + tweet + ", id=" + id + ", createdOn=" + createdOn
				+ ", processedOn=" + processedOn + "]";
	}
}
