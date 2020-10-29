/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xpg3393
 */
public class ModelTest {
    
    public ModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getQAndA method, of class Model. Comparing 2 results which should 
     * equal
     */
    @Test
    public void testCorrectGetQAndA() {
        System.out.println("getCorrectQAndA");
        int question = 1;
        Model instance = new Model();
        instance.getQAndA(question);
        String expected = "Question 1: What is 2+2?";
        assertEquals(instance.currentQandO[0], expected);
        
    }
    
    /**
     * Test of getQAndA method, of class Model. Comparing 2 results which should 
     * not equal
     */
    @Test
    public void testIncorrectGetQAndA() {
        System.out.println("getIncorrectQAndA");
        int question = 10;
        Model instance = new Model();
        instance.getQAndA(question);
        String expected = "Question 1: What is 2+2?";
        
       
        assertFalse(expected.equals(instance.currentQandO[0]));
        
    }

    /**
     * Test of setContestant method, of class Model.
     */
    @Test
    public void testSetContestant() {
        System.out.println("setContestant");
        Contestant contestant = new Contestant("Yash", "Raniga", 19);
        Model instance = new Model();
        instance.setContestant(contestant);
        assertNotNull(instance.getContestant());
    }

    /**
     * Test of checkAnswer method, of class Model. checkAnswer should return 
     * true.
     */
    @Test
    public void testCorrectCheckAnswer() {
        System.out.println("checkCorrectAnswer");
        int question = 1;
        String answer = "C";
        Model instance = new Model();
        instance.setContestant(new Contestant("Yash", "Raniga", 19));
        boolean expResult = true;
        boolean result = instance.checkAnswer(question, answer);
        assertEquals(expResult, result);

    }
    
    /**
     * Test of checkAnswer method, of class Model. checkAnswer should return 
     * false.
     */
    @Test
    public void testIncorrectCheckAnswer() {
        System.out.println("IncorrectCheckAnswer");
        int question = 10;
        String answer = "D";
        Model instance = new Model();
        instance.setContestant(new Contestant("Yash", "Raniga", 19));
        boolean expResult = false;
        boolean result = instance.checkAnswer(question, answer);
        assertEquals(expResult, result);

    }

    
}
