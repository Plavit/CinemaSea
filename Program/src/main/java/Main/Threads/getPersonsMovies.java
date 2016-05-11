/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Threads;

import Main.Movie;
import Main.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author David Löffler
 */
public class getPersonsMovies extends Thread{
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";
    static final String USER = "db16_loffldav";
    static final String PASS = "db16_loffldav";

    private boolean isRunning = true;
    private Movie[] movies = null;
    private final char Who;
    private final int id;
    
    public getPersonsMovies(char who,int id){
        this.Who = who;
        this.id = id;
    }
    
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
                    sql = "SELECT movie FROM movieactors, movie WHERE " + id + " = movieactors.id_actor AND\n" +
"movieactors.id_movie = movie.id_movie;";
                    break;
                case 'S':
                    sql = "SELECT movie FROM moviescenarist, movie WHERE " + id + " = moviescenarist.id_actor AND\n" +
"moviescenarist.id_movie = movie.id_movie;";
                    break;
                case 'D':
                    sql = "SELECT movie FROM moviedirectors, movie WHERE " + id + " = moviedirectors.id_actor AND\n" +
"moviedirectors.id_movie = movie.id_movie;";
                    break;
            }            
            
            ResultSet rs = stmt.executeQuery(sql);
            Movie result;
            ArrayList<Movie> list = new ArrayList<>();
            
            while(rs.next() && isRunning){
                result = new Movie(rs.getInt(1));
                result.setNameCZ(USER);
                result.setNameEN(USER);
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
    
    public void killThread(){
        this.isRunning = false;
    }
    
    public boolean isRunning(){
        return isRunning;
    }
    
    public Movie[] returnMoviesArray(){
        return movies;
    }
}

