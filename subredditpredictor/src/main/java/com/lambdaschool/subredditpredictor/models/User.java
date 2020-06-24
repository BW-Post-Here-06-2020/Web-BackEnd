package com.lambdaschool.subredditpredictor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	@Email
	private String primaryEmail;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "user", allowSetters = true)
	private List<UserRole> roles = new ArrayList<>();

	public User() {
	}

	public User(String username, String password, String primaryEmail, List<UserRole> userRoles) {
		setUsername(username);
		setPassword(password);
		for (UserRole ur : userRoles) {
			ur.setUser(this);
		}
		this.roles = userRoles;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		if (username == null) {
			return null;
		} else {
			return username.toLowerCase();
		}
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPasswordNoEncrypt(String password) {
		this.password = password;
	}

	public void setPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.password = passwordEncoder.encode(password);
	}

	public String getPrimaryEmail() {
		if (primaryEmail == null) {
			return null;
		} else {
			return primaryEmail.toLowerCase();
		}
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail.toLowerCase();
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		roles.add(new UserRole(this, role));
	}

	@JsonIgnore
	public List<SimpleGrantedAuthority> getAuthority() {
		List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

		for (UserRole r : this.roles) {
			String role = "ROLE_" + r.getRole().getName().toUpperCase();
			rtnList.add(new SimpleGrantedAuthority(role));
		}

		return rtnList;
	}
}
