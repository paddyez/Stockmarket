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
public class StockManagerDialogTest {
    
    public StockManagerDialogTest() {
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
     * Test of setSymbols method, of class StockManagerDialog.
     */
    @Test
    public void testSetSymbols() {
        System.out.println("setSymbols");
        HashSet<String> stocksymbols = null;
        StockManagerDialog instance = null;
        instance.setSymbols(stocksymbols);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
