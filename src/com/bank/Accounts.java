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
	
	
	public Accounts(Connection conneciton, Scanner scanner)
	{
		this.connection = conneciton;
		this.scanner = scanner;
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
