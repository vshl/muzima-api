/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.muzima.api.model.resolver;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

public class SearchFormTemplateResolver extends BaseOpenmrsResolver {

    private static final String REPRESENTATION = "?v=custom:(uuid,name,version)";

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
        return getConfiguration().getServer() + "/ws/rest/v1/form" + REPRESENTATION + paramBuilder.toString();
    }
}
