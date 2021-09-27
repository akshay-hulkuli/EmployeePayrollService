package com.bridgelabz.payroll;

import java.time.LocalDate;

public class EmployeePayrollData {
	public int id;
	public String name;
	public char gender;
	public double salary;
	public LocalDate startDate;
	
	public EmployeePayrollData(Integer id, String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary =salary;
	}
	public EmployeePayrollData(Integer id, String name, char gender,  double salary, LocalDate startDate) {
		this(id,name,salary);
		this.gender = gender;
		this.startDate = startDate;
	}
	@Override
	public String toString() {
		return "id: "+this.id+" name: "+this.name+" salary: "+this.salary;
	}
	
}
