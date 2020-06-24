package com.lambdaschool.subredditpredictor.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super("error in subreddit predictor application - " + message);
	}
}
