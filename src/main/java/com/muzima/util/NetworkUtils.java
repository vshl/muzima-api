package com.muzima.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class NetworkUtils {
    private static final Logger logger = LoggerFactory.getLogger(NetworkUtils.class.getCanonicalName());

    public static boolean isAddressReachable(String address, Proxy proxy, int timeout) {
        boolean serverAvailable = false;
        try {
            HttpURLConnection connection = null;
            if(proxy != null ){
                connection = openConnection(address, proxy);
            } else {
                connection = openConnection(address);
            }
            connection.setConnectTimeout(timeout);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                serverAvailable = true;
            }
        } catch (IOException e) {
            if(proxy == null ){
                logger.error("Unable to create connection to address" + address );
            } else {
                logger.error("Unable to create connection to address : " + address + " Proxy : " + proxy );
            }
        }
        return serverAvailable;
    }

    public static boolean isAddressReachable(String address, int timeout){
        return isAddressReachable(address,null, timeout);
    }

    public static boolean isAddressReachable(final String address){
        return NetworkUtils.isAddressReachable(address, Constants.CONNECTION_TIMEOUT);
    }

    public static HttpURLConnection openConnection(String address)  throws IOException {
        URL url = new URL(address);
        return (HttpURLConnection)url.openConnection();

    }

    public static HttpURLConnection openConnection(String address, Proxy proxy)  throws IOException {
        URL url = new URL(address);
        return (HttpURLConnection)url.openConnection(proxy);
    }
}
