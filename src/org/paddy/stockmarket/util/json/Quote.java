/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.json;
/**
 *
 * @author paddy (Patrick-Emil Zörner)
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
    protected String Symbol;
    protected String LastTradePriceOnly;
    protected String Bid;
    /**
     *
     * @return
     */
    public String getAskRealtime()
    {
            return AskRealtime;
    }
    /**
     *
     * @return
     */
    public String getBidRealtime()
    {
            return BidRealtime;
    }
    /**
     *
     * @return
     */
    public String getName()
    {
            return Name;
    }
    /**
     *
     * @return
     */
    public String getStockExchange()
    {
            return StockExchange;
    }
    /**
     *
     * @return
     */
    public String getIvidendYield()
    {
            return IvidendYield;
    }
    /**
     *
     * @return
     */
    public String getPercentChange()
    {
            return PercentChange;
    }
    /**
     *
     * @return
     */
    public String getAsk()
    {
            return Ask;
    }
    /**
     *
     * @return
     */
    public String getSymbol()
    {
            return Symbol;
    }
    /**
     *
     * @return
     */
    public String getLastTradePriceOnly()
    {
        return LastTradePriceOnly;
    }
    /**
     *
     * @return
     */
    public String getBid()
    {
        return Bid;
    }
    /**
     *
     * @param AskRealtime
     */
    public void setAskRealtime(String AskRealtime)
    {
            this.AskRealtime = AskRealtime;
    }
    /**
     *
     * @param BidRealtime
     */
    public void setBidRealtime(String BidRealtime)
    {
            this.BidRealtime = BidRealtime;
    }
    /**
     *
     * @param Name
     */
    public void setName(String Name)
    {
            this.Name = Name;
    }
    /**
     *
     * @param StockExchange
     */
    public void setStockExchange(String StockExchange)
    {
            this.StockExchange = StockExchange;
    }
    /**
     *
     * @param IvidendYield
     */
    public void setIvidendYield(String IvidendYield)
    {
            this.IvidendYield = IvidendYield;
    }
    /**
     *
     * @param PercentChange
     */
    public void setPercentChange(String PercentChange)
    {
            this.PercentChange = PercentChange;
    }
    /**
     *
     * @param Ask
     */
    public void setAsk(String Ask)
    {
            this.Ask = Ask;
    }
    /**
     *
     * @param Symbol
     */
    public void setSymbol(String Symbol)
    {
            this.Symbol = Symbol;
    }
    /**
     *
     * @param LastTradePriceOnly
     */
    public void setLastTradePriceOnly(String LastTradePriceOnly)
    {
        this.LastTradePriceOnly = LastTradePriceOnly;
    }
    /**
     *
     * @param Bid
     */
    public void setBid(String Bid)
    {
        this.Bid = Bid;
    }
}
