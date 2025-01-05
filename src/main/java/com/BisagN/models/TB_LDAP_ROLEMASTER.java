package com.BisagN.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_ldap_rolemaster", uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class TB_LDAP_ROLEMASTER{
	
	private int id;
	private int roleid;
	//private int moduleid;
	//private int submoduleid;
	//private int screenid;
	private String creation_by;
	private Date creation_date;
	//private String modify_by;
	//private Date modify_date;
	
	private TB_LDAP_MODULE_MASTER module;
	private TB_LDAP_SUBMODULE_MASTER sub_module;
	private TB_LDAP_SCREEN_MASTER screen;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
/*	public int getModuleid() {
		return moduleid;
	}
	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}*/
	/*public int getSubmoduleid() {
		return submoduleid;
	}
	public void setSubmoduleid(int submoduleid) {
		this.submoduleid = submoduleid;
	}*/
	/*public int getScreenid() {
		return screenid;
	}
	public void setScreenid(int screenid) {
		this.screenid = screenid;
	}*/
	public String getCreation_by() {
		return creation_by;
	}
	public void setCreation_by(String creation_by) {
		this.creation_by = creation_by;
	}
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	
/*	public String getModify_by() {
		return modify_by;
	}
	public void setModify_by(String modify_by) {
		this.modify_by = modify_by;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moduleid", nullable = false)
	public TB_LDAP_MODULE_MASTER getModule() {
		return module;
	}
	public void setModule(TB_LDAP_MODULE_MASTER module) {
		this.module = module;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "submoduleid", nullable = false)
	public TB_LDAP_SUBMODULE_MASTER getSub_module() {
		return sub_module;
	}
	public void setSub_module(TB_LDAP_SUBMODULE_MASTER sub_module) {
		this.sub_module = sub_module;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "screenid", nullable = false)
	public TB_LDAP_SCREEN_MASTER getScreen() {
		return screen;
	}
	public void setScreen(TB_LDAP_SCREEN_MASTER screen) {
		this.screen = screen;
	}
}