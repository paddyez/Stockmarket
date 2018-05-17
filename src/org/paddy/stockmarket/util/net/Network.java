/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.net;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 *
 * @author paddy (Patrick-Emil Zörner)
 */
public class Network {
    private static final String OS = System.getProperty("os.name").toLowerCase();
    static final Set<String> validInterfaces = new HashSet(Arrays.asList("en0"));
    /**
     *
     * @return boolean isInterfaceUp
     */
    public static boolean isInterfaceValidAndReachable() {
        boolean isInterfaceUp = false;
        Enumeration<NetworkInterface> interfaces;
        InetAddress iAddress;
        Boolean isReachableb;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            List<Enumeration<InetAddress>> inetAdressesL = Collections.list(interfaces)
                    .stream()
                    .filter(Network::validInterface)
                    .map(iface -> iface.getInetAddresses())
                    .collect(Collectors.toList());
            for(Enumeration<InetAddress> ial : inetAdressesL) {
                while(ial.hasMoreElements()) {
                    iAddress = ial.nextElement();
                    isReachableb = iAddress.isReachable(100);
                    if(isReachableb) {
                        System.out.println(iAddress.getHostAddress() + " is up and reachable");
                        isInterfaceUp = true;
                    }
                    else {
                        System.err.println("Seems possible interface is up and not Loopback. " +
                                "But is not reachable: " + iAddress.getHostAddress());
                    }
                }
            }
        }
        catch (SocketException se) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, se);
        }
        catch (IOException ioe) {
            System.err.println(ioe);
        }
        return isInterfaceUp;
    }
    /**
     *
     * @param host
     * @return boolean reachable
     */
    public static boolean isRemoteReachable(String host) {
        boolean reachable = false;
        if(host.equals("") ||
                host.equals("127.0.0.1") ||
                host.equals("localhost")) {
            System.err.println("Host can not be an empty String, 127.0.0.1 or localhost!");
            return reachable;
        }
        int timeout = 1000;
        try {
            InetAddress[] inetAddresses = InetAddress.getAllByName(host);
            for(InetAddress inetAddress : inetAddresses) {
                System.out.println("Host: " + inetAddress.getHostAddress());
                if(inetAddress instanceof Inet4Address) {
                    reachable = inetAddress.isReachable(timeout);
                    if (reachable) {
                        System.out.println(" is reachable.");
                        return reachable;
                    } else {
                        System.out.println(" is not reachable.");
                    }
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
            if(OS.indexOf("os x") >= 0 ||
                    OS.indexOf("mac os x") >= 0 ||
                    OS.indexOf("nux") >= 0) {
                process = java.lang.Runtime.getRuntime().exec("ping -c 1 " + host);
            }
            else if(OS.indexOf("win") >= 0) {
                process = java.lang.Runtime.getRuntime().exec("ping -n 1 " + host);
            }
            else {
                System.err.println("Unknown system or not yet supported.");
            }
            try {
                int returnVal = process.waitFor();
                if(returnVal == 0) {
                    reachable = true;
                    return reachable;
                }
                else {
                    System.err.println(host + " is not pingable!\nProcess exited with: " + returnVal);
                }
            }
            catch(InterruptedException ie) {
                System.err.println(ie);
            }
        }
        catch(IOException ioe) {
            System.err.println("IOException: " + ioe);
        }
        return reachable;
    }
    /**
     * Possible Values are
     * win for Windows
     * mac os x for Mac
     * nix and nux for Unix and Linux
     * sunos for Solaris
     * @param identifier String
     * @return true when the identifier is found as substring
     */
    public static boolean isOs(String identifier) {
            return (OS.indexOf(identifier) >= 0);
    }
    /**
     * @param iface
     * @return pass
     */
    private static boolean validInterface(NetworkInterface iface) {
        boolean pass = false;
        String ifaceName = iface.getName();
        try {
            if(!iface.isLoopback() &&
                    iface.isUp()) {
                if(validInterfaces.contains(ifaceName)) {
                    System.out.println("Choosing interface: " + ifaceName);
                    pass = true;
                }
                else {
                    System.out.println("Might want to consider interface: " + ifaceName);
                }
            }
        }
        catch (SocketException se) {
            System.err.println("validInterface: " + se);
        }
        return pass;
    }
}
