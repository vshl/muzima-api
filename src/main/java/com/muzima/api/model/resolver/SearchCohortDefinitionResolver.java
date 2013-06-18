package com.muzima.api.model.resolver;

import com.muzima.search.api.util.StringUtil;

import java.io.IOException;

/**
 * TODO: Write brief description about the class here.
 */
public class SearchCohortDefinitionResolver extends BaseOpenmrsResolver {

    private static final String REPRESENTATION = "?v=custom:(uuid,name)";

    /**
     * Return the full REST resource based on the search string passed to the method.
     *
     * @param searchString the search string
     * @return full URI to the REST resource
     */
    @Override
    public String resolve(final String searchString) throws IOException {
        String param = StringUtil.EMPTY;
        if (!StringUtil.isEmpty(searchString))
            param = "&q=" + searchString;
        return getConfiguration().getServer() + "/ws/rest/v1/reportingrest/cohortDefinition" + REPRESENTATION + param;
    }
}
