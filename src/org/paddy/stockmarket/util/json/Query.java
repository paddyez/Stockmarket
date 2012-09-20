/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;

/**
 *
 * @author paddy
 */
public class Query
{
    protected int count;
    protected Results results;
    /**
     *
     * @return
     */
    public int getCount()
    {
            return count;
    }
    /**
     *
     * @return
     */
    public Results getResults()
    {
            return results;
    }
    /**
     *
     * @param count
     */
    public void setCount(int count)
    {
            this.count = count;
    }
    /**
     *
     * @param results
     */
    public void setResults(Results results)
    {
            this.results = results;
    }
}
