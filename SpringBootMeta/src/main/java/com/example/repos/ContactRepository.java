package com.example.repos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.example.mapper.ContactMapper;
import com.example.model.Contact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ContactRepository 
{

	@Autowired
    JdbcTemplate template;
	
	
	static Connection crunchifyConn = null;
	static PreparedStatement crunchifyPrepareStat = null;
	
	
	private static void log(String string) {
		System.out.println(string);
 
	}
	

    /*Getting all Contacts from table*/
    public List<Contact> getAllContacts(){
        List<Contact> items = template.query("select id, name, mobile, emailid, category from contact",(result,rowNum)->new Contact(result.getInt("id"),
                result.getString("name"),result.getString("mobile"),result.getString("emailid"),result.getString("category")));
        return items;
    }
    /*Getting a specific item by item id from table*/
    public Contact getContact(int id){
        String query = "SELECT * FROM contact WHERE id = "+id;
        Contact item = template.queryForObject(query, new ContactMapper());
        //Contact item = template.queryForObject(query,new Object[]{id},new BeanPropertyRowMapper<>(Contact.class));

        return item;
    }
    /*Adding an item into database table*/
    public int addContact(String name,String mobile,String emailid,String category){
        String query = "INSERT INTO contact (name,mobile,emailid,category) VALUES(?,?,?,?)";
        return template.update(query,name,mobile,emailid,category);
    }
    /*delete an item from database*/
    public int deleteContact(int id){
        String query = "DELETE FROM contact WHERE ID =?";
        return template.update(query,id);
    }
    
    
	public void sourceMeta() {
		makeTargetJDBCConnection();
		ArrayList<String> list=new ArrayList<>();
		list.add("contact");
		list.add("emp99");
		list.add("user99");
		list.add("student99");
		
		for(String s:list)
		{
		String query = "SELECT * FROM "+s;
		template.query(query,new ResultSetExtractor<Integer>() {

	        public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
	            ResultSetMetaData rsmd = rs.getMetaData();
	            int columnCount = rsmd.getColumnCount();
	            String columnNameAdd="",isAutoAdd="",columnTypeNameAdd="",columnTypeAdd="";
	            for(int i = 1 ; i <= columnCount ; i++){
	                
	                System.out.println(rsmd.getColumnName(i)+" "+rsmd.isAutoIncrement(i)+" "+rsmd.getColumnTypeName(i)+" "+rsmd.getColumnType(i));
	                System.out.println();
	                columnNameAdd+=rsmd.getColumnName(i)+", ";
	                isAutoAdd+=rsmd.isAutoIncrement(i)+", ";
	                columnTypeNameAdd+=rsmd.getColumnTypeName(i)+", ";
	                columnTypeAdd+=rsmd.getColumnType(i)+", ";
	            }
	            addDataToDB(s, columnNameAdd,isAutoAdd,columnTypeNameAdd,columnTypeAdd);
	            return columnCount;
	        }

	    });
		}
		
	}	
	
	private void addDataToDB(String tableName, String columnNameAdd, String isAutoAdd, String columnTypeNameAdd,String columnTypeAdd) {
		try {
			String insertQueryStatement = "INSERT INTO metatable (tableName, columnName, isAuto, columnTypeName, columnType) VALUES (?,?,?,?,?)";
 
			crunchifyPrepareStat = crunchifyConn.prepareStatement(insertQueryStatement);
			crunchifyPrepareStat.setString(1, tableName);
			crunchifyPrepareStat.setString(2, columnNameAdd);
			crunchifyPrepareStat.setString(3, isAutoAdd);
			crunchifyPrepareStat.setString(4, columnTypeNameAdd);
			crunchifyPrepareStat.setString(5, columnTypeAdd);
 
			// execute insert SQL statement
			crunchifyPrepareStat.executeUpdate();
			log(columnNameAdd + " added successfully");
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
		
	}


	private static void makeTargetJDBCConnection() {
		 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			crunchifyConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shubham", "root", "Rashmiraj@0326");
			if (crunchifyConn != null) {
				log("Connection Successful! Enjoy. Now it's time to push data");
			} else {
				log("Failed to make connection!");
			}
		} catch (SQLException e) {
			log("MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
 
	}

	
	public  void dropMeta()
	{
		Statement stmt = null;
		try {
			stmt = crunchifyConn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "DROP TABLE metatable ";
	    try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void createMeta()
	{
		Statement stmt = null;
		try {
			stmt = crunchifyConn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "create table metatable("
				+ "id int not null auto_increment primary key,"
				+ "tableName varchar(1000),"
				+ "columnName varchar(1000),"
				+ "isAuto varchar(1000),"
				+ "columnTypeName varchar(1000),"
				+ "columnType varchar(1000)"
				+ ")";
	    try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void addColumn(String tableName, String columnName) {
		makeTargetJDBCConnection();
		Statement stmt = null;
		try {
			stmt = crunchifyConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = "ALTER TABLE "+tableName+ " ADD COLUMN " +columnName+" "+" varchar(45) ";
		System.out.println(sql);
	
	    try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    dropMeta();
	    createMeta();
	    sourceMeta();
	}


	public void updateColumn(String tableName, String columnName, String newcolumnName) {
		makeTargetJDBCConnection();
		Statement stmt = null;
		try {
			stmt = crunchifyConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = "ALTER TABLE "+tableName+ " CHANGE " +columnName+" "+newcolumnName+" "+" varchar(45) ";
		//System.out.println(sql);
	
	    try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    dropMeta();
	    createMeta();
	    sourceMeta();
	}


	public void deleteColumn(String tableName, String columnName) {
		makeTargetJDBCConnection();
		Statement stmt = null;
		try {
			stmt = crunchifyConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = "ALTER TABLE "+tableName+ " DROP COLUMN " +columnName;
	
	    try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    dropMeta();
	    createMeta();
	    sourceMeta();
		
	}


	public void getMeta() {
		makeTargetJDBCConnection();
		try {
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM shubham.metatable";
 
			crunchifyPrepareStat = crunchifyConn.prepareStatement(getQueryStatement);
 
			// Execute the Query, and get a java ResultSet
			ResultSet rs = crunchifyPrepareStat.executeQuery();
 
			// Let's iterate through the java ResultSet
			while (rs.next()) {
				String tableName = rs.getString("tableName");
				String columnName = rs.getString("columnName");
				String isAuto = rs.getString("isAuto");
				String columnTypeName = rs.getString("columnTypeName");
				String columnType = rs.getString("columnType");
				
				System.out.println("tableName "+tableName);
				System.out.println("columnName "+columnName);
				System.out.println("isAuto "+isAuto);
				System.out.println("columnTypeName "+columnTypeName);
				System.out.println("columnType "+columnType);
				System.out.println();
				
			}
 
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
	
}
