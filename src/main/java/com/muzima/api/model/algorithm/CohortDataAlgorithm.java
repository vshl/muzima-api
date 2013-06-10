package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.CohortData;
import com.muzima.search.api.model.object.Searchable;

import java.io.IOException;

/**
 * TODO: Write brief description about the class here.
 */
public class CohortDataAlgorithm extends BaseOpenmrsAlgorithm {

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        CohortData cohortData = new CohortData();

        Filter staticCohortDataFilter = Filter.filter(Criteria.where("results").exists(true));
        Object cohortDataObject = JsonPath.read(serialized, "$", staticCohortDataFilter);

        return cohortData;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        throw new IOException("Serializing the cohort data object is not supported right now!");
    }
}
