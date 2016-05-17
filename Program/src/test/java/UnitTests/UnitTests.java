/*
 * Copyright (C) 2016 CinemaSea
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
    
     
    
    
    
    
}
