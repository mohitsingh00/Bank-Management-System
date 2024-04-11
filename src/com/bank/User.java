package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
	
	private Connection connection;
	private Scanner scanner;
	
	public User(Connection conneciton, Scanner scanner)
	{
		this.connection = conneciton;
		this.scanner = scanner;
	}
	
	public void register()
	{
		scanner.nextLine();
		System.out.println("Full Name: ");
		String full_name = scanner.nextLine();
		System.out.println("Email: ");
		String email = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		
		
		String register_query = "INSERT INTO User(full_name, email, password) VALUES(?,?,?)";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(register_query);
			preparedStatement.setString(1, full_name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows > 0)
			{
				System.out.println("Registration Successfull!");
			}
			else
			{
				System.out.println("Registration Failed!");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	public String login()
	{
		scanner.nextLine();
		System.out.println("Email: ");
		String email = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		String login_query = "SELECT * FROM User WHERE email = ? AND password = ?";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(login_query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultset = preparedStatement.executeQuery();
			if(resultset.next())
			{
				return email;
			}
			else
			{
				return null; 
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void user_exist()
	{
		
	}

}
