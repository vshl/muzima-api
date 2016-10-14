package com.muzima.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class NetworkUtils {
    private static final Logger logger = LoggerFactory.getLogger(NetworkUtils.class.getCanonicalName());

    public static boolean checkServiceAvailability(String IP_address, Proxy proxy, int timeout) {
        boolean serverAvailable = false;
        try {
            HttpURLConnection connection = null;
            if(proxy != null ){
                connection = openConnection(IP_address, proxy);
            } else {
                connection = openConnection(IP_address);
            }
            connection.setConnectTimeout(timeout);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                serverAvailable = true;
            }
        } catch (IOException e) {
            if(proxy == null ){
                logger.error("Unable to create connection to address" + IP_address );
            } else {
                logger.error("Unable to create connection to address : " + IP_address + " Proxy : " + proxy );
            }
        }
        return serverAvailable;
    }
    public static boolean checkServiceAvailability(String server_address, int timeout){
        return checkServiceAvailability(server_address,null, timeout);
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
