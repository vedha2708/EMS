package com.BisagN.models;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "roleinformation", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Role_Id"),
		@UniqueConstraint(columnNames = "ROLE") })
public class Role {
	private int roleId;
	
//	@NotEmpty(message = "{error.role.required}")
//	@NotBlank(message = "{error.role.required}")
	@Column(unique = true)
	private String role;
	private String role_url;
	private String role_type;
	private String access_lvl;
	private String sub_access_lvl;
	private String staff_lvl;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Role_Id", unique = true, nullable = false)
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "ROLE", unique = true, nullable = false, length=45)
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Column(name = "role_url", nullable = false)
	public String getRole_url() {
		return role_url;
	}
	public void setRole_url(String role_url) {
		this.role_url = role_url;
	}
	
	@Column(name = "role_type", nullable = false)
	public String getRole_type() {
		return role_type;
	}
	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}
	public String getAccess_lvl() {
		return access_lvl;
	}
	public void setAccess_lvl(String access_lvl) {
		this.access_lvl = access_lvl;
	}
	public String getSub_access_lvl() {
		return sub_access_lvl;
	}
	public void setSub_access_lvl(String sub_access_lvl) {
		this.sub_access_lvl = sub_access_lvl;
	}
	public String getStaff_lvl() {
		return staff_lvl;
	}
	public void setStaff_lvl(String staff_lvl) {
		this.staff_lvl = staff_lvl;
	}
		
}
