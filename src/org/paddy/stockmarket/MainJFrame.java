/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket;

import com.google.gson.Gson;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.paddy.stockmarket.util.image.WindowIcons;
import org.paddy.stockmarket.util.json.Query;
import org.paddy.stockmarket.util.json.QueryContainer;
import org.paddy.stockmarket.util.json.Quote;
import org.paddy.stockmarket.util.json.Results;

/**
 *
 * @author paddy (Patrick-Emil Zörner)
 */
public class MainJFrame extends javax.swing.JFrame
{

    /**
     * Creates new form MAinJFrame
     */
    public MainJFrame()
    {
        super("Stockmarkets");
        URL url = getClass().getResource("Stockmarket.png");
        try
        {
            boolean exists = (new File(url.toURI())).exists();
            if(exists)
            {
                ArrayList<Image> imageList = WindowIcons.createScaledIcons(url);
                if(imageList.size() == WindowIcons.getResolutionSize())
                {
                    this.setIconImages(imageList);
                }
                else
                {
                    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Stockmarket.png"));
                    this.setIconImage(image);
                }
            }
            else
            {
                System.err.println("Iconfile: " + url + " does not exist!");
            }
        }
        catch(URISyntaxException urise)
        {
            System.err.println(urise);
        }
        initComponents();
        Enumeration<NetworkInterface> interfaces;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements())
            {
                NetworkInterface interf = interfaces.nextElement();
                if (interf.isUp() && !interf.isLoopback())
                {
                  System.out.println(interf.getName() + " up");
                }
                else
                {
                  System.out.println(interf.getName() + " down"); 
                }
            }
        }
        catch (SocketException ex)
        {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        stocksymbols = getSymbols();
    }
    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private HashSet<String> getSymbols()
    {
        HashSet<String> stockSymbols = null;
        boolean exists = (new File("Symbols")).exists();
        if (exists)
        {
            try
            {
                FileInputStream fis = new FileInputStream("Symbols");
                int empty = fis.read();
                if(empty == -1)
                {
                    System.err.println("File Symbols is empty!");
                }
                else
                {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    stockSymbols = (HashSet<String>) ois.readObject();
                }
            }
            catch(FileNotFoundException fnfe)
            {
                System.err.println(fnfe);
            }
            catch(IOException | ClassNotFoundException e)
            {
                System.err.println(e);
            }
        }
        return stockSymbols;
    }
    /**
     *
     * @param stockSymbols
     */
    @SuppressWarnings("unchecked")
    private void setSymbols(HashSet<String> stockSymbols)
    {
        HashSet<String> stockSymbolsFile = null;
        boolean exists = (new File("Symbols")).exists();
        if (exists)
        {
            try
            {
                FileInputStream fis = new FileInputStream("Symbols");
                int empty = fis.read();
                if(empty == -1)
                {
                    System.err.println("File Symbols is empty!");
                }
                else
                {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    stockSymbolsFile = (HashSet<String>) ois.readObject();
                }
            }
            catch(FileNotFoundException fnfe)
            {
                System.err.println(fnfe);
            }
            catch(IOException | ClassNotFoundException e)
            {
                System.err.println(e);
            }
        }
        if(stockSymbolsFile != null && stockSymbols != null)
        {
            stockSymbols.addAll(stockSymbolsFile);
        }

        if(stockSymbols.size()<100)
        {
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
        }
    }
    /**
     *
     * @param symbols
     */
    private void readPrices(String symbols)
    {
        try
        {
                String requestURI = "http://query.yahooapis.com/v1/public/yql?q=";
                String YQLquery = URLEncoder.encode("select Name,Ask,AskRealtime,BidRealtime,StockExchange,DividendYield,PercentChange,Symbol,LastTradePriceOnly,Bid "
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
                                        String lastTradePriceOnly = quote.getLastTradePriceOnly();
                                        String bidString = quote.getBid();
                                        float bid;
                                        if(bidRealtime != null)
                                        {
                                            try
                                            {
                                                bid = Float.parseFloat(bidRealtime);
                                                System.out.println(name + ": " + bid);
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
                                                float lastPrice = Float.parseFloat(lastTradePriceOnly);
                                                System.out.println(name + ": " + lastPrice);
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
                                                System.out.println(name + ": " + bid);
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
        jMenuItem2 = new javax.swing.JMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuHelp = new javax.swing.JMenu();
        JMenuItemAbout = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenuFile.setMnemonic(KeyEvent.VK_F);
        jMenuFile.setText("File");

        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNew.setMnemonic(KeyEvent.VK_N);
        jMenuItemNew.setText("New");
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemNew);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setMnemonic(KeyEvent.VK_O);
        jMenuItemOpen.setText("Open");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpen);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSave);

        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("Edit");
        jMenuBar1.add(jMenuEdit);

        jMenuHelp.setMnemonic(KeyEvent.VK_H);
        jMenuHelp.setText("Help");

        JMenuItemAbout.setMnemonic(KeyEvent.VK_A);
        JMenuItemAbout.setText("About");
        jMenuHelp.add(JMenuItemAbout);

        jMenuBar1.add(jMenuHelp);

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

    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewActionPerformed
        if(stocksymbols != null)
        {
            boolean reachable = false;
            try
            {
                reachable = InetAddress.getByName("query.yahooapis.com").isReachable(10000);
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
            catch(IOException ioe)
            {
                System.err.println(ioe);
                JOptionPane.showMessageDialog(this,
                    "Are you connected to the internet?\nMaybe your DNS-server is down?",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
        else
        {
            System.err.println("No stock symbols loaded.");
            JOptionPane.showMessageDialog(this,
                "No stock symbols loaded.\nTry saving at least on symbol.",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemNewActionPerformed

    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemOpenActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        String[] symbolsArray = {"DTE.DE","SAP.DE","CGE.F","ELE.MC","FTE.PA","MSFT","TNE5.DE","DKEX.SG","EURUSD=X","EURGBP=X","NESM.F","RWE.DE","SDF.DE","ALV.F","EOAN.F","ENA.F","ENL.F","BPE5.DE","CBK.F"};
        stocksymbols = new HashSet<>(Arrays.asList(symbolsArray));
        setSymbols(stocksymbols);
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMenuItemAbout;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemSave;
    // End of variables declaration//GEN-END:variables
    public static final long serialVersionUID = 12345667890L;
    private HashSet<String> stocksymbols;
}
