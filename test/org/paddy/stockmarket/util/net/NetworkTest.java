/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.net;

import java.util.Arrays;
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
    public void testIsInterfaceValidAndReachabl() {
        System.out.println("isAInterfaceUp");
        boolean expResult = true;
        boolean result = Network.isInterfaceValidAndReachable();
        assertEquals(expResult, result);
    }

    /**
     * Test of isReachable method, of class Network.
     */
    @Test
    public void testisRemoteReachable() {
        System.out.println("isReachable");
        String host = "";
        boolean result = false;
        boolean expResult = false;
        result = Network.isRemoteReachable(host);
        assertEquals(expResult, result);
        expResult = true;
        host = "query.yahooapis.com";
        result = Network.isRemoteReachable(host);
        assertEquals(expResult, result);
    }

    /**
     * Test of isOs method, of class Network.
     */
    @Test
    public void testIsOs() {
        System.out.println("isOs");
        String[] possibleOs = {"nix","nux","os x","win"};
        String identifier = "";
        boolean result = false;
        boolean expResult = true;
        for (String os : possibleOs) 
        {
            result = Network.isOs(os);
            if(result == true)
            {
                System.out.println("OS seems to be: " + os);
                break;
            }
        }
        if(result == false )
        {
            System.out.println("Only tested with: " + Arrays.asList(possibleOs).toString().replaceAll("^\\[|\\]$", ""));
        }
        assertEquals(expResult, result);
    }
}
