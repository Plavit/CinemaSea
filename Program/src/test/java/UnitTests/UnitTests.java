package UnitTests;

import Main.Dialogs.movieDialog;
import Main.Handlers.CheckPassword;
import Main.Handlers.CheckUsername;
import Main.Movie;
import Main.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertEquals;

//Unit tests

/**
 * This class consists entirely of unit tests for testing 
 * the methods in the project.
 * 
 * @author David Löffler
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitTests {
    
    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    /**
     * Tests the function copying an object.
     * Creates a dummy object @code testingObject , then makes a copy
     * of it and then compares the two results.
     * 
     */
    @Test
    public void copy_personObject_Test() {
        /**
         * {@link boolean} the expected outcome of the test - true.
         */
         
        boolean expectedResult = true;
              
        /**
         * {@link Person} the dummy object being tested.
         */
        Person testingObject = new Person(1);
        testingObject.setDescription("desc");
        testingObject.setFullName("full name");
        testingObject.setLastName("johnson");
        testingObject.setName("nobody");
        testingObject.setYear(2166);
        
        /**
         * {@link boolean} the real outcome of the copy method test.
         */
        Person toCompare = testingObject.copy();
        
        boolean result = toCompare.equals(testingObject);
        
        assertEquals(expectedResult,result);        
    }
    
     /**
     * Tests the password checking function.
     * Checks if a request to pass a blank password returns the desired 
     * error message.
     */
    @Test
    public void checkPassword_emptyBoth_test(){
        String expectedResult = "You need to choose your password!";         
        String realResult = CheckPassword.checkPasswords("", "");        
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the password checking function.
     * Checks if a request to pass a blank password returns the desired 
     * error message.
     */
    @Test
    public void checkPassword_emptyFirst_test(){
        String expectedResult = "You need to choose your password!";         
        String realResult = CheckPassword.checkPasswords("", "askjdfhaslkfja");        
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the password checking function.
     * Checks if a request to pass a blank confirm password field returns 
     * the desired error message.
     */
    @Test
    public void checkPassword_emptySecond_test(){
        String expectedResult = "You need to re-enter your password to proceed.";         
        String realResult = CheckPassword.checkPasswords("asdfasdasdff", "");        
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the password checking function.
     * Checks if a request to pass password and confirm password fields 
     * with different values returns the desired error message.
     */
    @Test
    public void checkPassword_bothDifferent_test(){
        String expectedResult = "You need to re-enter your password to proceed.";         
        String realResult = CheckPassword.checkPasswords("asdfasdfasdf", "fsadfsdfadsf");        
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the password checking function.
     * Checks if a request to pass password shorter than 8 characters
     * returns the desired error message.
     */
    @Test
    public void checkPassword_shorterThan8_test(){
        String expectedResult = "Password too short! Minimum: 8, Given: 2";         
        String realResult = CheckPassword.checkPasswords("as", "askjdfhaslkfja");        
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the password checking function.
     * Checks if a request to pass a valid password request succeeds.
     */
    @Test
    public void checkPassword_bothTheSame_test(){
        String expectedResult = "OK";         
        String realResult = CheckPassword.checkPasswords("lolololo", "lolololo");        
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the user checking function.
     * Checks if a request to pass a username that is leaved empty returns
     * the correct error message.
     */
    @Test
    public void checkUsername_empty_test(){
        String expectedResult = "You need to choose your username!";         
        String realResult = CheckUsername.checkUsername("");
        assertEquals(expectedResult,realResult);        
    }
    
     /**
     * Tests the user checking function.
     * Checks if a request to pass a valid new username request succeeds.
     */
    @Test
    public void checkUsername_nonExistUser_test(){
        String expectedResult = "OK";         
        String realResult = CheckUsername.checkUsername("asdkjfjaslakdf");
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
        String realResult = CheckUsername.checkUsername("admin");
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
        String realResult = CheckUsername.checkUsername("f@#sdg@#&^)(*$@#(*@&;;;$#");
        assertEquals(expectedResult,realResult);        
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
    
    @Test
    public void movie_CloneFunc_copyObjectOK_test(){
        boolean expectedResult = true; 
        
        Movie initObj = new Movie(1);
        Movie clon = initObj.clone();
        
        boolean realResult = clon.equals(initObj);
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_askForNonSenseEmpty_test(){
        boolean expectedResult = false;         
        Movie obj = new Movie(1);   
        
        boolean realResult = obj.isEmpty('Y');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_actorsEmpty_test(){
        boolean expectedResult = true;         
        Movie obj = new Movie(1);        
        
        boolean realResult = obj.isEmpty('A');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_actorsFull_test(){
        boolean expectedResult = false;         
        Movie obj = new Movie(1);
        Person[] ppl = {new Person(1),new Person(2)};
        obj.setActors(ppl);
        
        boolean realResult = obj.isEmpty('A');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_scenaristsEmpty_test(){
        boolean expectedResult = true;         
        Movie obj = new Movie(1);
        
        boolean realResult = obj.isEmpty('S');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_scenaristsFull_test(){
        boolean expectedResult = false;         
        Movie obj = new Movie(1);
        Person[] ppl = {new Person(1),new Person(2)};
        obj.setScenarists(ppl);
        
        boolean realResult = obj.isEmpty('S');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_directorsEmpty_test(){
        boolean expectedResult = true;         
        Movie obj = new Movie(1);
        
        boolean realResult = obj.isEmpty('D');
        assertEquals(expectedResult,realResult);       
    }
    
    @Test
    public void movie_isEmptyFunc_directorsFull_test(){
        boolean expectedResult = false;         
        Movie obj = new Movie(1);
        Person[] ppl = {new Person(1),new Person(2)};
        obj.setDirectors(ppl);
        
        boolean realResult = obj.isEmpty('D');
        assertEquals(expectedResult,realResult);       
    }
    
}
