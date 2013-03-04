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
public class JavascriptTest {
    
    public JavascriptTest() {
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
     * Test of getName method, of class Javascript.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Javascript instance = new Javascript();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVerb method, of class Javascript.
     */
    @Test
    public void testGetVerb() {
        System.out.println("getVerb");
        Javascript instance = new Javascript();
        String expResult = "";
        String result = instance.getVerb();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContent method, of class Javascript.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        Javascript instance = new Javascript();
        String expResult = "";
        String result = instance.getContent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Javascript.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Javascript instance = new Javascript();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVerb method, of class Javascript.
     */
    @Test
    public void testSetVerb() {
        System.out.println("setVerb");
        String verb = "";
        Javascript instance = new Javascript();
        instance.setVerb(verb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setContent method, of class Javascript.
     */
    @Test
    public void testSetContent() {
        System.out.println("setContent");
        String content = "";
        Javascript instance = new Javascript();
        instance.setContent(content);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
