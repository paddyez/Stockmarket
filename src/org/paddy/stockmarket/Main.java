/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
                /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
        try
        {
                String requestURI = "http://query.yahooapis.com/v1/public/yql?q=";
                String YQLquery = URLEncoder.encode("select Name,Ask,AskRealtime,BidRealtime,StockExchange,DividendYield,PercentChange "
                                                                                                + "from yahoo.finance.quotes "
                                                                                                + "where symbol in (\"DTE.DE\",\"SAP.DE\",\"CGE.F\",\"ELE.MC\",\"FTE.PA\",\"MSFT\",\"TNE5.DE\",\"DKEX.SG\",\"EURUSD\",\"EURGBP\",\"NESM.F\",\"RWE.DE\",\"SDF.DE\",\"ALV.F\",\"EOAN.F\",\"ENA.F\",\"ENL.F\",\"BPE5.DE\",\"CBK.F\") | sort(field=\"Name\", descending=\"true\")", "UTF-8");
                String GETparam = "&format=json"
                                                        + "&diagnostics=true"
                                                        + "&env=" + URLEncoder.encode("http://datatables.org/alltables.env", "UTF-8");
                String request = requestURI + YQLquery + GETparam;
                try
                {
                        URL url = new URL(request);
                        try
                        {
                                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                String inputLine;
                                String returnString = "";
                                while ((inputLine = in.readLine()) != null)
                                {
                                        returnString += inputLine;
                                }
                                in.close();
                                QueryContainer queryContainer = new Gson().fromJson(returnString, QueryContainer.class);
                                Query query = queryContainer.getQuery();
                                System.out.println(query.getCount());
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
}