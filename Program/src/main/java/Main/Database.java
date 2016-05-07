/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Threads.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author Löffler David, Szeles Marek
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
           JOptionPane.showMessageDialog(new JFrame(),
                    "Couldn't connect to server! Please try it later.",
                    "SERVER ERROR",
                    JOptionPane.ERROR_MESSAGE);
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
   
   public User register(String psw, String nick){
       User user = null;
       Statement stmt = null;
        try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
           String sql = "INSERT INTO users (password, nickname, isadmin) VALUES ('" + psw +"', '" + nick+"', '" + false + "');";
           
           // COLLECTING OF DATA
           ResultSet rs = stmt.executeQuery(sql);

           // CLOSING THE CONNECTION
           stmt.close();
           conn.close();
           rs.close();

       } catch (SQLException se) {
           System.out.println("FAIL #1 (SQL)");
           se.printStackTrace();
       } catch (Exception e) {
           System.out.println("FAIL #2");
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
       //TBD
       
       return user;
   }
   
   public User login(String psw, String nick){
       User user = null;
       Statement stmt = null;
       try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
           String sql = "SELECT * FROM users WHERE password LIKE '" + psw +"' AND nickname LIKE '" + nick + "'";
           
           // COLLECTING OF DATA
           ResultSet rs = stmt.executeQuery(sql);

           if(rs.next()){
               user = new User(rs.getInt("id_user"), rs.getString("nickname"), rs.getBoolean("isadmin"));
           }

           // CLOSING THE CONNECTION
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
   
   public void clearViews(){
       Statement stmt = null;
       try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
           String[] sqls = {"DROP VIEW IF EXISTS movieactors;", 
                            "DROP VIEW IF EXISTS moviedirectors;",
                            "DROP VIEW IF EXISTS moviescenarist;",
                            "DROP VIEW IF EXISTS movierating;",
                            "DROP VIEW IF EXISTS usermovies;",
                            "DROP VIEW IF EXISTS moviegenres;",
                            "DROP VIEW IF EXISTS movietags;"};
           for(String sql : sqls){
               stmt.executeUpdate(sql);               
           }
           // CLOSING THE CONNECTION
           stmt.close();
           conn.close();           

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
   }
   
   public boolean updateViews() throws InterruptedException{
       clearViews();       
       Thread[] threads = {new viewMovieActors(), new viewMovieDirectors(),
                           new viewMovieScenarists(), new viewMovieRating(),
                           new viewUserMovies(), new viewMovieTags(),
                           new viewMovieGenres()};
       
      for(Thread th : threads){
          th.start();
      }
      
      threads[0].join();
      threads[1].join();
      threads[2].join();
      threads[3].join();
      threads[4].join();
      threads[5].join();
      threads[6].join();
       
      return true;
   }
   
   public String HashPSW(String init) throws InvalidKeySpecException, NoSuchAlgorithmException{
        byte[] salt = {15,32,54,3,45,2,5,3,1,4,87,9,6,89,99,17};
        KeySpec spec = new PBEKeySpec(init.toCharArray(), salt, 98434, 256);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        /*
        System.out.printf("salt: %s%n", enc.encodeToString(salt));
        System.out.printf("hash: %s%n", enc.encodeToString(hash));
        */
        return enc.encodeToString(hash);
    }
   
    
}
