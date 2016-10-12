/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.resolver;

import com.muzima.api.model.algorithm.ConceptAlgorithm;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.Map;

public class UuidConceptResolver extends BaseOpenmrsResolver {

    private static final String REPRESENTATION =
            "?v=custom:" + ConceptAlgorithm.CONCEPT_STANDARD_REPRESENTATION;

    /**
     * Return the full REST resource based on the parameters passed to the method.
     *
     * @param resourceParams the parameters of the resource to resolved.
     * @return full uri to the REST resource.
     */
    public String resolve(final Map<String, String> resourceParams) throws IOException {
        String uuid = resourceParams.get("uuid");
        if (StringUtil.isEmpty(uuid)) {
            throw new IOException("Resolver unable to find required parameter uuid!");
        }
        return getConfiguration().getServer() + "/ws/rest/v1/muzima/concept/" + uuid + REPRESENTATION;
    }
}
