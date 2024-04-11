package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

	private Connection connection;
	private Scanner scanner;
	
	public AccountManager(Connection conneciton, Scanner scanner)
	{
		this.connection = conneciton;
		this.scanner = scanner;
	}
	
	public void debit_money(long account_number) throws SQLException
	{
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		scanner.nextLine();
		System.out.println("Enter Security Pin: ");
		String security_pin = scanner.nextLine();
		try
		{
			if(account_number != 0)
			{
				connection.setAutoCommit(false);
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?");
				preparedStatement.setLong(1, account_number);
				preparedStatement.setString(2, security_pin);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next())
				{
					double current_balance = resultSet.getDouble("balance");
					if(amount <= current_balance)
					{
						String credit_query = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
						PreparedStatement preparedStatement1 = connection.prepareStatement(credit_query);
						preparedStatement1.setDouble(1, amount);
						preparedStatement1.setLong(2, account_number);
						int rowsAffected = preparedStatement.executeUpdate();
						if(rowsAffected > 0)
						{
							System.out.println("RS."+amount+" debited Successfully");
							connection.commit();
							connection.setAutoCommit(true);
							return;
						}
						else
						{
							System.out.println("Transection Failed!");
							connection.rollback();
							connection.setAutoCommit(false);
						}
					}
					else
					{
						System.out.println("Insufficient Balance!");
					}
				}
				else
				{
					System.out.println("Invalid Pin!");
				}
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		connection.setAutoCommit(true);
	}
	
}
