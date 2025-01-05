package com.BisagN.models;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_ldap_user_master", uniqueConstraints = {
@UniqueConstraint(columnNames = "id") })
public class TB_LDAP_USER_MASTER {
	private int id;
	private String user_name;
	private String user_password;	
	private String user_re_password;
	private String user_sus_no;
	private int user_role_id;
	private String user_status;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_re_password() {
		return user_re_password;
	}
	public void setUser_re_password(String user_re_password) {
		this.user_re_password = user_re_password;
	}
	public String getUser_sus_no() {
		return user_sus_no;
	}
	public void setUser_sus_no(String user_sus_no) {
		this.user_sus_no = user_sus_no;
	}
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	public String getUser_status() {
		return user_status;
	}
	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}
	
	

}
