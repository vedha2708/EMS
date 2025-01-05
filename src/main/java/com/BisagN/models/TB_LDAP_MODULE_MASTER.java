package com.BisagN.models;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_ldap_module_master", uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class TB_LDAP_MODULE_MASTER {
	
	private int id;
	private String module_name;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModule_name() {
		return module_name;
	}
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	
	private Set<TB_LDAP_SUBMODULE_MASTER> sub_module = new HashSet<TB_LDAP_SUBMODULE_MASTER>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	public Set<TB_LDAP_SUBMODULE_MASTER> getSub_module() {
		return sub_module;
	}
	public void setSub_module(Set<TB_LDAP_SUBMODULE_MASTER> sub_module) {
		this.sub_module = sub_module;
	}
	
	private Set<TB_LDAP_SCREEN_MASTER> screen = new HashSet<TB_LDAP_SCREEN_MASTER>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	public Set<TB_LDAP_SCREEN_MASTER> getScreen() {
		return screen;
	}
	public void setScreen(Set<TB_LDAP_SCREEN_MASTER> screen) {
		this.screen = screen;
	}
	
	private Set<TB_LDAP_ROLEMASTER> role_module = new HashSet<TB_LDAP_ROLEMASTER>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
	public Set<TB_LDAP_ROLEMASTER> getRole_module() {
		return role_module;
	}
	public void setRole_module(Set<TB_LDAP_ROLEMASTER> role_module) {
		this.role_module = role_module;
	}

}