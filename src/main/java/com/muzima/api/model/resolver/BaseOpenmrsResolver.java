/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
            httpsURLConnection.setRequestProperty ("Authorization", basicAuth);
            return httpsURLConnection;
        } else {
            String userPassword = getConfiguration().getUsername() + ":" + getConfiguration().getPassword();
            String basicAuth = "Basic " + new String(new Base64().encode(userPassword.getBytes()));
            connection.setRequestProperty("Authorization", basicAuth);
            return connection;
        }
    }
}
