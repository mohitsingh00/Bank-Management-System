package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Accounts {

	private Connection connection;
	private Scanner scanner;
	
	
	public Accounts(Connection connection, Scanner scanner)
	{
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public long openAccount(String email)
	{
		if(!account_exist(email))
		{
			String open_account_query = "INSERT INTO Accounts(account_number, full_name,"
					                  + " email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";
			
			scanner.nextLine();
			System.out.print("Enter Full Name: ");
			String full_name = scanner.nextLine();
			System.out.print("Enter Initial Amount: ");
			double balance = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Enter Security Pin: ");
			String security_pin = scanner.nextLine();
			
			try
			{
				long account_number = generateAccount_number();
				PreparedStatement preparedStatement = connection.prepareStatement(open_account_query);
				preparedStatement.setLong(1, account_number);
				preparedStatement.setString(2, full_name);
				preparedStatement.setString(3, email);
				preparedStatement.setDouble(4, balance);
				preparedStatement.setString(5, security_pin);
				int rowsAffected = preparedStatement.executeUpdate();
				if(rowsAffected > 0)
				{
					return account_number;
				}
				else
				{
					throw new RuntimeException("Account Creation Failed");
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
				throw new RuntimeException("Account Creation Failed");
			}
			
		}
		else
		{
			throw new RuntimeException("Account Already Exist");
		}
	}
	
	public long getAccount_number(String email)
	{
		String query = "SELECT account_number FROM Accounts WHERE email = ?;";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				return resultSet.getLong("account_number");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		throw new RuntimeException("Account Number Doesn't Exist!");
	}
	
	private long generateAccount_number()
	{
		Random random = new Random();
		long account_number;
		do 
		{
			account_number = 1000000000L + random.nextInt(900000000);
		}
		while(accountExist(account_number));
		{
			return account_number;
		}
	}
	
	private boolean accountExist(long account_number)
	{
		String query = "SELECT account_number FROM Accounts WHERE account_number = ?;";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, account_number);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean account_exist(String email)
	{
		String query = "SELECT account_number FROM Accounts WHERE email = ?";
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
