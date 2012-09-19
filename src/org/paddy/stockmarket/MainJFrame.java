/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.paddy.stockmarket.util.json.Query;
import org.paddy.stockmarket.util.json.QueryContainer;
import org.paddy.stockmarket.util.json.Quote;
import org.paddy.stockmarket.util.json.Results;

/**
 *
 * @author root
 */
public class MainJFrame extends javax.swing.JFrame 
{

    /**
     * Creates new form MAinJFrame
     */
    public MainJFrame()
    {
        initComponents();
        HashSet<String> stocksymbols = getSymbols();
        if(stocksymbols != null)
        {
            Iterator<String> iterator = stocksymbols.iterator();
            String symbols;
            symbols = "";
            while (iterator.hasNext())
            {
                symbols += "\"" + iterator.next() + "\"";
                if(iterator.hasNext())
                {
                    symbols += ",";
                }
            }
            readPrices(symbols);
        }
    }
    private HashSet<String> getSymbols()
    {
        HashSet<String> stockSymbols = null;
        boolean exists = (new File("Symbols")).exists();
        if (exists) 
        {
            try
            {
                FileInputStream fis = new FileInputStream("Symbols");
                ObjectInputStream ois = new ObjectInputStream(fis);
                stockSymbols = (HashSet<String>) ois.readObject();
            }
            catch(FileNotFoundException fnfe)
            {
                System.err.println(fnfe);
            }
            catch(IOException | ClassNotFoundException e)
            {
                System.err.println(e);
            }
            // File or directory exists
        }
        else
        {
            String[] symbols = {"DTE.DE","SAP.DE","CGE.F","ELE.MC","FTE.PA","MSFT","TNE5.DE","DKEX.SG","EURUSD","EURGBP","NESM.F","RWE.DE","SDF.DE","ALV.F","EOAN.F","ENA.F","ENL.F","BPE5.DE","CBK.F"};
            stockSymbols = new HashSet(Arrays.asList(symbols));
            try
            {
                FileOutputStream fos = new FileOutputStream("Symbols");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(stockSymbols);
                oos.close();
            }
            catch(FileNotFoundException fnfe)
            {
                System.err.println(fnfe);
            }
            catch(IOException ioe)
            {
                System.err.println(ioe);
            }
            // File or directory does not exist
        }
        return stockSymbols;
    }
    private void readPrices(String symbols)
    {
        try
        {
                String requestURI = "http://query.yahooapis.com/v1/public/yql?q=";
                String YQLquery = URLEncoder.encode("select Name,Ask,AskRealtime,BidRealtime,StockExchange,DividendYield,PercentChange "
                                                                                                + "from yahoo.finance.quotes "
                                                                                                + "where symbol in (" + symbols + ") | sort(field=\"Name\", descending=\"true\")", "UTF-8");
                String GETparam = "&format=json"
                                                        + "&diagnostics=true"
                                                        + "&env=" + URLEncoder.encode("http://datatables.org/alltables.env", "UTF-8");
                String request = requestURI + YQLquery + GETparam;
                try
                {
                        URL url = new URL(request);
                        try
                        {
                                InputStreamReader isr = new InputStreamReader(url.openStream());
                                BufferedReader in = new BufferedReader(isr);
                                String inputLine;
                                String returnString = "";
                                while ((inputLine = in.readLine()) != null)
                                {
                                        returnString += inputLine;
                                }
                                in.close();
                                QueryContainer queryContainer = new Gson().fromJson(returnString, QueryContainer.class);
                                Query query = queryContainer.getQuery();
                                //System.out.println(query.getCount());
                                Results results = query.getResults();
                                List<Quote> quotes = results.getQuote();
                                Iterator<Quote> iterator = quotes.iterator();
                                while (iterator.hasNext())
                                {
                                        Quote quote = iterator.next();
                                        String name = quote.getName();
                                        String bidRealtime = quote.getBidRealtime();
                                        if(bidRealtime != null)
                                        {
                                                try
                                                {
                                                        float bid = Float.parseFloat(bidRealtime);
                                                        System.out.println(name + ": " + bid);
                                                }
                                                catch(NumberFormatException nfe)
                                                {
                                                        System.err.println(nfe);
                                                }
                                        }
                                        else
                                        {
                                                System.out.println("BidRealtime is null for: " + name);
                                        }
                                }
                        }
                        catch(IOException ioe)
                        {
                                System.err.println(ioe);
                        }
                }
                catch(MalformedURLException mue)
                {
                        System.err.println(mue);
                }
        }
        catch(UnsupportedEncodingException uee)
        {
                System.err.println(uee);
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem2.setText("New");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem3.setText("Open");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, 0));
        jMenuItem4.setText("About");
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    // End of variables declaration//GEN-END:variables
}
