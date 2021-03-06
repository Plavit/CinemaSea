/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * Thread that gather all persons from database
 *
 * @author Löffler David, Szeles Marek
 */
public class selectAllPersons extends Thread{
    
    /**
    * Setting up the parameters vital for database connection.
    */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";
    static final String USER = "db16_loffldav";
    static final String PASS = "db16_loffldav";

    private boolean isRunning = true;
    private Person[] people = null;
    private final char Who;
    /**
     * Constructor of class
     * @param who receive character that determine who will be downloaded (actors,scenarists,directors)
     */
    public selectAllPersons(char who){
        this.Who = who;
    }
    
    /**
     * Overwritten func for gathering all data we want (actors,scenarists,directors)
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
                    sql = "SELECT * FROM actor";
                    break;
                case 'S':
                    sql = "SELECT * FROM scenarist";
                    break;
                case 'D':
                    sql = "SELECT * FROM director";
                    break;
            }            
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next() && isRunning){                
                result = new Person(rs.getInt(1));                
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
    * @return array of persons (actors or scenarists or directors)
    */
    public Person[] returnPersonArray(){
        return people;
    }
}

