package com.lambdaschool.subredditpredictor.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super("Error in Subreddit Predictor Application - " + message);
	}
}
