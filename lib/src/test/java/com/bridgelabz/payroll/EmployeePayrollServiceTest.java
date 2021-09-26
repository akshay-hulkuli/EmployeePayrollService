package com.bridgelabz.payroll;

import org.junit.Assert;
import org.junit.Test;
import java.util.*;
import static com.bridgelabz.payroll.EmployeePayrollService.IOService.*;
public class EmployeePayrollServiceTest {

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
	public void givenEmployeePayrollInDB_WhenRetrived_ShouldatchEmployeeCount()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		Assert.assertEquals(3, employeePayrollData.size());
	}
	@Test
	public void givenNewSalaryForEmpoyee_WhenUpdated_ShouldSyncWithDB()
	{
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		employeePayrollService.updateEmployeeSalary("Tanisha",5000000.00);
		employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
		System.out.println(employeePayrollData);
	}
}
