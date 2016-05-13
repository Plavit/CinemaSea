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
import java.util.ArrayList;
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
           conn.close();
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
           }//end finally try
       }
       
   }
   
   public Connection getConnection(){
       return this.conn;
   }
   
   public User register(String psw, String nick){
       User user = null;
       Statement stmt = null;
       //TODO: change to MAX id found + 1
       java.util.Date date= new java.util.Date();
       String idHash="4"+date.getTime();
       idHash=idHash.substring(5, 12);
       //System.out.println("TIME: "+date.getTime());
        try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                        ResultSet.CONCUR_READ_ONLY);
           String sql = "INSERT INTO users (id_user, password, nickname, isadmin) VALUES ('" + idHash +"', '"+ psw +"', '" + nick+"', '" + false + "');";
           
           // CLOSING THE CONNECTION
           try ( // COLLECTING OF DATA
                   ResultSet rs = stmt.executeQuery(sql)) {
               // CLOSING THE CONNECTION
               stmt.close();
               conn.close();
           }

       } catch (SQLException se) {
           System.out.println("FAIL #1 (SQL)");
       } catch (ClassNotFoundException e) {
           System.out.println("FAIL #2");
       } finally {
           //finally block used to close resources
           try {
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException se) {
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
   
    public boolean isUserRegistered(String nick){
       boolean registered = false;
       Statement stmt = null;
       try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
           String sql = "SELECT * FROM users WHERE nickname LIKE '" + nick + "'";
           
           // COLLECTING OF DATA
           ResultSet rs = stmt.executeQuery(sql);

           if(rs.next()){
               registered = true;
           }

           // CLOSING THE CONNECTION
           stmt.close();
           conn.close();
           rs.close();

       } catch (SQLException se) {
           System.out.println("FAIL #1");
       } catch (ClassNotFoundException e) {
           System.out.println("FAIL #2");
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
       return registered;
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
   
   public void rateMovie(double rate, int id_movie, User user){
       Double rating = rate;
       boolean alreadyRated = false;
       for(Movie mv : user.getRated()){
           if(mv.getId() == id_movie) alreadyRated = true;
       }
       
       ResultSet rs = null;
       Statement stmt = null;
       try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
                
           String sql = "";
           
           if(alreadyRated){
               sql = "UPDATE rating SET stars = "+ rating.intValue() + "\n" +
        "WHERE (SELECT rr.id_rate FROM rating_related AS rr\n" +
        "WHERE "+ id_movie +" = rr.id_movie) = id_rate AND id_user = " + user.getId();
               stmt.executeUpdate(sql);
           }else{
               sql = "INSERT INTO rating VALUES((SELECT MAX(id_rate) FROM rating)+1," + user.getId() + ","+ rating.intValue() +");\n" +
                    "INSERT INTO rating_related VALUES((SELECT MAX(id_rate) FROM rating),"+ id_movie +");";
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

    public void updatePerson(String name, String surname, String year, String desc, int id, char Who){
       Statement stmt = null;
       try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
           
           String sql = "";
           
           switch (Who) {
               case 'A':
                   if (year != null) {
                       sql = "UPDATE actor SET name = '" + name
                               + "', surname = '" + surname
                               + "', year = " + year + ", description = '" + desc
                               + "'\nWHERE id_actor = " + id;
                   } else {
                       sql = "UPDATE actor SET name = '" + name
                               + "', surname = '" + surname
                               + "', description = '" + desc
                               + "'\nWHERE id_actor = " + id;
                   }
                   break;
               case 'D':
                   if (year != null) {
                       sql = "UPDATE director SET name = '" + name
                               + "', surname = '" + surname
                               + "', year = " + year + ", description = '" + desc
                               + "'\nWHERE id_director = " + id;
                   } else {
                       sql = "UPDATE director SET name = '" + name
                               + "', surname = '" + surname
                               + "', description = '" + desc
                               + "'\nWHERE id_director = " + id;
                   }
                   break;
               case 'S':
                   if (year != null) {
                       sql = "UPDATE scenarist SET name = '" + name
                               + "', surname = '" + surname
                               + "', year = " + year + ", description = '" + desc
                               + "'\nWHERE id_scenarist = " + id;
                   } else {
                       sql = "UPDATE scenarist SET name = '" + name
                               + "', surname = '" + surname
                               + "', description = '" + desc
                               + "'\nWHERE id_scenarist = " + id;
                   }
                   break;
           }
           
           stmt.executeUpdate(sql);
           
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
    
    public void insertPerson(String name, String surname, String year, String desc, int id, char Who){
        Statement stmt = null;
       try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
           
           String sql = "";
           
           switch (Who) {
               case 'A':
                   if(year != null){
                   sql = "INSERT INTO actor (id_actor, name, surname, year, description)" 
                           +"VALUES(" + "(SELECT MAX(id_actor)+1 FROM actor)" + ", '" + name
                           + "', '" + surname + "', " + year + ",'" + desc + "');";
                   }else{
                       sql = "INSERT INTO actor (id_actor, name, surname, description)" 
                           +"VALUES(" + "(SELECT MAX(id_actor)+1 FROM actor)" + ", '" + name
                           + "', '" + surname + "', '" + desc + "');";
                   }                    
                   break;
               case 'D':
                   if(year != null){
                   sql = "INSERT INTO director (id_director, name, surname, year, description)" 
                           +"VALUES(" + "(SELECT MAX(id_director)+1 FROM director)" + ", '" + name
                           + "', '" + surname + "', " + year + ",'" + desc + "');";
                   }else{
                       sql = "INSERT INTO director (id_director, name, surname, description)" 
                           +"VALUES(" + "(SELECT MAX(id_director)+1 FROM director)" + ", '" + name
                           + "', '" + surname + "', '" + desc + "');";
                   }                   
                   break;
               case 'S':
                   if(year != null){
                   sql = "INSERT INTO scenarist (id_scenarist, name, surname, year, description)" 
                           +"VALUES(" + "(SELECT MAX(id_scenarist)+1 FROM scenarist)" + ", '" + name
                           + "', '" + surname + "', " + year + ",'" + desc + "');";
                   }else{
                       sql = "INSERT INTO scenarist (id_scenarist, name, surname, description)" 
                           +"VALUES(" + "(SELECT MAX(id_scenarist)+1 FROM scenarist)" + ", '" + name
                           + "', '" + surname + "', '" + desc + "');";
                   }
                   break;
           }
           
           stmt.executeUpdate(sql);
           
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
    
    public void inserMovie(int id, String nameCZ, String nameEN,String year, String desc, int[] actors, int[] directors, int[] scenarists){
         Statement stmt = null;
       try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
           String sql = "SELECT MAX(id_movie)+1 FROM movie";
           ResultSet rs = stmt.executeQuery(sql);
           while(rs.next()){
               id = rs.getInt(1);               
           }
           
           sql = "INSERT INTO movie"
                   + "\nVALUES (" + id + ",'null', '" + nameCZ + "', '" + nameEN + "', " + year
                   + ", '" + desc + "')";
           stmt.executeUpdate(sql);
           
           for(int idMan : actors){
               sql = "INSERT INTO plays VALUES(" + idMan + ", " + id + ");";
               stmt.executeUpdate(sql);
           }
           
           for(int idMan : directors){
               sql = "INSERT INTO shoots VALUES(" + idMan + ", " + id + ");";
               stmt.executeUpdate(sql);
           }
           
           for(int idMan : scenarists){
               sql = "INSERT INTO screenplay VALUES(" + idMan + ", " + id + ");";
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
    
    public void updateMovie(Movie movie,ArrayList<Integer> addActors, ArrayList<Integer> addDirectors,
            ArrayList<Integer> addScenarists,ArrayList<Integer> rmActors, ArrayList<Integer> rmDirectors,
            ArrayList<Integer> rmScenarists){
       Statement stmt = null;
       String sql = "";
       try {
           // PREPARING THE SQL REQUEST
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                           ResultSet.CONCUR_READ_ONLY);
           
           sql = "UPDATE movie SET namecz = '" + movie.getNameCZ() + "', "
                   + "nameen = '" + movie.getNameEN() + "', "
                   + "year = " + movie.getYear() + ", "
                   + "description = '" + movie.getDescription() + "'"
                   + "\nWHERE id_movie = " + movie.getId();
           stmt.executeUpdate(sql);
           
           if(rmActors.size() > 0){
               for(int rmID : rmActors){
                   sql = "DELETE FROM plays WHERE id_movie = " + movie.getId() + " AND id_actor = " + rmID;
                   stmt.executeUpdate(sql);
                   System.out.println("DELETING ACTORS");
               }
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
    
    
}
