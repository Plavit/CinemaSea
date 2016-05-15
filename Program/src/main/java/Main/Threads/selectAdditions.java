/*
 * Copyright (C) 2016 leffly
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
import java.util.ArrayList;

/**
 * Thread for gathering additional data of movies
 *
 * @author Löffler David, Szeles Marek
 */
public class selectAdditions extends Thread{
    
    /**
    * Setting up the parameters vital for database connection.
    */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";
    static final String USER = "db16_loffldav";
    static final String PASS = "db16_loffldav";
    
    private final int Id;
    private final char What;
    private double rating;
    private String[] additions;

    /**
    * Constructor receives id of movie and whether should be gathering genres, tags or rating
    * @param What character which determine what should be downloaded from database
    * @param Id unique number of certain movie
    */
    public selectAdditions(int Id, char What) {
        this.Id = Id;
        this.What = What;
    }
    
    /**
    * Thread will find all additional data that has a relation to the certain movie
    */
    @Override
    public void run(){
        
        ArrayList<String> listSTR = new ArrayList<>();   
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            String sql = null;
            ResultSet rs;
            
            // PREPARING SQL STATEMENT
            switch(What){
                case 'G':
                    sql = "SELECT * FROM moviegenres "                    
                            + "WHERE " + String.valueOf(Id) + " = id_movie";                    
                    rs = stmt.executeQuery(sql);
                    
                    while(rs.next()){
                        listSTR.add(rs.getString("type"));
                    }
                    
                    additions = new String[listSTR.size()];
                    additions = listSTR.toArray(additions);
                    rs.close();
                    break;
                case 'T':
                    sql = "SELECT * FROM movietags "
                            + "WHERE " + String.valueOf(Id) + " = id_movie";
                    rs = stmt.executeQuery(sql);
                    
                    while(rs.next()){
                        listSTR.add(rs.getString("type"));
                    }
                    
                    additions = new String[listSTR.size()];
                    additions = listSTR.toArray(additions);                    
                    rs.close();
                    break;
                case 'R':
                    sql = "SELECT * FROM movierating "
                            + "WHERE " + String.valueOf(Id) + " = id_movie";
                    rs = stmt.executeQuery(sql);
                    
                    while(rs.next()){
                        rating = (rs.getDouble(2));
                    }
                    
                    rs.close();
                    break;
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

    /**
    * @return rating of certain movie
    */
    public double getRating() {
        return rating;
    }

    /**
    * @return array of additional information about certain movie
    */
    public String[] getAdditions() {
        return additions;
    }
}
