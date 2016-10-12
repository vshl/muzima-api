/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.resolver;

import com.google.inject.Inject;
import com.muzima.api.config.Configuration;
import com.muzima.search.api.internal.http.CustomKeyStore;
import com.muzima.search.api.model.resolver.Resolver;
import org.apache.commons.codec.binary.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.net.HttpURLConnection;

public abstract class BaseOpenmrsResolver implements Resolver {

    @Inject
    private Configuration configuration;

    @Inject
    private CustomKeyStore customKeyStore;

    /**
     * Get the default openmrs configuration for this resolver.
     *
     * @return the default openmrs configuration for this resolver.
     */
    protected Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Add authentication information to the http url connection.
     *
     * @param connection the original connection without authentication information.
     * @return the connection with authentication information when applicable.
     */
    @Override
    public HttpURLConnection authenticate(final HttpURLConnection connection) {
        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;
            if (customKeyStore != null) {
                SSLContext sslContext = customKeyStore.createContext();
                if (sslContext != null) {
                    httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return (hostname.endsWith("ampath.or.ke"));
                        }
                    };
                    httpsURLConnection.setHostnameVerifier(hostnameVerifier);
                }
            }

            String userPassword = getConfiguration().getUsername() + ":" + getConfiguration().getPassword();
            String basicAuth = "Basic " + new String(new Base64().encode(userPassword.getBytes()));
            httpsURLConnection.setRequestProperty("Authorization", basicAuth);
            return httpsURLConnection;
        } else {
            String userPassword = getConfiguration().getUsername() + ":" + getConfiguration().getPassword();
            String basicAuth = "Basic " + new String(new Base64().encode(userPassword.getBytes()));
            connection.setRequestProperty("Authorization", basicAuth);
            return connection;
        }
    }

    @Override
    public HttpURLConnection setCustomRequestProperties(final HttpURLConnection connection){
        connection.setRequestProperty("Accept-Language", configuration.getPreferredLocale());
        return connection;
    }
}
