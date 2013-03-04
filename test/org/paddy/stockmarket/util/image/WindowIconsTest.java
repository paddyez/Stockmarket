/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.image;

import java.net.URL;
import java.util.ArrayList;
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
public class WindowIconsTest {
    
    public WindowIconsTest() {
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
     * Test of createScaledIcons method, of class WindowIcons.
     */
    @Test
    public void testCreateScaledIcons() {
        System.out.println("createScaledIcons");
        URL url = null;
        ArrayList expResult = null;
        ArrayList result = WindowIcons.createScaledIcons(url);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResolutionSize method, of class WindowIcons.
     */
    @Test
    public void testGetResolutionSize() {
        System.out.println("getResolutionSize");
        int expResult = 0;
        int result = WindowIcons.getResolutionSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
