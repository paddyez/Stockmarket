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
        List<InetAddress> inetAddresses = null;
        try {
            inetAddresses = Arrays.asList(InetAddress.getAllByName(host));
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        reachable = inetAddresses.stream()
                .filter(Network::ipv4)
                .anyMatch(Network::reachable);
        if(reachable == false) {
            System.out.println("Pinging host.");
            reachable = pingHost(host);
        }
        return reachable;
    }
    /*
     * A Hackish Workaround
     * java InetAddress.isReachable only permits port 7
     * which in most cases is closed by firewall
     * This will return 0 if the host is reachable.
     * Otherwise, you will get "2" as a return value.
     */
    private static boolean pingHost(String host) {
        boolean pingable = false;
        Process process = null;
        try {
            if (OS.indexOf("os x") >= 0 ||
                    OS.indexOf("mac os x") >= 0 ||
                    OS.indexOf("nux") >= 0) {
                process = Runtime.getRuntime().exec("ping -c 1 " + host);
            } else if (OS.indexOf("win") >= 0) {
                process = java.lang.Runtime.getRuntime().exec("ping -n 1 " + host);
            } else {
                System.err.println("Unknown system or not yet supported.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            int returnVal = process.waitFor();
            if (returnVal == 0) {
                pingable = true;
            } else {
                System.err.println(host + " is not pingable!\nProcess exited with: " + returnVal);
            }
        } catch (InterruptedException ie) {
            System.err.println(ie);
        }
        return pingable;
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
    private static boolean ipv4(InetAddress ia) {
        return ia instanceof Inet4Address;
    }
    private static boolean reachable(InetAddress ia) {
        boolean reachable = false;
        int timeout = 1000;
        try {
            reachable = !ia.isReachable(timeout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reachable;
    }
}