package com.bridgelabz.payroll;

import java.time.LocalDate;
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
	}
	
	public void addEmployeeToPayroll(String name, Double salary, LocalDate startDate,char gender,String address, String phoneNumber, String deptId, int companyId) {
		employeePayrollList.add(employeePayrollDBService.addEmployeeToPayroll(name,salary,startDate,gender,address,phoneNumber,deptId,companyId));
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
	
	public Map<Character, Double> getGenderWiseTotalSalary(IOService type){
		HashMap<Character,Double> salaryMap = new HashMap<>();
		if(type.equals(IOService.DB_IO))
			salaryMap = employeePayrollDBService.getGenderWiseTotalSalary();
		return salaryMap;
	}
	public Map<Character, Double> getGenderWiseMinSalary(IOService type){
		HashMap<Character,Double> salaryMap = new HashMap<>();
		if(type.equals(IOService.DB_IO))
			salaryMap = employeePayrollDBService.getGenderWiseMinSalary();
		return salaryMap;
	}
	public Map<Character, Double> getGenderWiseMaxSalary(IOService type){
		HashMap<Character,Double> salaryMap = new HashMap<>();
		if(type.equals(IOService.DB_IO))
			salaryMap = employeePayrollDBService.getGenderWiseMaxSalary();
		return salaryMap;
	}
	public Map<Character, Double> getGenderWiseAvgSalary(IOService type){
		HashMap<Character,Double> salaryMap = new HashMap<>();
		if(type.equals(IOService.DB_IO))
			salaryMap = employeePayrollDBService.getGenderWiseAvgSalary();
		return salaryMap;
	}
	public Map<Character, Integer> getGenderWiseCount(IOService type){
		HashMap<Character,Integer> countMap = new HashMap<>();
		if(type.equals(IOService.DB_IO))
			countMap = employeePayrollDBService.getGenderWiseCount();
		return countMap;
	}
	
	public int insertDepartment(Department dept) {
		return employeePayrollDBService.insertDepartment(dept);
	}
	public int insertCompany(Company company) {
		return employeePayrollDBService.insertCompany(company);
	}
	
	public int deleteEmployee(String name) {
		return employeePayrollDBService.deleteEmployee(name, this.employeePayrollList);
	}
	
}
