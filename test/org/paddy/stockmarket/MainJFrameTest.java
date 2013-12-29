/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket;

import java.util.HashSet;
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
public class MainJFrameTest {
    
    public MainJFrameTest() {
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
     * Test of setScrollableViewportSize method, of class MainJFrame.
     */
    @Test
    public void testSetScrollableViewportSize() {
        System.out.println("setScrollableViewportSize");
        int width = 0;
        int height = 0;
        MainJFrame instance = new MainJFrame();
        instance.setScrollableViewportSize(width, height);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSymbols method, of class MainJFrame.
     */
    @Test
    public void testGetSymbols() {
        System.out.println("getSymbols");
        MainJFrame instance = new MainJFrame();
        HashSet<String> expResult = null;
        HashSet<String> result = instance.getSymbols();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSymbols method, of class MainJFrame.
     */
    @Test
    public void testSetSymbols() {
        System.out.println("setSymbols");
        HashSet<String> stockSymbols = null;
        MainJFrame instance = new MainJFrame();
        instance.setSymbols(stockSymbols);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSymbolHash method, of class MainJFrame.
     */
    @Test
    public void testSetSymbolHash() {
        System.out.println("setSymbolHash");
        HashSet<String> stockSymbols = null;
        MainJFrame instance = new MainJFrame();
        instance.setSymbolHash(stockSymbols);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isUpAndReachable method, of class MainJFrame.
     */
    @Test
    public void testIsUpAndReachable() {
        System.out.println("isUpAndReachable");
        MainJFrame instance = new MainJFrame();
        boolean expResult = false;
        boolean result = instance.isUpAndReachable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSymbolQueryString method, of class MainJFrame.
     */
    @Test
    public void testGetSymbolQueryString() {
        System.out.println("getSymbolQueryString");
        MainJFrame instance = new MainJFrame();
        String expResult = "";
        String result = instance.getSymbolQueryString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
