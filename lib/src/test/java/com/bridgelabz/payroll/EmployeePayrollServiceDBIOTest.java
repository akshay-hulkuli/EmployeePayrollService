package com.bridgelabz.payroll;

import static com.bridgelabz.payroll.EmployeePayrollService.IOService.DB_IO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollServiceDBIOTest {
	int size = 3;
	@Test
	public void givenEmployeePayrollInDB_WhenRetrived_ShouldReturnEmployeeCount()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		Assert.assertEquals(size, employeePayrollData.size());
	}

	
	@Test
	public void givenEmployee_WhenInserted_ShouldMatchEmployeeEntries() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2019";
		LocalDate startDate = LocalDate.parse(date, formatter);
		size += 1;
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(new ArrayList<>()); 
		employeePayrollService.addEmployeeToPayroll("arun",100000.00,startDate,'M',"bengaluru","8974561236","D001",1);
		boolean result = employeePayrollService.checkEmployeePayrollInsyncWithDB("arun");
		Assert.assertTrue(result);
	}
	
	@Test
	public void givenDepartment_WhenInserted_ShouldReturnOne(){
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		int result = employeePayrollService.insertDepartment(new Department( "D004","Engineering" ,"Virat"));
		Assert.assertEquals(1,result);
	}
	@Test
	public void givenCompany_WhenInserted_ShouldReturnOne(){
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		int result = employeePayrollService.insertCompany(new Company( "Uber",2));
		Assert.assertEquals(1,result);
	}
	
	@Test
	public void givenNewSalaryForEmpoyee_WhenUpdated_ShouldSyncWithDB()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		employeePayrollService.updateEmployeeSalary("Tanisha",5000000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInsyncWithDB("Tanisha");
		Assert.assertTrue(result);
	}
	
	@Test
	public void givenDateRange_WhenQueried_ShouldReturnEmployeeList()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> empList = employeePayrollService.getEmpInADateRange("2019-01-01","2021-01-01");
		System.out.println(empList);
		Assert.assertEquals(2, empList.size());
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnSumOfSalaryBasedOnGender() {
		
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		Map<Character, Double> salaryMap = employeePayrollService.getGenderWiseTotalSalary(DB_IO);
		Assert.assertEquals((double)salaryMap.get('F'),5000000,0.0);
		Assert.assertEquals((double)salaryMap.get('M'),400000,0.0);
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnMinSalaryBasedOnGender() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		Map<Character, Double> salaryMap = employeePayrollService.getGenderWiseMinSalary(DB_IO);
		Assert.assertEquals((double)salaryMap.get('F'),5000000,0.0);
		Assert.assertEquals((double)salaryMap.get('M'),100000,0.0);
		
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnAverageSalaryBasedOnGender() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		Map<Character, Double> salaryMap = employeePayrollService.getGenderWiseAvgSalary(DB_IO);
		Assert.assertEquals((double)salaryMap.get('F'),5000000,0.0);
		Assert.assertEquals((double)salaryMap.get('M'),200000,0.0);
		
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnMaximumSalaryBasedOnGender() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		Map<Character, Double> salaryMap = employeePayrollService.getGenderWiseMaxSalary(DB_IO);
		Assert.assertEquals((double)salaryMap.get('F'),5000000,0.0);
		Assert.assertEquals((double)salaryMap.get('M'),300000,0.0);
		
	}
	
	@Test
	public void givenEmployeePayrollInDB_ShouldReturnCountOfBasedOnGender() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		Map<Character, Integer> countMap = employeePayrollService.getGenderWiseCount(DB_IO);
		Assert.assertEquals((int)countMap.get('F'),1);
		Assert.assertEquals((int)countMap.get('M'),2);
		
	}
	
	@Test
	public void givenEmployeeName_WhenDeleted_ShouldBecomeInactive() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(new ArrayList<>()); 
		employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		int result = employeePayrollService.deleteEmployee("Tanisha");
		Assert.assertTrue(result>0);
	}
}
