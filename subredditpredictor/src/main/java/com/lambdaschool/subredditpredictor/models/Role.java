package com.lambdaschool.subredditpredictor.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roleid;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "role", allowSetters = true)
	private List<UserRole> users = new ArrayList<>();

	public Role() {
	}

	public Role(String name) {
		this.name = name.toUpperCase();
	}

	public long getRoleid() {
		return roleid;
	}

	public void setRoleid(long roleId) {
		this.roleid = roleId;
	}

	public String getName() {
		if (name == null) {
			return null;
		} else {
			return name.toUpperCase();
		}
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public List<UserRole> getUsers() {
		return users;
	}

	public void setUsers(List<UserRole> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Role{" +
			"roleId=" + roleid +
			", name='" + name + '\'' +
			", users=" + users +
			'}';
	}
}
