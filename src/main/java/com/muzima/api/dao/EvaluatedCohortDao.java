package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.EvaluatedCohortDaoImpl;
import com.muzima.api.model.EvaluatedCohort;

import java.io.IOException;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(EvaluatedCohortDaoImpl.class)
public interface EvaluatedCohortDao extends OpenmrsDao<EvaluatedCohort> {
    /**
     * Get evaluated data objects by their cohort definition's uuid.
     *
     * @param cohortDefinitionUuid the cohort definition's uuid.
     * @return the evaluated cohort based on the cohort definition uuid.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    EvaluatedCohort getByCohortDefinitionUuid(final String cohortDefinitionUuid) throws IOException;
}
