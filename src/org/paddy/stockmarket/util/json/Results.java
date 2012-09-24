/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;

import java.util.List;

/**
 *
 * @author paddy (Patrick-Emil Zörner)
 */
public class Results
{
    protected List<Quote> quote;
    /**
     *
     * @return
     */
    public List<Quote> getQuote()
    {
            return quote;
    }
    /**
     *
     * @param quote
     */
    public void setQuote(List<Quote> quote)
    {
            this.quote = quote;
    }
}
