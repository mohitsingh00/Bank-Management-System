package com.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankApp {
	
	private static final String url = "jdbc:mysql://localhost:3306/carrentalsystem";
	private static final String username = "root";
	private static final String password = "Mohit@123";
	
	public static void main(String[] args) {
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		try
		{
			Connection connection = DriverManager.getConnection(url, username, password);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
