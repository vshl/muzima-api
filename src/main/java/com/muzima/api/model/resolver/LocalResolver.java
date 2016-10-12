/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.resolver;

import com.muzima.search.api.model.resolver.Resolver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public class LocalResolver implements Resolver {

    /**
     * Return the full REST resource based on the parameters passed to the method.
     *
     * @param resourceParams the parameters of the resource to resolved.
     * @return full uri to the REST resource.
     */
    public String resolve(final Map<String, String> resourceParams) throws IOException {
        throw new IOException("Remote resource is not available for this object.");
    }

    /**
     * Add authentication information to the http url connection.
     *
     * @param connection the original connection without authentication information.
     * @return the connection with authentication information when applicable.
     */
    public HttpURLConnection authenticate(final HttpURLConnection connection) throws IOException {
        return connection;
    }

    @Override
    public HttpURLConnection setCustomRequestProperties(final HttpURLConnection connection){
        return connection;
    }
}
