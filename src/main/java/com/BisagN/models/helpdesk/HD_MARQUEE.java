package com.BisagN.models.helpdesk;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "hd_tb_mercuries", uniqueConstraints = {
@UniqueConstraint(columnNames = "id"),})
public class HD_MARQUEE {
	
	private int id;
	private int userid;
	private String created_by;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date created_on;
	private String msg;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date valid_upto;
	private String status;
		

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getValid_upto() {
		return valid_upto;
	}
	public void setValid_upto(Date valid_upto) {
		this.valid_upto = valid_upto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
