package org.paddy.stockmarket.util.json;
import java.util.List;
/**
 *
 * @author paddy (Patrick-Emil Zörner)
 */
public class Results
{
    protected List<Quote> quotes;
    protected Quote quote;
    /**
     *
     * @return List<Quote> quote
     */
    public List<Quote> getQuotes()
    {
            return quotes;
    }
    /**
     *
     * @return List<Quote> quote
     */
    public Quote getQuote()
    {
            return quote;
    }
    /**
     *
     * @param quotes
     */
    public void setQuotes(List<Quote> quotes)
    {
            this.quotes = quotes;
    }
    /**
     *
     * @param quote
     */
    public void setQuote(Quote quote)
    {
            this.quote = quote;
    }
}
