package com.muzima.api.model.resolver;

import com.muzima.api.model.algorithm.SetupConfigurationAlgorithm;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.Map;

public class UuidSetupConfigurationResolver extends BaseOpenmrsResolver {

    private static final String REPRESENTATION
            = "?v=custom:"+ SetupConfigurationAlgorithm.SETUP_CONFIGURATION_STANDARD_REPRESENTATION;

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
        return getConfiguration().getServer() + "/ws/rest/v1/muzima/config/" + uuid + REPRESENTATION;
    }
}
