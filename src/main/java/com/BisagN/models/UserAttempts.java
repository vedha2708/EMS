package com.BisagN.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "userattemptsinformation", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Attempt_Id") }) //,@UniqueConstraint(columnNames = "User_Id")
public class UserAttempts {

	private int id;
	private int userId;
	private int attempts;
	private Date lastModified;
	
	public UserAttempts(){
	}
	
	public UserAttempts(int userId, int attempts, Date lastModified) {
		super();
		this.userId = userId;
		this.attempts = attempts;
		this.lastModified = lastModified;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Attempt_Id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "User_Id",  nullable = false) //unique = true
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Column(name = "attempts", nullable = false, length = 45)
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	@Column(name = "lastModified", nullable = false)
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}