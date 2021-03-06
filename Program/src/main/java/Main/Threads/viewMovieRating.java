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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Thread that creates VIEW in database called movierating
 *
 * @author Löffler David, Szeles Marek
 */
public class viewMovieRating extends Thread{
    
    /**
    * Setting up the parameters vital for database connection.
    */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";
    static final String USER = "db16_loffldav";
    static final String PASS = "db16_loffldav";
    
    /**
     * Function that creates the certain VIEW
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
            String sql = "CREATE VIEW movierating AS SELECT movie.id_movie, AVG(rating.stars)\n"
                    + "FROM movie, rating, rating_related\n"
                    + "WHERE movie.id_movie = rating_related.id_movie AND rating.id_rate = rating_related.id_rate\n"
                    + "GROUP BY movie.id_movie;";
            stmt.executeUpdate(sql);
            
            System.out.println("VIEW(rating) CREATED");
            
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
