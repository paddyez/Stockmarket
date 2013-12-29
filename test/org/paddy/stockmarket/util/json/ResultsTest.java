/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paddy
 */
public class ResultsTest {
    
    public ResultsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getQuotes method, of class Results.
     */
    @Test
    public void testGetQuotes() {
        System.out.println("getQuotes");
        Results instance = new Results();
        List expResult = null;
        List result = instance.getQuotes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getQuote method, of class Results.
     */
    @Test
    public void testGetQuote() {
        System.out.println("getQuote");
        Results instance = new Results();
        List<Quote> expResult;
        expResult = null;
        List<Quote> result;
        result = instance.getQuotes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setQuotes method, of class Results.
     */
    @Test
    public void testSetQuotes() {
        System.out.println("setQuotes");
        List<Quote> quotes = null;
        Results instance = new Results();
        instance.setQuotes(quotes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setQuote method, of class Results.
     */
    @Test
    public void testSetQuote() {
        System.out.println("setQuote");
        List<Quote> quote = null;
        Results instance = new Results();
        instance.setQuotes(quote);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
