/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntegrationTests;

import Main.Database;
import Main.Movie;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.fail;

/**
 *
 * @author Löffler David, Szeles Marek
 */
public class CheckDatabase {

    boolean failed = false;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:postgresql://slon.felk.cvut.cz:5432/db16_loffldav";

    static final String USER = "db16_loffldav";
    static final String PASS = "db16_loffldav";

    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private Connection conn = null;

    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    @Test
    public void checkMovie_Create_test() {
        if (failed) {
            fail();
        }

        Database db = new Database();

        String nameCZ = "Koko 1";
        String nameEN = "Koko: One";
        String desc = "Koko is koko and his purpose is to koko.";
        String year = "2016";
        int year2 = 2016;
        int id = 0;

        try {
            failed = true;
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            
            // LAUNCHING THE FUNCTION TO TEST
            db.inserMovie(id, nameCZ, nameEN, year, desc, null, null, null);
  
            String sql = "SELECT * FROM movie WHERE id_movie = (SELECT MAX(id_movie) FROM movie)";
            
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // CHECKING THE DATABASE DATA
                assertEquals(nameCZ,rs.getString("namecz"));
                assertEquals(nameEN,rs.getString("nameen"));
                assertEquals(year2,rs.getInt("year"));
                assertEquals(desc,rs.getString("description"));
                failed = false;
            }
            stmt.close();
            conn.close();
            rs.close();
        } catch (SQLException se) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);
        } catch (Exception e) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se2);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);

            }
        }
        if(failed) fail();

    }
    
    @Test
    public void checkMovie_Update_test() {
        if (failed) {
            fail();
        }

        Database db = new Database();        
        Movie mv = new Movie(0);
        
        String nameCZ = "Koko 1";
        String nameEN = "Koko: Two";
        String desc = "Koko is koko and his purpose is to koko.";
        String year = "2015";
        int year2 = 2015;
        int id = 0;       

        try {
            failed = true;
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
  
            // LAUNCHING THE FUNCTION TO TEST
            db.inserMovie(id, nameCZ, "Koko: One", "2016", desc, null, null, null);
            
            String sql = "SELECT * FROM movie WHERE id_movie = (SELECT MAX(id_movie) FROM movie)";
            
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                mv = new Movie(rs.getInt(1));
                mv.setNameCZ(nameCZ);
                mv.setNameEN(nameEN);
                mv.setYear(year2);
                mv.setDescription(desc);
                failed = false;
            }
            
            if(failed) fail();
            
            db.updateMovie(mv, null, null, null, null, null, null);
            failed = true;
            
            sql = "SELECT * FROM movie WHERE id_movie = " + mv.getId();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                // CHECKING THE DATABASE DATA
                assertEquals(nameCZ,rs.getString("namecz"));
                assertEquals(nameEN,rs.getString("nameen"));
                assertEquals(year2,rs.getInt("year"));
                assertEquals(desc,rs.getString("description"));
                failed = false;
            }
            
            stmt.close();
            conn.close();
            rs.close();
        } catch (SQLException se) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);
        } catch (Exception e) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se2);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);

            }
        }
        if(failed) fail();
    }
    
    
    @Test
    public void checkActor_Create_test() {
        if (failed) {
            fail();
        }

        Database db = new Database();

        String name = "James";
        String surname = "Bond";
        String desc = "Bond, the expert on Martini and ladies.";
        String year = "2007";
        int year2 = 2007;
        int id = 0;

        try {
            failed = true;
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            
            // LAUNCHING THE FUNCTION TO TEST
            db.insertPerson(name, surname, year, desc, id, 'A');
            
            String sql = "SELECT * FROM actor WHERE id_actor = (SELECT MAX(id_actor) FROM actor)";
            
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // CHECKING THE DATABASE DATA
                assertEquals(name,rs.getString("name"));
                assertEquals(surname,rs.getString("surname"));
                assertEquals(year2,rs.getInt("year"));
                assertEquals(desc,rs.getString("description"));
                failed = false;
            }
            stmt.close();
            conn.close();
            rs.close();
        } catch (SQLException se) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);
        } catch (Exception e) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se2);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);

            }
        }
        if(failed) fail();

    }
    
    @Test
    public void checkActor_Update_test() {
        if (failed) {
            fail();
        }

        Database db = new Database();        
        Movie mv = new Movie(0);
        
        String name = "Jamie";
        String surname = "Lannister";
        String desc = "Bloody Lannister";
        String year = "2007";
        int year2 = 2007;
        int id = 0;      

        try {
            failed = true;
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
  
            db.insertPerson("James", "Bond", year, "Bond, the expert on Martini and ladies.", id, 'A');
            
            String sql = "SELECT * FROM actor WHERE id_actor = (SELECT MAX(id_actor) FROM actor)";
            
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt(1);
                failed = false;
            }
            
            if(failed) fail();
            
            db.updatePerson(name, surname, year, desc, id, 'A');
            failed = true;
            
            sql = "SELECT * FROM actor WHERE id_actor = " + id;
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                // CHECKING THE DATABASE DATA
                assertEquals(name,rs.getString("name"));
                assertEquals(surname,rs.getString("surname"));
                assertEquals(year2,rs.getInt("year"));
                assertEquals(desc,rs.getString("description"));
                failed = false;
            }
            
            stmt.close();
            conn.close();
            rs.close();
        } catch (SQLException se) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);
        } catch (Exception e) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se2);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);

            }
        }
        if(failed) fail();
    }
    
    
    @Test
    public void checkUser_Create_test() {
        if (failed) {
            fail();
        }

        Database db = new Database();

        String nick = "integrationTest";
        String psw = "";
        try {
            psw = db.HashPSW("nonenone");
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        try {
            failed = true;
            // PREPARING THE SQL REQUEST
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);            
            
            String sql = "DELETE FROM users WHERE nickname = 'integrationTest'";
            stmt.executeUpdate(sql);
            
            db.register(psw, nick);
            
            sql = "SELECT * FROM users WHERE nickname = 'integrationTest'";
            
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // CHECKING THE DATABASE DATA
                assertEquals(nick,rs.getString("nickname"));
                assertEquals(psw,rs.getString("password"));
                assertEquals(false,rs.getBoolean("isadmin"));
                failed = false;
            }
            stmt.close();
            conn.close();
            rs.close();
        } catch (SQLException se) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);
        } catch (Exception e) {
            failed = true;
            fail();
            Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se2);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                failed = true;
                fail();
                Logger.getLogger(CheckDatabase.class.getName()).log(Level.SEVERE, null, se);

            }
        }
        if(failed) fail();

    }
}
