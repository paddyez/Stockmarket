/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;

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
@Suite.SuiteClasses({org.paddy.stockmarket.util.json.DiagnosticsTest.class, org.paddy.stockmarket.util.json.ResultsTest.class, org.paddy.stockmarket.util.json.QuoteTest.class, org.paddy.stockmarket.util.json.JavascriptTest.class, org.paddy.stockmarket.util.json.QueryTest.class, org.paddy.stockmarket.util.json.QueryContainerTest.class})
public class JsonSuite {

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
