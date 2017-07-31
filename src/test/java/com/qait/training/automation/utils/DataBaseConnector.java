package com.qait.training.automation.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnector {
	Connection con;
	ResultSet rst;
	Statement stat;
	ResultSetMetaData rsMdata;
	
   public  void connectToDataBase(String host, String databaseName, String username, String password){
	   try {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://"+host+"/"+databaseName, username, password);
		stat = con.createStatement();
	} catch (Exception e) {
		e.printStackTrace();
	}
	   
   }
   
   
   public ResultSet getResultSetOnExecutingASelectQuery(String query){
	   try {
		   	rst = stat.executeQuery(query);
		   
	   } catch (SQLException e) {
			e.printStackTrace();
		}
	   return rst;
   }

}
