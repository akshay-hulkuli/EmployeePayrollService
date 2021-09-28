package com.bridgelabz.payroll;

public class Company {
	private String companyName;
	private int companyId;
	
	public Company(String name, int id) {
		this.setCompanyId(id);
		this.setCompanyName(name);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
