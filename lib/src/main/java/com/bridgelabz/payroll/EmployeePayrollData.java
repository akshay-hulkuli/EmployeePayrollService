package com.bridgelabz.payroll;

public class EmployeePayrollData {
	public int id;
	public String name;
	public double salary;
	
	public EmployeePayrollData(Integer id, String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary =salary;
	}
	@Override
	public String toString() {
		return "id: "+this.id+" name: "+this.name+" salary: "+this.salary;
	}
}
