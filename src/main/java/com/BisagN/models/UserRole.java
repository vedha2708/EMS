package com.BisagN.models;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "userroleinformation", uniqueConstraints = {
		@UniqueConstraint(columnNames = "User_Role_Id"),
		@UniqueConstraint(columnNames = "User_Id") })
public class UserRole {
	private int id;
	private int userId;
	private int roleId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "User_Role_Id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name = "User_Id", unique = true, nullable = false)
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Column(name = "Role_Id", nullable = false)
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
}
