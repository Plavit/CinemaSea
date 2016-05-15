/*
 * Copyright (C) 2016 David Löffler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Main.Threads;

import Main.Movie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Callable thread that gather all movies from database
 *
 * @author Löffler David, Szeles Marek
 */
public class selectAllMovies implements Callable<Movie[]>{
    
    /**
    * Setting up the parameters vital for database connection.
    */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";
    static final String USER = "db16_loffldav";
    static final String PASS = "db16_loffldav";

    /** 
    * @return array of all movies or nothing
    */
    @Override
    public Movie[] call(){
        ArrayList<Movie> list = new ArrayList<>();
        Movie result;        
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            
            String sql = "SELECT * FROM movie;";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                result = new Movie(rs.getInt("id_movie"));
                result.setCoverImage(rs.getString("Cover image"));
                result.setNameCZ(rs.getString("namecz"));
                result.setNameEN(rs.getString("nameen"));
                result.setYear(rs.getInt("year"));
                result.setDescription(rs.getString("description"));
                list.add(result);
            }
            
            System.out.println("ALL MOVIES SELECTED & ADDED");
            
            // CLOSING THE CONNECTION
            stmt.close();
            conn.close();           
            rs.close();
            Movie[] movies = new Movie[list.size()];            
            return list.toArray(movies);
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
        
    
        return null;
    }
    
}
