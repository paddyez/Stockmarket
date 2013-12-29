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
public class DiagnosticsTest {
    
    public DiagnosticsTest() {
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
     * Test of getJavascript method, of class Diagnostics.
     */
    @Test
    public void testGetJavascript() {
        System.out.println("getJavascript");
        Diagnostics instance = new Diagnostics();
        Object expResult = null;
        Object result = instance.getJavascript();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setJavascript method, of class Diagnostics.
     */
    @Test
    public void testSetJavascript() {
        System.out.println("setJavascript");
        Javascript javascript = null;
        Diagnostics instance = new Diagnostics();
        instance.setJavascript(javascript);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
