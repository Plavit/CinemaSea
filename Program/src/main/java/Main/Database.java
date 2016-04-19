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
   static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz";
   
   static final String USER = "db16_loffldav";
   static final String PASS = "db16_loffldav";

    public Database() {
    }
   
   
   
   public void ConnectToServer(){
       Connection conn = null;
       Statement stmt = null;
       
       try{
           //Class.forName("com.mysql.jdbc.Driver");
           
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           System.out.println("CONNECTED !!!!!!!!!!!!!!");
           
           //stmt.close();
           conn.close();
       }
       catch(Exception e){
           System.out.println("CONNECTION FAILED");
           System.out.println(e.toString());
       }finally {
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
       }
       
   }
    
}
