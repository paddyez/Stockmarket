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
    public int getCount()
    {
            return count;
    }
    public Results getResults()
    {
            return results;
    }
    public void setCount(int count)
    {
            this.count = count;
    }
    public void setResults(Results results)
    {
            this.results = results;
    }    
}
