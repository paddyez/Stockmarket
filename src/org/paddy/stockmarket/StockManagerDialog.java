package org.paddy.stockmarket;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.paddy.stockmarket.util.json.Query;
import org.paddy.stockmarket.util.json.Quote;
import org.paddy.stockmarket.util.json.Results;

/**
 *
 * @author paddy
 */
public class StockManagerDialog extends javax.swing.JDialog 
{
    /**
     *
     */
    protected MainJFrame parent;
    public static final long serialVersionUID = 12345667890L;
    HashSet<String> stocksymbols;
    /**
     * Creates new form StockManagerDialog
     */
    public StockManagerDialog(MainJFrame parent, boolean modal)
    {
        super(parent, "Manage stocks", modal);
        this.parent = parent;
        initComponents();
        this.stocksymbols = parent.stocksymbols;
        initStockComponents();
    }
    /**
     * 
     * @param stocksymbols HashSet<String>
     */
    protected void setSymbols(HashSet<String> stocksymbols)
    {
        this.stocksymbols = stocksymbols;
    }
    /**
     * 
     */
    private void lookupStock()
    {
        String lookupString, searchString, ownerName, message;
        lookupString = addStockLookupTextField.getText();
        searchString = addStockSearchTextField.getText();
        ownerName = this.getFocusOwner().getName();
        if(ownerName != null && ownerName.equals("SearchTextField") &&
                (lookupString.equals("Enter a yahoo stock symbol") || lookupString.equals("")))
        {
            openBrowser();
        }
        else if(lookupString.equals("Enter a yahoo stock symbol") || lookupString.equals(""))
        {
            addStockLookupButton.setToolTipText("You should enter a yahoo stock symbol first");
        }
        else if(parent.stocksymbols.contains(lookupString))
        {
           message = lookupString + " is already in your stocks!";
                    JOptionPane.showMessageDialog(this,
                        message,
                        "YAHOO Errog",
                        JOptionPane.ERROR_MESSAGE);
            System.out.println(lookupString + " is already in your stocks!");
        }
        else
        {
            boolean yahooFinanceQuotesBlocked = false;
            Query query = null;
            addStockLookupButton.setToolTipText("Last search was: " + lookupString);
            try
            {
                String YQLqueryString, GETparam, request, symbols;
                symbols = parent.getSymbolQueryString();
                YQLqueryString = URLEncoder.encode("select * " +
                                                        "from yahoo.finance.quotes " +
                                                        "where symbol in (" + 
                                                        symbols + ",\"" + lookupString + "\"" +
                                                        ") | sort(field=\"Name\", descending=\"false\")", "UTF-8");
                GETparam = YQLquery.GETparam + URLEncoder.encode("http://datatables.org/alltables.env", "UTF-8");
                request = YQLquery.requestURI + YQLqueryString + GETparam;
                query = YQLquery.yqlQueryResult(request);
            }
            catch(UnsupportedEncodingException uee)
            {
                    System.err.println(uee);
            }
            Results results = query.getResults();
            if(results == null)
            {
                yahooFinanceQuotesBlocked = true;
                String diagnostics = YQLquery.getDiagnostics(query);
                lookupResultLabel.setText("diagnostics");
                JOptionPane.showMessageDialog(this,
                    diagnostics,
                    "YAHOO Errog",
                    JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String symbol, name, bidRealtime, lastTradePriceOnly, bidString;
                List<Quote> quotes = results.getQuotes();
                Iterator<Quote> iterator = quotes.iterator();
                int i=0;
                while (iterator.hasNext())
                {
                    Quote quote = iterator.next();
                    symbol = quote.getSymbol();
                    if(symbol.equals(lookupString))
                    {
                        name = quote.getName();
                        bidRealtime = quote.getBidRealtime();
                        lastTradePriceOnly = quote.getLastTradePriceOnly();
                        bidString = quote.getBid();
                        float bid;
                        bid = (float) 0;
                        if(bidRealtime != null)
                        {
                            try
                            {
                                bid = Float.parseFloat(bidRealtime);
                            }
                            catch(NumberFormatException nfe)
                            {
                                System.err.println(nfe);
                            }
                        }
                        else if(lastTradePriceOnly != null)
                        {
                            try
                            {
                                bid = Float.parseFloat(lastTradePriceOnly);
                            }
                            catch(NumberFormatException nfe)
                            {
                                System.err.println(nfe);
                            }
                        }
                        else if(bidString != null)
                        {
                            try
                            {
                                bid = Float.parseFloat(bidString);
                            }
                            catch(NumberFormatException nfe)
                            {
                                System.err.println(nfe);
                            }
                        }
                        else
                        {
                                System.out.println("BidRealtime is null for: " + name + " " + lastTradePriceOnly);
                        }
                        lookupResultLabel.setText("<html><p>Stockname: " + name +
                                "</p><p>Symbol: " + symbol +
                                "</p><p>Bidprice: " + bid + "</p></html>");
                    }
                }
            }  
        }
    }
    /**
     * 
     */
    private void openBrowser()
    {
        String message, searchString;
        searchString = addStockSearchTextField.getText();
        if(searchString.equals("Search a stock by name online") || searchString.equals(""))
        {
            addStockSearchButton.setToolTipText("You should enter a yahoo stock search string first");
        }
        else
        {
            try
            {
                searchString = URLEncoder.encode(addStockSearchTextField.getText(), "UTF-8");
            }
            catch (UnsupportedEncodingException ueex)
            {
                Logger.getLogger(StockManagerDialog.class.getName()).log(Level.SEVERE, null, ueex);
                System.err.println(ueex);
            }
            if(java.awt.Desktop.isDesktopSupported())
            {
                System.out.println("Desktop is supported!");
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                if(desktop.isSupported(java.awt.Desktop.Action.BROWSE))
                {
                    try
                    {
                        java.net.URI uri;
                        uri = new java.net.URI("http://de.finance.yahoo.com/lookup?s=" + searchString);
                        System.out.println(uri);
                        desktop.browse(uri);
                        addStockSearchButton.setToolTipText("Last search was: " + searchString);
                    }
                    catch ( URISyntaxException | IOException e )
                    {
                        System.err.println( e.getMessage() );
                    }
                }
                else
                {
                    message = "Desktop doesn't support the browse action (fatal)";
                    JOptionPane.showMessageDialog(this,
                        message,
                        "YAHOO Errog",
                        JOptionPane.ERROR_MESSAGE);
                    System.err.println(message);
                }
            }
            else
            {
                message = "Desktop is not supported (fatal)";
                JOptionPane.showMessageDialog(this,
                    message,
                    "YAHOO Errog",
                    JOptionPane.ERROR_MESSAGE);
                System.err.println(message);
            }
        }
    }
    /**
     * 
     */
    private void addStock()
    {
        String lookupString;
        lookupString = addStockLookupTextField.getText();
        if(stocksymbols.contains(lookupString))
        {
            System.out.println("Already in your stocks!");
        }
        else
        {
            System.out.println("Adding " + lookupString + " to your Stocks!");
            stocksymbols.add(lookupString);
            parent.setSymbols(stocksymbols);
            lookupResultLabel.setText(null);
        }
    }
    /**
     * 
     */
    private void initStockComponents()
    {
        JLabel stockLabel;
        for(String stock : stocksymbols)
        {
            System.out.println(stock);
            stockLabel = new JLabel(stock);
            currentStocksPanel.add(stockLabel);
            stockLabel.setPreferredSize(new java.awt.Dimension(100, 15));
        }
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stockManagerTabbedPane = new javax.swing.JTabbedPane();
        currentStocksScrollPane = new javax.swing.JScrollPane();
        currentStocksPanel = new javax.swing.JPanel();
        addStockPanel = new javax.swing.JPanel();
        addStockSearchLabel = new javax.swing.JLabel();
        addStockLookupTextField = new javax.swing.JTextField();
        addStockLookupButton = new javax.swing.JButton();
        searchLabel = new javax.swing.JLabel();
        addStockSearchTextField = new javax.swing.JTextField();
        addStockSearchButton = new javax.swing.JButton();
        lookupResultLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();

        stockManagerTabbedPane.setPreferredSize(new java.awt.Dimension(505, 330));
        stockManagerTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stockManagerTabbedPaneStateChanged(evt);
            }
        });

        currentStocksScrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

        currentStocksPanel.setPreferredSize(new java.awt.Dimension(500, 300));
        currentStocksPanel.setRequestFocusEnabled(false);

        javax.swing.GroupLayout currentStocksPanelLayout = new javax.swing.GroupLayout(currentStocksPanel);
        currentStocksPanel.setLayout(currentStocksPanelLayout);
        currentStocksPanelLayout.setHorizontalGroup(
            currentStocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        currentStocksPanelLayout.setVerticalGroup(
            currentStocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        currentStocksScrollPane.setViewportView(currentStocksPanel);

        stockManagerTabbedPane.addTab("Current stocks", currentStocksScrollPane);

        addStockPanel.setForeground(new java.awt.Color(255, 255, 255));
        addStockPanel.setName("addStockPanel"); // NOI18N
        addStockPanel.setPreferredSize(new java.awt.Dimension(600, 75));

        addStockSearchLabel.setText("Stock Symbol:");
        addStockSearchLabel.setPreferredSize(new java.awt.Dimension(100, 15));

        addStockLookupTextField.setText("Enter a yahoo stock symbol");
        addStockLookupTextField.setToolTipText("Enter a yahoo stock symbol");
        addStockLookupTextField.setPreferredSize(new java.awt.Dimension(500, 25));
        addStockLookupTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lookupTextFieldMouseEntered(evt);
            }
        });
        addStockLookupTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lookupTextFieldKeyPressed(evt);
            }
        });

        addStockLookupButton.setText("Lookup");
        addStockLookupButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        addStockLookupButton.setPreferredSize(new java.awt.Dimension(75, 25));
        addStockLookupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        searchLabel.setText("Search Symbol:");
        searchLabel.setPreferredSize(new java.awt.Dimension(100, 15));

        addStockSearchTextField.setText("Search a stock by name online");
        addStockSearchTextField.setToolTipText("Search a stock by name online");
        addStockSearchTextField.setName("SearchTextField"); // NOI18N
        addStockSearchTextField.setPreferredSize(new java.awt.Dimension(500, 25));
        addStockSearchTextField.setScrollOffset(0);
        addStockSearchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseEntered(evt);
            }
        });

        addStockSearchButton.setText("Search");
        addStockSearchButton.setPreferredSize(new java.awt.Dimension(75, 25));
        addStockSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        lookupResultLabel.setPreferredSize(new java.awt.Dimension(100, 15));

        addButton.setText("Add");
        addButton.setPreferredSize(new java.awt.Dimension(75, 25));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addStockPanelLayout = new javax.swing.GroupLayout(addStockPanel);
        addStockPanel.setLayout(addStockPanelLayout);
        addStockPanelLayout.setHorizontalGroup(
            addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addStockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lookupResultLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(addStockPanelLayout.createSequentialGroup()
                        .addGroup(addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addStockSearchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addStockLookupTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                            .addComponent(addStockSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                .addGap(10, 10, 10)
                .addGroup(addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addStockLookupButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addStockSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        addStockPanelLayout.setVerticalGroup(
            addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addStockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addStockSearchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addStockLookupTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addStockLookupButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addStockSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addStockSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lookupResultLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        stockManagerTabbedPane.addTab("Add stock", addStockPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stockManagerTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stockManagerTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lookupTextFieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lookupTextFieldMouseEntered
        if(addStockLookupTextField.getText().equals("Enter a yahoo stock symbol"))
        {
            addStockLookupTextField.setText(null);
        }
    }//GEN-LAST:event_lookupTextFieldMouseEntered

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        switch (evt.getActionCommand())
        {
            case "Lookup":
                lookupStock();
                break;
            case "Search":
                openBrowser();
                break;
            case "Add":
                addStock();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_buttonActionPerformed

    private void stockManagerTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stockManagerTabbedPaneStateChanged
        int index = stockManagerTabbedPane.getSelectedIndex();
        if(index == 1)
        {
            this.getRootPane().setDefaultButton(addStockLookupButton);
            addStockLookupButton.requestFocus();
        }
    }//GEN-LAST:event_stockManagerTabbedPaneStateChanged

    private void lookupTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lookupTextFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            lookupStock();
        }    
    }//GEN-LAST:event_lookupTextFieldKeyPressed

    private void searchTextFieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseEntered
        if(addStockSearchTextField.getText().equals("Search a stock by name online"))
        {
            addStockSearchTextField.setText(null);
        }
    }//GEN-LAST:event_searchTextFieldMouseEntered

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton addStockLookupButton;
    private javax.swing.JTextField addStockLookupTextField;
    private javax.swing.JPanel addStockPanel;
    private javax.swing.JButton addStockSearchButton;
    private javax.swing.JLabel addStockSearchLabel;
    private javax.swing.JTextField addStockSearchTextField;
    private javax.swing.JPanel currentStocksPanel;
    private javax.swing.JScrollPane currentStocksScrollPane;
    private javax.swing.JLabel lookupResultLabel;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTabbedPane stockManagerTabbedPane;
    // End of variables declaration//GEN-END:variables
}