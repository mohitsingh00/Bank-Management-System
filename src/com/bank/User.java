package com.bank;

import java.sql.Connection;
import java.util.Scanner;

public class User {
	
	private Connection connection;
	private Scanner scanner;
	
	public User(Connection conneciton, Scanner scanner)
	{
		this.connection = conneciton;
		this.scanner = scanner;
	}

}
