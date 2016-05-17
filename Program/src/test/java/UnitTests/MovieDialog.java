/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitTests;

import Main.Dialogs.movieDialog;
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
 * @author David Löffler
 */
public class MovieDialog {
    
    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

 
    
    @Test
    public void movieDialog_findPeopleFunc_EmptyArrays_test(){
        int expectedResult = 0;         
        
        Person[] obj1 = new Person[0];
        Person[] obj2 = new Person[0];
        movieDialog dialog = new movieDialog();
        
        int realResult = dialog.findPeople(obj1, obj2).size();
        assertEquals(expectedResult,realResult);        
    }
    
    @Test
    public void movieDialog_findPeopleFunc_firstEmpty_test(){
        int expectedResult = 3;         
        
        Person[] obj1 = new Person[0];
        Person[] obj2 = {new Person(1),new Person(2),new Person(3)};
        
        movieDialog dialog = new movieDialog();
        
        int realResult = dialog.findPeople(obj1, obj2).size();
        assertEquals(expectedResult,realResult);        
    }
    
    @Test
    public void movieDialog_findPeopleFunc_secondEmpty_test(){
        int expectedResult = 0;         
        
        Person[] obj2 = new Person[0];
        Person[] obj1 = {new Person(1),new Person(2),new Person(3)};
        
        movieDialog dialog = new movieDialog();
        
        int realResult = dialog.findPeople(obj1, obj2).size();
        assertEquals(expectedResult,realResult);        
    }
    
    @Test
    public void movieDialog_findPeopleFunc_ArraysSameData_test(){
        int expectedResult = 0;         
        
        Person[] obj1 = {new Person(1),new Person(2),new Person(3)};
        Person[] obj2 = {new Person(1),new Person(2),new Person(3)};
        
        movieDialog dialog = new movieDialog();
        
        int realResult = dialog.findPeople(obj1, obj2).size();
        assertEquals(expectedResult,realResult);        
    }
    
    @Test
    public void movieDialog_findPeopleFunc_firstArrLarger_andSwap_test(){
        int expectedResult1 = 0;    
        int expectedResult2 = 1;   
        
        Person[] obj1 = {new Person(1),new Person(2),new Person(3)};
        Person[] obj2 = {new Person(1),new Person(2)};
        
        movieDialog dialog = new movieDialog();
        
        int realResult1 = dialog.findPeople(obj1, obj2).size();
        int realResult2 = dialog.findPeople(obj2, obj1).size();
        assertEquals(expectedResult1,realResult1);
        assertEquals(expectedResult2,realResult2);        
    }
    
    @Test
    public void movieDialog_findPeopleFunc_arrsDifferentData_test(){
        int expectedResult1 = 2; 
        
        Person[] obj1 = {new Person(1),new Person(2),new Person(3)};
        Person[] obj2 = {new Person(6),new Person(4)};
        
        movieDialog dialog = new movieDialog();
        
        int realResult1 = dialog.findPeople(obj1, obj2).size();
        assertEquals(expectedResult1,realResult1);       
    }
    
}
