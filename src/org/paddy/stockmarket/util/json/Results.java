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
     *
     * @return List<Quote> quote
     */
    /*public Quote getQuote()
    {
            return quote;
    }
    /**
     *
     * @param quotes
     */
    public void setQuotes(List<Quote> quote)
    {
            this.quote = quote;
    }
    /**
     *
     * @param quote
     */
    /*public void setQuote(Quote quote)
    {
            this.quote = quote;
    }*/
}
