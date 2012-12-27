package org.paddy.stockmarket;
import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import org.paddy.stockmarket.util.image.WindowIcons;
import org.paddy.stockmarket.util.json.Diagnostics;
import org.paddy.stockmarket.util.json.Javascript;
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
        URL url;
        ArrayList<Image> imageList;
        url = getClass().getResource("Stockmarket.png");
        imageList = WindowIcons.createScaledIcons(url);
        if(!imageList.isEmpty())
        {
            if(imageList.size() == WindowIcons.getResolutionSize())
            {
                this.setIconImages(imageList);
            }
            else
            {
                image = Toolkit.getDefaultToolkit().getImage(url);
                this.setIconImage(image);
            }
        }
        initComponents();
        stocksymbols = getSymbols();
    }
    /**
     *
     * @return HashSet<String> of stock symbols
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
                FileInputStream fis;
                BufferedInputStream bis;
                fis = new FileInputStream("Symbols");
                bis = new BufferedInputStream(fis);
                bis.mark(1);
                int empty = bis.read();
                if(empty == -1)
                {
                    System.err.println("File Symbols is empty!");
                }
                else
                {
                    bis.reset();
                    ObjectInputStream ois = new ObjectInputStream(bis);
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
                FileInputStream fis;
                BufferedInputStream bis;
                fis = new FileInputStream("Symbols");
                bis = new BufferedInputStream(fis);
                bis.mark(1);
                int empty = bis.read();
                if(empty == -1)
                {
                    System.err.println("File Symbols is empty!");
                }
                else
                {
                    bis.reset();
                    ObjectInputStream ois = new ObjectInputStream(bis);
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
                FileOutputStream fos;
                ObjectOutputStream oos;
                fos = new FileOutputStream("Symbols");
                oos = new ObjectOutputStream(fos);
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
        Date date = new Date();
        boolean yahooFinanceQuotesBlocked = false;
        JInternalFrame jInternalFrame=new JInternalFrame("Prices on: "+date.toString(),
                                                         true, //resizable
                                                         true, //closable
                                                         true, //maximizable
                                                         true);
        Object[][] rowData = new Object[stocksymbols.size()][6];
        JTable table = new JTable(rowData, COLUMN_NAMES);
        try
        {
            String requestURI = "http://query.yahooapis.com/v1/public/yql?q=";
            String YQLquery = URLEncoder.encode("select * " +
                                                    "from yahoo.finance.quotes " +
                                                    "where symbol in (" + 
                                                    symbols +
                                                    ") | sort(field=\"Name\", descending=\"false\")", "UTF-8");
            String GETparam = "&format=json" +
                                "&diagnostics=true" +
                                "&env=" + URLEncoder.encode("http://datatables.org/alltables.env", "UTF-8");
            String request = requestURI + YQLquery + GETparam;
            try
            {
                URL url = new URL(request);
                try
                {
                    InputStreamReader isr;
                    BufferedReader br;
                    isr = new InputStreamReader(url.openStream());
                    br = new BufferedReader(isr);
                    String inputLine;
                    String returnString;
                    returnString = "";
                    while ((inputLine = br.readLine()) != null)
                    {
                            returnString += inputLine;
                    }
                    br.close();
                    QueryContainer queryContainer = new Gson().fromJson(returnString, QueryContainer.class);
                    Query query = queryContainer.getQuery();
                    //System.out.println(query.getCount());
                    Results results = query.getResults();
                    if(results == null)
                    {
                        yahooFinanceQuotesBlocked = true;
                        Diagnostics diagnostics = query.getDiagnostics();
                        Javascript javascript = diagnostics.getJavascript();
                        String content = javascript.getContent();
                        JOptionPane.showMessageDialog(this,
                            content,
                            "YAHOO Errog",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {        
                        List<Quote> quotes = results.getQuote();
                        Iterator<Quote> iterator = quotes.iterator();
                        int i=0;
                        while (iterator.hasNext())
                        {
                            Quote quote = iterator.next();
                            String symbol = quote.getSymbol();
                            rowData[i][0] = symbol;
                            String name = quote.getName();
                            rowData[i][1] = name;
                            String bidRealtime = quote.getBidRealtime();
                            rowData[i][2] = bidRealtime;
                            String lastTradePriceOnly = quote.getLastTradePriceOnly();
                            String bidString = quote.getBid();
                            String changeFromFiftydayMovingAverage = quote.getChangeFromFiftydayMovingAverage();
                            rowData[i][3] = changeFromFiftydayMovingAverage;
                            String changeFromTwoHundreddayMovingAverage = quote.getChangeFromTwoHundreddayMovingAverage();
                            rowData[i][4] = changeFromTwoHundreddayMovingAverage;
                            String changeInPercent = quote.getChangeinPercent();
                            rowData[i][5] = changeInPercent;
                            float bid;
                            if(bidRealtime != null)
                            {
                                try
                                {
                                    bid = Float.parseFloat(bidRealtime);
                                    /*
                                    System.out.println(name + ": " + bid +
                                            " ChangeFromFiftydayMovingAverage:"+changeFromFiftydayMovingAverage +
                                            " ChangeFromTwoHundreddayMovingAverage :" + changeFromTwoHundreddayMovingAverage +
                                            " ChangeinPercent:" + changeInPercent);
                                    */
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
                                    rowData[i][2] = lastTradePriceOnly;
                                    //System.out.println(name + ": " + lastPrice);
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
                                    rowData[i][2] = bidString;
                                    //System.out.println(name + ": " + bid);
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
                            i++;
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
        if(yahooFinanceQuotesBlocked)
        {
            jInternalFrame.dispose();
            jInternalFrame = null;
            table = null;
            rowData = null;
        }
        else
        {
            TableColumn column = null;
            JScrollPane scrollPane = new JScrollPane(table);
            table.setPreferredScrollableViewportSize(preferredScrollableViewportSize);
            jInternalFrame.getContentPane().add(scrollPane);
            jInternalFrame.setSize(jInternalFrame.getPreferredSize());
            int openFrameCount=jDesktopPane1.getAllFrames().length;
            jInternalFrame.setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
            jDesktopPane1.add(jInternalFrame);
            jInternalFrame.setVisible(true);
        }
    }
    /**
     * 
     */
    public void setScrollableViewportSize(int width, int height)
    {
        this.preferredScrollableViewportSize = new Dimension(width, height);
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
        jMenuItem3 = new javax.swing.JMenuItem();
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

        jMenuEdit.setMnemonic(KeyEvent.VK_E);
        jMenuEdit.setText("Edit");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem3.setMnemonic(KeyEvent.VK_S);
        jMenuItem3.setText("Stocks");
        jMenuItem3.setToolTipText("");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditStocksActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItem3);

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
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewActionPerformed
        if(stocksymbols != null)
        {
            if(org.paddy.stockmarket.util.net.Network.isAInterfaceUp())
            {
                if(org.paddy.stockmarket.util.net.Network.isReachable(QUERY_YAHOOAPIS_COM))
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
                else
                {
                    JOptionPane.showMessageDialog(this,
                        "Are you connected to the internet?\nMaybe your DNS-server is down?",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
           else
            {
                JOptionPane.showMessageDialog(this,
                    "There seems no internet interface up other than loopback.\nCheck there is a connection.",
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

    private void jMenuItemEditStocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditStocksActionPerformed
        int lx, ly, width, height, shrink;
        shrink = 20;
        if(managerDialog == null)
        {
            managerDialog = new StocksManagerDialog(this);
        }
        managerDialog.setSymbols(stocksymbols);
        /* Calculate location and dimansion of the frame */
        lx = (int)getLocationOnScreen().getX()+shrink/2;
        ly = (int)getLocationOnScreen().getY()+shrink/2;
        Point managerLocation = new Point(lx, ly);
        width = (int)getSize().getWidth() - shrink;
        height = (int)getSize().getHeight() - shrink;
        Dimension stockManagerSize = new Dimension(width, height);
        /* Now set location and dimension */
        managerDialog.setLocation(managerLocation);
        managerDialog.setSize(stockManagerSize);
        managerDialog.setIconImage(image);
        managerDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItemEditStocksActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMenuItemAbout;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemSave;
    // End of variables declaration//GEN-END:variables
    public static final long serialVersionUID = 12345667890L;
    private static StocksManagerDialog managerDialog;
    private static final String QUERY_YAHOOAPIS_COM = "query.yahooapis.com";
    private HashSet<String> stocksymbols;
    /**
     * The column names for the table with the stock prices and other
     * information.
     */
    private final String[] COLUMN_NAMES = {"Symbol",
                                           "Name",
                                           "Price",
                                           "Change from 50 day moving average",
                                           "Change from 200 day moving average",
                                           "Change in %"};
    private Image image;
    private Dimension preferredScrollableViewportSize = new Dimension(800, 350);
    /**
     * The x and y offset of the desktop displayed by the main frame
     * @see #MAIN_FRAME_DESKTOP
     */
    static final int xOffset = 20, yOffset = 20;
}
