package com.bridgelabz.payroll;

public class Department {
	private String departmentName;
	private String departmentId;
	private String hod;
	
	public Department(String name, String id, String hod) {
		this.setDepartmentId(id);
		this.setDepartmentName(name);
		this.setHod(hod);
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getHod() {
		return hod;
	}

	public void setHod(String hod) {
		this.hod = hod;
	}
}
