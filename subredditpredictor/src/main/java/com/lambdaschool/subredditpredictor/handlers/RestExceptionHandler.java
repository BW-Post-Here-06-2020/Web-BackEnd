package com.lambdaschool.subredditpredictor.handlers;

import com.lambdaschool.subredditpredictor.exceptions.ResourceNotFoundException;
import com.lambdaschool.subredditpredictor.models.ErrorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private HelperFunctions helper;

	public RestExceptionHandler() {
		super();
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimestamp(new Date());
		errorDetail.setStatus((HttpStatus.NOT_FOUND.value()));
		errorDetail.setTitle("resource not found");
		errorDetail.setDetail(rnfe.getMessage());
		errorDetail.setDevMessage(rnfe.getClass().getName());
		errorDetail.setErrors(helper.getConstraintViolation(rnfe));

		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimestamp(new Date());
		errorDetail.setStatus(status.value());
		errorDetail.setTitle("rest internal exception");
		errorDetail.setDetail(ex.getMessage());
		errorDetail.setDevMessage(ex.getClass().getName());
		errorDetail.setErrors(helper.getConstraintViolation(ex));

		return new ResponseEntity<>(errorDetail, null, status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimestamp(new Date());
		errorDetail.setStatus(status.value());
		errorDetail.setTitle("parameter missing for " + "path - " + request.getDescription(false));
		errorDetail.setDetail("parameter missing: " + ex.getParameterName() + " type - " + ex.getParameterType());
		errorDetail.setDevMessage(ex.getMessage() + " " + ex.getClass());
		errorDetail.setErrors(helper.getConstraintViolation(ex));

		return new ResponseEntity<>(errorDetail,null, status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimestamp(new Date());
		errorDetail.setStatus(status.value());
		errorDetail.setTitle("method argument not valid");
		errorDetail.setDetail(request.getDescription(false) + " | parameter: " + ex.getParameter());
		errorDetail.setDevMessage(ex.getBindingResult().toString());
		errorDetail.setErrors(helper.getConstraintViolation(ex));

		return new ResponseEntity<>(errorDetail,null, status);
	}
}