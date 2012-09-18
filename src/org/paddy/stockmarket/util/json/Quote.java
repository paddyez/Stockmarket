/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;

/**
 *
 * @author paddy
 */
public class Quote
{
    /**
     *
     */
    protected String AskRealtime;
    protected String BidRealtime;
    protected String Name;
    protected String StockExchange;
    protected String IvidendYield;
    protected String PercentChange;
    protected String Ask;
    public String getAskRealtime()
    {
            return AskRealtime;
    }
    public String getBidRealtime()
    {
            return BidRealtime;
    }
    public String getName()
    {
            return Name;
    }
    public String getStockExchange()
    {
            return StockExchange;
    }
    public String getIvidendYield()
    {
            return IvidendYield;
    }
    public String getPercentChange()
    {
            return PercentChange;
    }
    public String getAsk()
    {
            return Ask;
    }
    public void setAskRealtime()
    {
            this.AskRealtime = AskRealtime;
    }
    public void setBidRealtime()
    {
            this.BidRealtime = BidRealtime;
    }
    public void setName()
    {
            this.Name = Name;
    }
    public void setStockExchange()
    {
            this.StockExchange = StockExchange;
    }
    public void setIvidendYield()
    {
            this.IvidendYield = IvidendYield;
    }
    public void setPercentChange()
    {
            this.PercentChange = PercentChange;
    }
    public void setAsk()
    {
            this.Ask = Ask;
    }    
}
