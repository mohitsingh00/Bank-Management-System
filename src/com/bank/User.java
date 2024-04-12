package com.bank;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
		System.out.print("Full Name: ");
		String full_name = scanner.nextLine();
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();
		if(user_exist(email))
		{
            System.out.println("User Already Exists for this Email Address!!\n");
            return;
        }
		
		 // Hash the password
	    String hashedPassword = hashPassword(password);
		
		String register_query = "INSERT INTO User(full_name, email, password) VALUES(?,?,?)";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(register_query);
			preparedStatement.setString(1, full_name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, hashedPassword);
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
	
	// Method to hash the password
	private String hashPassword(String password) 
	{
		try 
	    {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hashedBytes = md.digest(password.getBytes());
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashedBytes)
	        {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public String login()
	{
		scanner.nextLine();
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();
		
		// Hash the entered password
	    String hashedPassword = hashPassword(password);
	    
		String login_query = "SELECT * FROM User WHERE email = ? AND password = ?";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(login_query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, hashedPassword);
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
	
	public boolean user_exist(String email)
	{
		String query = "SELECT * FROM user WHERE email = ?";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
 	}

}
