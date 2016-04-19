/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.*;


/**
 *
 * @author leffl_000
 */
public class Database {
    
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";
   
   static final String USER = "db16_loffldav";
   static final String PASS = "db16_loffldav";
   
   private Connection conn = null;
   
   public void Database(){
       try{
           Class.forName("org.postgresql.Driver");
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           System.out.println("CONNECTION SUCCESFUL");           
       }
       catch(Exception e){
           System.out.println("CONNECTION FAILED");
           System.out.println(e.toString());
       }finally {           
           try {
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException se) {
               se.printStackTrace();
           }//end finally try
       }
       
   }
   
   public Connection getConnection(){
       return this.conn;
   }
   
   public User login(String psw){
       User user = null;
       Statement stmt = null;
       try {
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement();
           String sql = "SELECT 'id_user','nickname','isadmin'"
                   + " FROM User"
                   + " WHERE 'nickname' LIKE '" + "admin" + "'";
           
           ResultSet rs = stmt.executeQuery(sql);
           
           System.out.println(psw);
           if(rs.next()){
               user = new User(rs.getInt("id_user"), rs.getString("nickname"), rs.getBoolean("isadmin"));
               System.out.println("ID: " + Integer.toString(user.getId()) + 
                "\nNick: " + user.getNickname() + "\nisAdmin: " + 
                Boolean.toString(user.isIsAdmin()));
           }   
           
           
           
           stmt.close();
           conn.close();
           rs.close();

       } catch (SQLException se) {
           System.out.println("FAIL #1");
           se.printStackTrace();
       } catch (Exception e) {
           System.out.println("FAIL #2");
           e.printStackTrace();
       } finally {
           //finally block used to close resources
           //finally block used to close resources
           try {
               if (stmt != null) {
                   stmt.close();
               }
           } catch (SQLException se2) {
           }// nothing we can do
           try {
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException se) {
               se.printStackTrace();
           }//end finally try
       }//end try
       return user;
   }
   
    
}
