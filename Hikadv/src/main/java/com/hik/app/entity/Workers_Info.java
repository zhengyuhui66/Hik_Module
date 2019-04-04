package com.hik.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="workers_info")
public class Workers_Info implements Serializable{
	public Workers_Info(){}
	public Workers_Info(Integer id, String workername, String salary,
			String email, String employedDates, String department) {
		super();
		this.id = id;
		this.workername = workername;
		this.salary = salary;
		this.email = email;
		EmployedDates = employedDates;
		this.department = department;
	}
	private Integer id;
	private String workername;
	private String salary;
	private String email;
	private String EmployedDates;
	private String department;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(length=20,nullable=true)
	public String getWorkername() {
		return workername;
	}

	public void setWorkername(String workername) {
		this.workername = workername;
	}
	@Column(length=20,nullable=true)
	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	@Column(length=20,nullable=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=20,nullable=true)
	public String getEmployedDates() {
		return EmployedDates;
	}

	public void setEmployedDates(String employedDates) {
		EmployedDates = employedDates;
	}
	@Column(length=20,nullable=true)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department){
		this.department = department;
	}
    @Override
	public String toString() {
		return "Workers_Info [id=" + id + ", workername=" + workername
				+ ", salary=" + salary + ", email=" + email
				+ ", EmployedDates=" + EmployedDates + ", department="
				+ department + ", getId()=" + getId() + ", getWorkername()="
				+ getWorkername() + ", getSalary()=" + getSalary()
				+ ", getEmail()=" + getEmail() + ", getEmployedDates()="
				+ getEmployedDates() + ", getDepartment()=" + getDepartment()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	
}
