/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.net;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author paddy
 */
public class Network
{
    /**
     *
     * @return
     */
    public static boolean isAInterfaceUp()
    {
        boolean isInterfaceUp = false;
        Enumeration<NetworkInterface> interfaces;
        try
        {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements())
            {
                NetworkInterface interf = interfaces.nextElement();
                if (interf.isUp() && !interf.isLoopback())
                {
                    isInterfaceUp = true;
                    return isInterfaceUp;
                }
                else
                {
                    System.out.println(interf.getName() + " down");
                }
            }
        }
        catch (SocketException ex)
        {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isInterfaceUp;
    }
    /**
     *
     * @param host
     * @return
     */
    public static boolean isReachable(String host)
    {
        boolean reachable;
        reachable = false;
        int timeout = 100;
        try
        {
            InetAddress[] inetAddresses = InetAddress.getAllByName(host);
            for(InetAddress inetAddress : inetAddresses)
            {
                reachable = inetAddress.isReachable(timeout);
                if(reachable)
                {
                    return reachable;
                }
            }
            /*
             * A Hackish Workaround
             * java InetAddress.isReachable only permits port 7
             * which in most cases is closed by firewall
             * This will return 0 if the host is reachable.
             * Otherwise, you will get "2" as a return value.
             */
            Process process = null;
            if(isOs("nux") || isOs("nix"))
            {
                process = java.lang.Runtime.getRuntime().exec("ping -c 1 " + host);
            }
            else if(isOs("win"))
            {
                process = java.lang.Runtime.getRuntime().exec("ping -n 1 " + host);
            }
            else
            {
                System.err.println("Unknown system or not yet supported.");
            }  
            try
            {
                int returnVal = process.waitFor();
                if(returnVal == 0)
                {
                    reachable = true;
                    return reachable;
                }
                else
                {
                    System.err.println(host + " Is not pingable!");
                }
            }
            catch(InterruptedException ie)
            {
                System.err.println(ie);
            }
        }
        catch(IOException ioe)
        {
            System.err.println(ioe);
        }
        return reachable;
    }
    /**
     * Possible Values are
     * win for Windows
     * mac for Mac
     * nix and nux for Unix and Linux
     * sunos for Solaris
     * @param String identifier
     * @return 
     */
    public static boolean isOs(String identifier) 
    {
            String os = System.getProperty("os.name").toLowerCase();
            return (os.indexOf(identifier) >= 0);
    }
}
