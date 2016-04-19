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
    
}
