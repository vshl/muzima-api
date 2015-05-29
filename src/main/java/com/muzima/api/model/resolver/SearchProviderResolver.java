package com.muzima.api.model.resolver;



import com.muzima.api.model.algorithm.ProviderAlgorithm;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by vikas on 16/03/15.
 */
public class SearchProviderResolver extends BaseOpenmrsResolver {
    private static final String REPRESENTATION =
            "?v=custom:" + ProviderAlgorithm.PROVIDER_STANDARD_REPRESENTATION;

    @Override
    public String resolve(Map<String, String> resourceParams) throws IOException {
        StringBuilder paramBuilder = new StringBuilder();
        for (String key : resourceParams.keySet()) {
            paramBuilder.append("&").append(key).append("=").append(URLEncoder.encode(resourceParams.get(key), "UTF-8"));
        }
        return getConfiguration().getServer() + "/ws/rest/v1/provider" + REPRESENTATION + paramBuilder.toString();
    }
}
