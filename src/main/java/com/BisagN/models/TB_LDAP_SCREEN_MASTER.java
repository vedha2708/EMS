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
@Table(name = "tb_ldap_screen_master", uniqueConstraints = {
@UniqueConstraint(columnNames = "id") })
public class TB_LDAP_SCREEN_MASTER {
	
	private int id;
	private String screen_name;
	private String screen_url;

	private TB_LDAP_MODULE_MASTER module;
	private TB_LDAP_SUBMODULE_MASTER sub_module;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getScreen_url() {
		return screen_url;
	}
	public void setScreen_url(String screen_url) {
		this.screen_url = screen_url;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "screen_module_id", nullable = false)
	public TB_LDAP_MODULE_MASTER getModule() {
		return module;
	}
	public void setModule(TB_LDAP_MODULE_MASTER module) {
		this.module = module;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "screen_submodule_id", nullable = false)
	public TB_LDAP_SUBMODULE_MASTER getSub_module() {
		return sub_module;
	}
	public void setSub_module(TB_LDAP_SUBMODULE_MASTER sub_module) {
		this.sub_module = sub_module;
	}
	
	private Set<TB_LDAP_ROLEMASTER> screen = new HashSet<TB_LDAP_ROLEMASTER>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "screen")
	public Set<TB_LDAP_ROLEMASTER> getRole_module() {
		return screen;
	}
	public void setRole_module(Set<TB_LDAP_ROLEMASTER> screen) {
		this.screen = screen;
	}

}