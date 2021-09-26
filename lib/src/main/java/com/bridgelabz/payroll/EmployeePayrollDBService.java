package com.bridgelabz.payroll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import java.sql.*;

public class EmployeePayrollDBService {


	
	public List<EmployeePayrollData> readData() {
		String sql = "SELECT * FROM employee_payroll";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try {
			Connection connection = this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("salary");
				LocalDate startDate = result.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary,startDate));
			}
			connection.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
	public Connection getConnection() throws SQLException
	{
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?userSSL=false";
		String userName = "root";
		String password = "Root$241";
		Connection connection;
		System.out.println("Connecting to database:"+jdbcURL);
		connection =  (Connection) DriverManager.getConnection(jdbcURL,userName,password);
		System.out.println("Connection is successful!!!!"+connection);
		return connection;
	
	}
	
	public int updateEmployeeSalary(String name, double salary) {
		return this.updateEmployeeDataUsingStatement(name,salary);
	}
	
	private int updateEmployeeDataUsingStatement(String name,double salary) {
		String sqlString = String.format("update employee_payroll set salary = %2f where name = '%s';",salary,name);
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sqlString);
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	
	}


}