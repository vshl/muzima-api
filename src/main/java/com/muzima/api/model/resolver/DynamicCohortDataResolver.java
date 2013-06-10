package com.muzima.api.model.resolver;

import java.io.IOException;

/**
 * TODO: Write brief description about the class here.
 */
public class DynamicCohortDataResolver extends BaseOpenmrsResolver {

    private static final String REPRESENTATION = "?v=custom:(definition.uuid,definition.name," +
            "members:(uuid,gender,birthdate,personName.givenName,personName.middleName,personName.familyName," +
            "patientIdentifier.identifier,patientIdentifier.identifierType.name))";

    /**
     * Return the full REST resource based on the search string passed to the method.
     *
     * @param searchString the search string
     * @return full URI to the REST resource
     */
    @Override
    public String resolve(final String searchString) throws IOException {
        // the search string here is the cohort definition uuid
        return getConfiguration().getServer() + "/ws/rest/v1/reportingrest/cohort/" + searchString + REPRESENTATION;
    }


}
