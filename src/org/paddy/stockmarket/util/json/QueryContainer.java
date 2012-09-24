/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;

/**
 *
 * @author paddy (Patrick-Emil Zörner)
 */
public class QueryContainer {
    protected Query query;
    /**
     *
     * @return
     */
    public Query getQuery()
    {
            return query;
    }
    /**
     *
     * @param query
     */
    public void setQuery(Query query)
    {
            this.query = query;
    }
}
