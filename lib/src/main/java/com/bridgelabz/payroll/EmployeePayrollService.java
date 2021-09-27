package com.bridgelabz.payroll;

import java.util.*;

public class EmployeePayrollService {
	public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO};
	private List<EmployeePayrollData> employeePayrollList;
	
	public EmployeePayrollService() {}
	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}
	
	public static void main(String[] args) {
	List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
	EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
	Scanner consoleInputReader = new Scanner(System.in);
	employeePayrollService.readEmployeePayrollData(consoleInputReader);
	employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
	}
	
	private void readEmployeePayrollData(Scanner consoleInputReader) {
		System.out.println("enter employee ID:");
		int id = consoleInputReader.nextInt();
		System.out.println("enter employee name :");
		String name  = consoleInputReader.next();
		System.out.println("Enter employee salary: ");
		double salary = consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	
	public List<EmployeePayrollData> readEmployeePayrollDataDB(IOService type){
		if(type.equals(IOService.DB_IO)) {
			this.employeePayrollList = new EmployeePayrollDBService().readData();
		}
		return this.employeePayrollList;
	}
	
	public void updateEmployeeSalary(String name, double salary) {
		int result = new EmployeePayrollDBService().updateEmployeeSalary(name, salary);
		if (result == 0) return;
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if( employeePayrollData != null) employeePayrollData.salary = salary;
	}
	
	private EmployeePayrollData getEmployeePayrollData(String name) {
		return this.employeePayrollList.stream()
				   .filter(employeePayrolldata -> employeePayrolldata.name.equals(name))
				   .findFirst()
				   .orElse(null);
	}
	public void writeEmployeePayrollData(IOService ioService) {
		if(ioService.equals(IOService.CONSOLE_IO))
		System.out.println("\n writing Employee payroll roaster to console \n"+ employeePayrollList);
		else if(ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
		else if(ioService.equals(IOService.DB_IO))
			new EmployeePayrollDBService().writeDB(employeePayrollList);
	}
	
	
	public long readEmployeePayrollData(IOService ioService) {
		List<String> payrollList = null;
		if(ioService.equals(IOService.FILE_IO))
			payrollList = new EmployeePayrollFileIOService().readData();
		return payrollList.size();
	}
	
	public void printData(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().printData();
		}
	}
	
	public long countEntries(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			return new EmployeePayrollFileIOService().countEntries();
		}
		else if(ioService.equals(IOService.DB_IO)) {
			return new EmployeePayrollDBService().countEntries();
		}
		
		return 0;
	}
}
