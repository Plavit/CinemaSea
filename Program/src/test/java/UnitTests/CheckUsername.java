/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitTests;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.fail;

/**
 *
 * @author David Löffler
 */
public class CheckUsername {
    
    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

 
    
     /**
     * Tests the user checking function.
     * Checks if a request to pass a username that is leaved empty returns
     * the correct error message.
     */
    @Test
    public void checkUsername_empty_test(){
        String expectedResult = "You need to choose your username!";         
        String realResult = Main.Handlers.CheckUsername.checkUsername("");
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the user checking function.
     * Checks if a request to pass a valid new username request succeeds.
     */
    @Test
    public void checkUsername_nonExistUser_test(){
        String expectedResult = "OK";         
        String realResult = Main.Handlers.CheckUsername.checkUsername("asdkjfjaslakdf");
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the user checking function.
     * Checks if a request to pass a username that is already taken returns
     * the correct error message.
     */
    @Test
    public void checkUsername_nameAlreadyInDatabse_test(){
        String expectedResult = "Username already taken!";         
        String realResult = Main.Handlers.CheckUsername.checkUsername("admin");
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the user checking function.
     * Checks if a request to pass a username that is made of <i>unusual</i> characters
     * passes the test.
     */
    @Test
    public void checkUsername_nonSenseText_test(){
        String expectedResult = "OK";         
        String realResult = Main.Handlers.CheckUsername.checkUsername("f@#sdg@#&^)(*$@#(*@&;;;$#");
        assertEquals(expectedResult,realResult);        
    }
    
}
