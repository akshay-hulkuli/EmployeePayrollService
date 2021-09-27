package com.bridgelabz.payroll;

import java.util.*;

public class EmployeePayrollService {
	public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO};
	public List<EmployeePayrollData> employeePayrollList;
	private EmployeePayrollDBService employeePayrollDBService;
	public EmployeePayrollService() {
		employeePayrollDBService = EmployeePayrollDBService.getInstance();
	}
	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		this();
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
	
	
	
	public void updateEmployeeSalary(String name, double salary) {
		int result = employeePayrollDBService.updateEmployeeSalary(name, salary);
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
			employeePayrollDBService.writeDB(employeePayrollList);
	}
	
	
	public long readEmployeePayrollData(IOService ioService) {
		List<String> payrollList = null;
		if(ioService.equals(IOService.FILE_IO))
			payrollList = new EmployeePayrollFileIOService().readData();
		return payrollList.size();
	}
	public List<EmployeePayrollData> readEmployeePayrollDataDB(IOService type){
		if(type.equals(IOService.DB_IO)) {
			this.employeePayrollList = employeePayrollDBService.readData();
		}
		return this.employeePayrollList;
	}
	
	public boolean checkEmployeePayrollInsyncWithDB(String name) {
		List<EmployeePayrollData> employeePayrollDataList =  employeePayrollDBService.getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
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
			return employeePayrollDBService.countEntries();
		}
		
		return 0;
	}
	
	public List<EmployeePayrollData> getEmpInADateRange(String date1, String date2){
		return employeePayrollDBService.getEmployeesInDateRange(date1, date2);
	}
}
