package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserConnector {
	
	public static final int USER_NOT_FOUND = 1;
	public static final int PASSWORD_INCORRECT = 2;
	public static final int USER_ADMIN = 3;
	public static final int USER_NORMAL = 4;
	
	Connection connection;
	Statement statement;
	
	public UserConnector() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/InvMan", "Employee", "Inv808");
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	public int checkUser(String user, String password) throws SQLException {
		ResultSet rs = statement.executeQuery("select user_password, user_admin from tbl_user where user_name = '"+user+"'");
		if(rs.next() == false) {
			return USER_NOT_FOUND;
		}
		if(!rs.getString(1).equals(password)) {
			return PASSWORD_INCORRECT;
		}
		if(rs.getInt(2) == 0) {
			return USER_NORMAL;
		} else {
			return USER_ADMIN;
		}
	}
}
