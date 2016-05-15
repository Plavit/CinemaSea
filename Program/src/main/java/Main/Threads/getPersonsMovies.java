/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Threads;

import Main.Movie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Thread which determine who plays where
 *
 * @author Löffler David, Szeles Marek
 */
public class getPersonsMovies extends Thread{
    
    /**
    * Setting up the parameters vital for database connection.
    */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";
    static final String USER = "db16_loffldav";
    static final String PASS = "db16_loffldav";

    private boolean isRunning = true;
    private Movie[] movies = null;
    private final char Who;
    private final int id;    
    
    /**
    * Constructor receives id of person and whether person is actor, scenarist or director
    * @param who character which categorize the person
    * @param id unique number of certain person
    */
    public getPersonsMovies(char who,int id){
        this.Who = who;
        this.id = id;
    }
    
    /**
    * Thread will find all movies that has a relation to the certain person
    */
    @Override
    public void run(){
          
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            String sql = null;            
            
            switch(Who){
                case 'A':
                    sql = "SELECT movie.id_movie, movie.namecz, movie.nameen FROM movieactors, movie WHERE " + id + " = movieactors.id_actor AND\n" +
"movieactors.id_movie = movie.id_movie;";
                    break;
                case 'S':
                    sql = "SELECT movie.id_movie, movie.namecz, movie.nameen FROM moviescenarist, movie WHERE " + id + " = moviescenarist.id_scenarist AND\n" +
"moviescenarist.id_movie = movie.id_movie;";
                    break;
                case 'D':
                    sql = "SELECT movie.id_movie, movie.namecz, movie.nameen FROM moviedirectors, movie WHERE " + id + " = moviedirectors.id_director AND\n" +
"moviedirectors.id_movie = movie.id_movie;";
                    break;
            }            
            
            ResultSet rs = stmt.executeQuery(sql);
            Movie result;
            ArrayList<Movie> list = new ArrayList<>();
            
            
            
            while(rs.next() && isRunning){
                result = new Movie(rs.getInt(1));
                result.setNameCZ(rs.getString("namecz"));
                result.setNameEN(rs.getString("nameen"));
                list.add(result);
            }
            //System.out.println("RUNNING");
            
            // CLOSING THE CONNECTION
            stmt.close();
            conn.close();           
            rs.close();
            movies = new Movie[list.size()];            
            movies = list.toArray(movies);
            isRunning = false;
        } catch (SQLException se) {
            System.out.println("FAIL #1");
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("FAIL #2");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            //finally block used to close resources
            killThread();
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
    
    /**
    * If thread gets into deadlock this function will terminate it
    */
    public void killThread(){
        this.isRunning = false;
    }
    
    /**
    * @return the status of thread whether is running or not 
    */
    public boolean isRunning(){
        return isRunning;
    }
    
    /**
    * @return array of movies related to the certain person
    */
    public Movie[] returnMoviesArray(){
        return movies;
    }
}

