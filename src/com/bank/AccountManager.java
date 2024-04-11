package com.bank;

import java.sql.Connection;
import java.util.Scanner;

public class AccountManager {

	private Connection connection;
	private Scanner scanner;
	
	public AccountManager(Connection conneciton, Scanner scanner)
	{
		this.connection = conneciton;
		this.scanner = scanner;
	}
}
