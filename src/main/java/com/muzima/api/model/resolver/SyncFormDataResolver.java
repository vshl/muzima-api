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

import org.apache.commons.codec.binary.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public class SyncFormDataResolver extends BaseOpenmrsResolver {

    /**
     * Add authentication information to the http url connection.
     *
     * @param connection the original connection without authentication information.
     * @return the connection with authentication information when applicable.
     */
    @Override
    public HttpURLConnection authenticate(final HttpURLConnection connection) {
        HttpURLConnection authenticatedConnection = super.authenticate(connection);
        String userPassword = getConfiguration().getUsername() + ":" + getConfiguration().getPassword();
        String basicAuth = "Basic " + new String(new Base64().encode(userPassword.getBytes()));
        authenticatedConnection.setRequestProperty ("Authorization", basicAuth);
        return authenticatedConnection;
    }

    /**
     * Return the full REST resource based on the parameters passed to the method.
     *
     * @param resourceParams the parameters of the resource to resolved.
     * @return full uri to the REST resource.
     */
    @Override
    public String resolve(final Map<String, String> resourceParams) throws IOException {
        return getConfiguration().getServer() + "/ws/rest/v1/muzima/queuedata";
    }
}
