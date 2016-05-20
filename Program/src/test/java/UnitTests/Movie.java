/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitTests;

import Main.Person;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.fail;

/**
 *
 * @author David Löffler, Szeles Marek
 */
public class Movie {
    
    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

 
    @Test
    public void movie_CloneFunc_copyObjectOK_test(){
        boolean expectedResult = true; 
        
        Main.Movie initObj = new Main.Movie(1);
        Main.Movie clon = initObj.clone();
        
        boolean realResult = clon.equals(initObj);
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_askForNonSenseEmpty_test(){
        boolean expectedResult = false;         
        Main.Movie obj = new Main.Movie(1);   
        
        boolean realResult = obj.isEmpty('Y');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_actorsEmpty_test(){
        boolean expectedResult = true;         
        Main.Movie obj = new Main.Movie(1);        
        
        boolean realResult = obj.isEmpty('A');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_actorsFull_test(){
        boolean expectedResult = false;         
        Main.Movie obj = new Main.Movie(1);
        Person[] ppl = {new Person(1),new Person(2)};
        obj.setActors(ppl);
        
        boolean realResult = obj.isEmpty('A');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_scenaristsEmpty_test(){
        boolean expectedResult = true;         
        Main.Movie obj = new Main.Movie(1);
        
        boolean realResult = obj.isEmpty('S');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_scenaristsFull_test(){
        boolean expectedResult = false;         
        Main.Movie obj = new Main.Movie(1);
        Person[] ppl = {new Person(1),new Person(2)};
        obj.setScenarists(ppl);
        
        boolean realResult = obj.isEmpty('S');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_directorsEmpty_test(){
        boolean expectedResult = true;         
        Main.Movie obj = new Main.Movie(1);
        
        boolean realResult = obj.isEmpty('D');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_directorsFull_test(){
        boolean expectedResult = false;         
        Main.Movie obj = new Main.Movie(1);
        Person[] ppl = {new Person(1),new Person(2)};
        obj.setDirectors(ppl);
        
        boolean realResult = obj.isEmpty('D');
        assertEquals(expectedResult,realResult);       
    }
    
}
