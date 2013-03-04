/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.net;

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
public class NetworkTest {
    
    public NetworkTest() {
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
     * Test of isAInterfaceUp method, of class Network.
     */
    @Test
    public void testIsAInterfaceUp() {
        System.out.println("isAInterfaceUp");
        boolean expResult = false;
        boolean result = Network.isAInterfaceUp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isReachable method, of class Network.
     */
    @Test
    public void testIsReachable() {
        System.out.println("isReachable");
        String host = "";
        boolean expResult = false;
        boolean result = Network.isReachable(host);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOs method, of class Network.
     */
    @Test
    public void testIsOs() {
        System.out.println("isOs");
        String identifier = "";
        boolean expResult = false;
        boolean result = Network.isOs(identifier);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
