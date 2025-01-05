package com.BisagN.models.helpdesk;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_htover", uniqueConstraints = {
@UniqueConstraint(columnNames = "id")})
public class Tb_HTover {
	private int id;
	private String ht_type;
	private String ht_reason;
	private String from_army_no;
	private int from_userid;
	private String from_username;
	private int to_userid;
	private String to_username;
	private String to_army_no;
	private String created_by;
	private Date created_on;
	private String auth_letter;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getHt_type() {
		return ht_type;
	}
	public void setHt_type(String ht_type) {
		this.ht_type = ht_type;
	}
	
	public String getHt_reason() {
		return ht_reason;
	}
	public void setHt_reason(String ht_reason) {
		this.ht_reason = ht_reason;
	}
	
	public String getFrom_army_no() {
		return from_army_no;
	}
	public void setFrom_army_no(String from_army_no) {
		this.from_army_no = from_army_no;
	}
	public int getFrom_userid() {
		return from_userid;
	}
	public void setFrom_userid(int from_userid) {
		this.from_userid = from_userid;
	}
	
	public String getFrom_username() {
		return from_username;
	}
	public void setFrom_username(String from_username) {
		this.from_username = from_username;
	}
	
	public int getTo_userid() {
		return to_userid;
	}
	public void setTo_userid(int to_userid) {
		this.to_userid = to_userid;
	}
	
	public String getTo_username() {
		return to_username;
	}
	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}
	
	public String getTo_army_no() {
		return to_army_no;
	}
	public void setTo_army_no(String to_army_no) {
		this.to_army_no = to_army_no;
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
	public String getAuth_letter() {
		return auth_letter;
	}
	public void setAuth_letter(String auth_letter) {
		this.auth_letter = auth_letter;
	}
}
