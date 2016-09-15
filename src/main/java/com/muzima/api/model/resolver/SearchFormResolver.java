/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.resolver;

import com.muzima.api.model.algorithm.FormAlgorithm;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

public class SearchFormResolver extends BaseOpenmrsResolver {

    private static final String REPRESENTATION = "?v=custom:" + FormAlgorithm.STANDARD_FORM_REPRESENTATION;

    /**
     * Return the full REST resource based on the parameters passed to the method.
     *
     * @param resourceParams the parameters of the resource to resolved.
     * @return full uri to the REST resource.
     */
    public String resolve(final Map<String, String> resourceParams) throws IOException {
        StringBuilder paramBuilder = new StringBuilder();
        for (String key : resourceParams.keySet()) {
            paramBuilder.append("&").append(key).append("=").append(URLEncoder.encode(resourceParams.get(key), "UTF-8"));
        }
        return getConfiguration().getServer() + "/ws/rest/v1/muzima/form" + REPRESENTATION + paramBuilder.toString();

    }
}
