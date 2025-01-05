package com.BisagN.Model.Master;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tbl_SalaryStructure", uniqueConstraints = {
		@UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "id") })

public class TBL_SALARY_STRUCTURE {
	
	private int id;
	private String salary_month;
	private String Earnings;
	private String Deduction;
	private String Netsalary;
	private String Action;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSalary_month() {
		return salary_month;
	}
	public void setSalary_month(String salary_month) {
		this.salary_month = salary_month;
	}
	public String getEarnings() {
		return Earnings;
	}
	public void setEarnings(String earnings) {
		Earnings = earnings;
	}
	public String getDeduction() {
		return Deduction;
	}
	public void setDeduction(String deduction) {
		Deduction = deduction;
	}
	public String getNetsalary() {
		return Netsalary;
	}
	public void setNetsalary(String netsalary) {
		Netsalary = netsalary;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	
		
	
	
	
	
	
	

}

