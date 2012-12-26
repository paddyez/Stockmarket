/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;
/**
 *
 * @author paddy (Patrick-Emil Zörner)
 */
public class Query
{
    protected int count;
    protected Results results;
    protected Diagnostics diagnostics;
    /**
     *
     * @return int count
     */
    public int getCount()
    {
            return count;
    }
    /**
     *
     * @return Results results
     */
    public Results getResults()
    {
            return results;
    }
    /**
     *
     * @return Diagnostics diagnostics
     */
    public Diagnostics getDiagnostics()
    {
            return diagnostics;
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
    /**
     *
     * @param diagnostics
     */
    public void setDiagnostics(Diagnostics diagnostics)
    {
            this.diagnostics = diagnostics;
    }
}