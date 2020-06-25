package com.lambdaschool.subredditpredictor.exceptions;

public class ResourceFoundException extends RuntimeException {
	public ResourceFoundException(String message) {
		super("error in subreddit predictor application - " + message);
	}
}
