package com.BisagN.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "logininformation", uniqueConstraints = {
		@UniqueConstraint(columnNames = "userid"),
		@UniqueConstraint(columnNames = "username") })
public class UserLogin {
	private int userId;
	private String userName;
	private String password;
	private int enabled;
	private int accountNonExpired;
	private int accountNonLocked;
	private int credentialsNonExpired;	
	private String ac_dc_date;
	private String login_name;
	//private String army_no;
	private String created_by;
	private Date created_on;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userid", unique = true, nullable = false)
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Column(name = "username", unique = true, nullable = false, length = 45)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "password", nullable = false, length = 45)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "enabled", nullable = false, length = 4)
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	@Column(name = "accountNonExpired", nullable = false, length = 4)
	public int getAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(int accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	@Column(name = "accountNonLocked", nullable = false, length = 4)
	public int getAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(int accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	@Column(name = "credentialsNonExpired", nullable = false, length = 4)
	public int getCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(int credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getAc_dc_date() {
		return ac_dc_date;
	}
	public void setAc_dc_date(String ac_dc_date) {
		this.ac_dc_date = ac_dc_date;
	}
//	public String getArmy_no() {
//		return army_no;
//	}
//	public void setArmy_no(String army_no) {
//		this.army_no = army_no;
//	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Date getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	
	public UserLogin(int userId, String userName, String password,String login_name) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.login_name =login_name;
	}
	public UserLogin() {
		super();
	}
	
}
