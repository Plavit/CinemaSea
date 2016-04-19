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
   
   public ResultSet execStatement(Statement stmt){
       ResultSet rs = null;
       try {
           stmt = conn.createStatement();
           String sql;
           sql = "SELECT id, first, last, age FROM Employees";
           rs = stmt.executeQuery(sql);

           //STEP 5: Extract data from result set
           /*
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("id");
         int age = rs.getInt("age");
         String first = rs.getString("first");
         String last = rs.getString("last");

         //Display values
         System.out.print("ID: " + id);
         System.out.print(", Age: " + age);
         System.out.print(", First: " + first);
         System.out.println(", Last: " + last);
      }
            */
           //STEP 6: Clean-up environment
           stmt.close();
           conn.close();
           return rs;
       } catch (SQLException se) {
           //Handle errors for JDBC
           se.printStackTrace();
       } catch (Exception e) {
           //Handle errors for Class.forName
           e.printStackTrace();
       } finally {
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
       return rs;
   }
   
    
}
