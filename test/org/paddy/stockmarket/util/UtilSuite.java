/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author paddy
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({org.paddy.stockmarket.util.image.ImageSuite.class, org.paddy.stockmarket.util.net.NetSuite.class, org.paddy.stockmarket.util.json.JsonSuite.class})
public class UtilSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
