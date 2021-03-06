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

import Main.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Thread that gather all persons from database that has relation to some movie
 *
 * @author Löffler David, Szeles Marek
 */
public class selectPersons extends Thread{
    
    /**
    * Setting up the parameters vital for database connection.
    */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";
    static final String USER = "db16_loffldav";
    static final String PASS = "db16_loffldav";
    
    private final int Id;
    private boolean isRunning = true;
    private Person[] people = null;
    private final char Who;
    
    /**
     * Constructor of class
     * @param who receive character that determine relation between movie and person
     * @param id unique number of certain movie
     */
    public selectPersons(int id,char who){
        this.Id = id;
        this.Who = who;
    }
    
    /**
     * Overwritten func for gathering all data we want actors or scenarists or directors of certain movie
     */
    @Override
    public void run(){
        
        ArrayList<Person> list = new ArrayList<>();
        Person result;     
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            String sql = null;
            
            // PREPARING OF SQL STATEMENT
            switch(Who){
                case 'A':
                    sql = "SELECT * FROM movieactors "
                            + "WHERE " + String.valueOf(Id) + " = id_movie";
                    break;
                case 'S':
                    sql = "SELECT * FROM moviescenarist "
                            + "WHERE " + String.valueOf(Id) + " = id_movie";
                    break;
                case 'D':
                    sql = "SELECT * FROM moviedirectors "
                            + "WHERE " + String.valueOf(Id) + " = id_movie";
                    break;
            }            
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next() && isRunning){                
                result = new Person(rs.getInt(2));                
                result.setName(rs.getString("name"));
                result.setLastName(rs.getString("surname"));
                result.setFullName(result.getName() + " " + result.getLastName());
                result.setYear(rs.getInt("year"));
                result.setDescription(rs.getString("description"));
                list.add(result);
            }
            //System.out.println("RUNNING");
            
            // CLOSING THE CONNECTION
            stmt.close();
            conn.close();           
            rs.close();
            people = new Person[list.size()];            
            people = list.toArray(people);            
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
    * @return array of persons (actors or scenarists or directors) that has relation to the certain movie
    */
    public Person[] returnPersonArray(){
        return people;
    }
}
