package org.paddy.stockmarket.util.json;
import java.util.List;
/**
 *
 * @author paddy (Patrick-Emil Zörner)
 */
public class Results
{
    protected List<Quote> quote;
    //protected Quote quote;
    /**
     *
     * @return List<Quote> quote
     */
    public List<Quote> getQuotes()
    {
            return quote;
    }
    /**
     * @param quote
     */
    public void setQuotes(List<Quote> quote)
    {
            this.quote = quote;
    }
}