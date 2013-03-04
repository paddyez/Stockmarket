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
}
