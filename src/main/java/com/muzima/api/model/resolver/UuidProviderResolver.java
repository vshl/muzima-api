package com.muzima.api.model.resolver;

import com.muzima.api.model.algorithm.ProviderAlgorithm;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by vikas on 16/03/15.
 */
public class UuidProviderResolver extends BaseOpenmrsResolver {
    private static final String REPRESENTATION =
            "?v=custom:"+ ProviderAlgorithm.PROVIDER_STANDARD_REPRESENTATION;

    @Override
    public String resolve(Map<String, String> requestParam) throws IOException {
        String uuid = requestParam.get("uuid");
        if(StringUtil.isEmpty(uuid)){
            throw new IOException("Resolver unable to find required parameter uuid!");
        }
        return getConfiguration().getServer()+"/ws/rest/v1/provider/" + uuid + REPRESENTATION;
    }
}
