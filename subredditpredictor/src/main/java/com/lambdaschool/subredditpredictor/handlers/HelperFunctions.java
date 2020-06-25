package com.lambdaschool.subredditpredictor.handlers;

import com.lambdaschool.subredditpredictor.exceptions.ResourceNotFoundException;
import com.lambdaschool.subredditpredictor.models.ValidationError;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HelperFunctions {
	public boolean isAuthorizedToMakeChange(String username) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (
			username.equalsIgnoreCase(authentication.getName().toLowerCase()) ||
			authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
		) {
			return true;
		} else {
			throw new ResourceNotFoundException(authentication.getName() + " not authorized to make this change");
		}
	}

	public List<ValidationError> getConstraintViolation(Throwable cause) {
		while ((cause != null) && !(cause instanceof ConstraintViolationException)) {
			cause = cause.getCause();
		}

		List<ValidationError> listVE = new ArrayList<>();

		if (cause != null) {
			ConstraintViolationException ex = (ConstraintViolationException)cause;
			for (ConstraintViolation cv : ex.getConstraintViolations()) {
				ValidationError newVE = new ValidationError();
				newVE.setCode(cv.getInvalidValue().toString());
				newVE.setMessage(cv.getMessage());
				listVE.add(newVE);
			}
		}
		return listVE;
	}
}
