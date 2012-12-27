package org.paddy.stockmarket;

import java.util.HashSet;
import javax.swing.JDialog;
import javax.swing.JFrame;
/**
 *
 * @author paddy
 */
public class StocksManagerDialog extends JDialog
{
    public static final long serialVersionUID = 12345667890L;
    HashSet<String> stocksymbols;
    /**
     * 
     * @param 
     */
    public StocksManagerDialog(JFrame parent)
    {
        super(parent, "Manage stocks", true);        
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    /**
     * 
     * @param stocksymbols HashSet<String>
     */
    protected void setSymbols(HashSet<String> stocksymbols)
    {
        this.stocksymbols = stocksymbols;
    }
}
