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
     * @return List<Quote> quote
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
