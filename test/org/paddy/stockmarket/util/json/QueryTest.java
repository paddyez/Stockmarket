/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;

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
public class QueryTest {
    
    public QueryTest() {
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
     * Test of getCount method, of class Query.
     */
    @Test
    public void testGetCount() {
        System.out.println("getCount");
        Query instance = new Query();
        int expResult = 0;
        int result = instance.getCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResults method, of class Query.
     */
    @Test
    public void testGetResults() {
        System.out.println("getResults");
        Query instance = new Query();
        Results expResult = null;
        Results result = instance.getResults();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDiagnostics method, of class Query.
     */
    @Test
    public void testGetDiagnostics() {
        System.out.println("getDiagnostics");
        Query instance = new Query();
        Diagnostics expResult = null;
        Diagnostics result = instance.getDiagnostics();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCount method, of class Query.
     */
    @Test
    public void testSetCount() {
        System.out.println("setCount");
        int count = 0;
        Query instance = new Query();
        instance.setCount(count);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setResults method, of class Query.
     */
    @Test
    public void testSetResults() {
        System.out.println("setResults");
        Results results = null;
        Query instance = new Query();
        instance.setResults(results);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDiagnostics method, of class Query.
     */
    @Test
    public void testSetDiagnostics() {
        System.out.println("setDiagnostics");
        Diagnostics diagnostics = null;
        Query instance = new Query();
        instance.setDiagnostics(diagnostics);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
