/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.paddy.stockmarket.util.json.Query;

/**
 *
 * @author paddy
 */
public class YQLqueryTest {
    
    public YQLqueryTest() {
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
     * Test of yqlQueryResult method, of class YQLquery.
     */
    @Test
    public void testYqlQueryResult() {
        System.out.println("yqlQueryResult");
        String request = "";
        Query expResult = null;
        Query result = YQLquery.yqlQueryResult(request);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDiagnostics method, of class YQLquery.
     */
    @Test
    public void testGetDiagnostics() {
        System.out.println("getDiagnostics");
        Query query = null;
        String expResult = "";
        String result = YQLquery.getDiagnostics(query);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
