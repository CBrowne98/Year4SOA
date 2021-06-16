/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceorienproject;

/**
 *
 * @author major
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson; 
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author major
 */
public enum PeopleDao {
    instance;
    
    private Connection con = null;
    
    private PeopleDao(){
        try{
            System.out.println("Loading db driver...");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("Db driver loaded.");
            
            con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/ProjectDB",
                    "cian",
                    "1234");
        }
        catch (ClassNotFoundException ex){
            System.err.println("\nClassNotFoundException");
            ex.printStackTrace();
        } catch (SQLException ex){
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
    }
    public List<Person> getPeople(){
        List<Person> accounts = new ArrayList<Person>();
        try {
			PreparedStatement psmt = con.prepareStatement("Select * from people");
			
			ResultSet data = psmt.executeQuery();
			while(data.next()) {
				int id = data.getInt("id");
				String fName = data.getString("first_name");
				String lName = data.getString("last_name");
				String car = data.getString("car");
				String city = data.getString("city");
				String country = data.getString("country");
                                Timestamp time = data.getTimestamp("timestamp");
                                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = f.format(time);
//                                Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formattedDate); 

                                
                accounts.add(new Person (id, fName, lName, car, city, country, formattedDate));
                //System.out.println("Added to array list");

			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
        return accounts;
    }
    public int addPerson(Person person){
        try{
            PreparedStatement psmt = con.prepareStatement("INSERT INTO people(first_name, last_name, car, city, country) values( ?,?,?,?,?)");  
            psmt.setString(1, person.getFirstName());
            psmt.setString(2, person.getLastName());
            psmt.setString(3, person.getCar());
            psmt.setString(4, person.getCity());
            psmt.setString(5, person.getCountry());
            
            int count = psmt.executeUpdate();
            System.out.println(count);
            if(count != 1)
                System.out.println(count);
                return -1;
            
        } catch(SQLException ex){
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
        return 1;
    }
    public int updatePerson(Person person){
       try{
            PreparedStatement psmt = con.prepareStatement("UPDATE people SET first_name = ?, last_name = ?, car = ?, city = ?, country = ?  WHERE id = ?");  
            psmt.setString(1, person.getFirstName());
            psmt.setString(2, person.getLastName());
            psmt.setString(3, person.getCar());
            psmt.setString(4, person.getCity());
            psmt.setString(5, person.getCountry());
            psmt.setInt(6, person.getId());
            
            int count = psmt.executeUpdate();
            System.out.println(count);
            if(count != 1)
                System.out.println(count);
                return -1;
            
        } catch(SQLException ex){
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
        return 1;
    }
    public Person getNewestTimestamp(){
        Person person = new Person();
        try{
            PreparedStatement psmt = con.prepareStatement("Select * from people order by timestamp desc fetch first 1 rows only");
            ResultSet data = psmt.executeQuery();
            while(data.next()) {
             int id = data.getInt("id");
				String fName = data.getString("first_name");
				String lName = data.getString("last_name");
				String car = data.getString("car");
				String city = data.getString("city");
				String country = data.getString("country");
                                Timestamp time = data.getTimestamp("timestamp");
                                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = f.format(time);
                                person = new Person (id, fName, lName, car, city, country, formattedDate);
                                 person = new Person (id, fName, lName, car, city, country, formattedDate);
            }
        }
         catch(SQLException ex){
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
         return person;
    }
    public Person getPerson(int findId){
        Person person = new Person();
        try {
			PreparedStatement psmt = con.prepareStatement("Select * from people where ID = ?");
			 psmt.setInt(1, findId);
			ResultSet data = psmt.executeQuery();
			while(data.next()) {
				int id = data.getInt("id");
				String fName = data.getString("first_name");
				String lName = data.getString("last_name");
				String car = data.getString("car");
				String city = data.getString("city");
				String country = data.getString("country");
                                Timestamp time = data.getTimestamp("timestamp");
                                Date date = new Date();
                                date.setTime(time.getTime());
                                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = f.format(time);
                 person = new Person (id, fName, lName, car, city, country, formattedDate);
                //System.out.println("Added to array list");

			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
        return person;
	
    }
}

