package com.bridgelabz.payroll;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.bridgelabz.payroll.EmployeePayrollService.IOService.*;
public class EmployeePayrollServiceTest {
    int size = 3;
	@Test
	public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmps = {
				new EmployeePayrollData(1, "Jeff Bezos", 10000),
				new EmployeePayrollData(2, "Bill Gates", 20000),
				new EmployeePayrollData(3, "Mark Zuckerberg", 30000)
		};
		
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(FILE_IO);
		employeePayrollService.printData(FILE_IO);
		long entries = employeePayrollService.countEntries(FILE_IO);
		Assert.assertEquals(3,entries);
	}
	
	@Test
	public void givenFileOnReadingFromMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		long entries = employeePayrollService.readEmployeePayrollData(FILE_IO);
		Assert.assertEquals(3,entries);
	}
	
	@Test
	public void givenEmployeePayrollInDB_WhenRetrived_ShouldReturnEmployeeCount()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		Assert.assertEquals(size, employeePayrollData.size());
	}

	
	@Test
	public void givenListOfEmployees_WhenInserted_ShouldMatchEmployeeEntries() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date = "16/08/2019";
		LocalDate startDate = LocalDate.parse(date, formatter);
		size += 1;
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(new ArrayList<>()); 
		employeePayrollService.addEmployeeToPayroll("arun",100000.00,startDate,'M' );
		boolean result = employeePayrollService.checkEmployeePayrollInsyncWithDB("arun");
		Assert.assertTrue(result);
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
	
}
