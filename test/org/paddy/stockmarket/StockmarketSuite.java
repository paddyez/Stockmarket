/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket;

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
@Suite.SuiteClasses({org.paddy.stockmarket.StockManagerDialogTest.class, org.paddy.stockmarket.util.UtilSuite.class, org.paddy.stockmarket.MainTest.class, org.paddy.stockmarket.MainJFrameTest.class, org.paddy.stockmarket.YQLqueryTest.class})
public class StockmarketSuite {

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
