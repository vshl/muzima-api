package com.muzima.api.model.resolver;

import com.muzima.api.model.algorithm.SetupConfigurationTemplateAlgorithm;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

public class SearchSetupConfigurationTemplateResolver extends BaseOpenmrsResolver {

    private static final String REPRESENTATION
            = "?v=custom:"+ SetupConfigurationTemplateAlgorithm.SETUP_CONFIGURATION_TEMPLATE_STANDARD_REPRESENTATION;

    public String resolve(final Map<String, String> resourceParams) throws IOException {
        StringBuilder paramBuilder = new StringBuilder();
        for (String key : resourceParams.keySet()) {
            paramBuilder.append("&").append(key).append("=").append(URLEncoder.encode(resourceParams.get(key), "UTF-8"));
        }
        return getConfiguration().getServer() + "/ws/rest/v1/muzima/config" + REPRESENTATION + paramBuilder.toString();
    }
}
