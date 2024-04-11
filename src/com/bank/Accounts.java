package com.bank;

import java.sql.Connection;
import java.util.Scanner;

public class Accounts {

	private Connection connection;
	private Scanner scanner;
	
	public Accounts(Connection conneciton, Scanner scanner)
	{
		this.connection = conneciton;
		this.scanner = scanner;
	}
}
