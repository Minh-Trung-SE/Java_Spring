package com.bookweb.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class SpringWebApplication {
	public  static Connection connection;

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found !!");
		}
		System.out.println("MySQL JDBC Driver Registered!");
		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/book?characterEncoding=utf8", "root", "Minhtrung1772k1");
			System.out.println("Connection Successful!");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console"  + e);
		}
	}

}
