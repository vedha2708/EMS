package com.BisagN.models;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_ldap_submodule_master", uniqueConstraints = {
@UniqueConstraint(columnNames = "id") })
public class TB_LDAP_SUBMODULE_MASTER {
	
	private int id;
	private String submodule_name;
	private TB_LDAP_MODULE_MASTER module;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSubmodule_name() {
		return submodule_name;
	}
	public void setSubmodule_name(String submodule_name) {
		this.submodule_name = submodule_name;
	}
	
	

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id", nullable = false)
	public TB_LDAP_MODULE_MASTER getModule() {
		return module;
	}
	public void setModule(TB_LDAP_MODULE_MASTER module) {
		this.module = module;
	}
	
	private Set<TB_LDAP_SCREEN_MASTER> screen = new HashSet<TB_LDAP_SCREEN_MASTER>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sub_module")
	public Set<TB_LDAP_SCREEN_MASTER> getScreen() {
		return screen;
	}
	public void setScreen(Set<TB_LDAP_SCREEN_MASTER> screen) {
		this.screen = screen;
	}
	
	private Set<TB_LDAP_ROLEMASTER> sub_module = new HashSet<TB_LDAP_ROLEMASTER>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sub_module")
	public Set<TB_LDAP_ROLEMASTER> getRole_module() {
		return sub_module;
	}
	public void setRole_module(Set<TB_LDAP_ROLEMASTER> sub_module) {
		this.sub_module = sub_module;
	}

}