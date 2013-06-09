/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.annotation.Authorization;
import com.muzima.api.dao.CohortDefinitionDao;
import com.muzima.api.dao.EvaluatedCohortDao;
import com.muzima.api.model.CohortDefinition;
import com.muzima.api.model.EvaluatedCohort;
import com.muzima.api.service.CohortDefinitionService;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

public class CohortDefinitionServiceImpl implements CohortDefinitionService {

    @Inject
    private CohortDefinitionDao cohortDefinitionDao;

    @Inject
    private EvaluatedCohortDao evaluatedCohortDao;

    protected CohortDefinitionServiceImpl() {
    }

    /**
     * Download a single cohort definition record from the cohort definition rest resource and convert them into
     * <code>CohortDefinition</code> object.
     *
     * @param cohortDefinitionUuid the uuid of the cohort definition.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download cohort definition with matching uuid.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public CohortDefinition downloadCohortDefinitionByUuid(final String cohortDefinitionUuid) throws IOException {
        List<CohortDefinition> cohorts = cohortDefinitionDao.download(cohortDefinitionUuid, Constants.UUID_COHORT_RESOURCE);
        if (cohorts.size() > 1) {
            throw new IOException("Unable to uniquely identify a cohort record.");
        } else if (cohorts.size() == 0) {
            return null;
        }
        return cohorts.get(0);
    }

    /**
     * Download all cohort definitions with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the cohort definition to be downloaded. When empty, will return all cohort
     *             definitions available.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download all cohort definitions with partially matched name.
     * @should download all cohort definitions when name is empty.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<CohortDefinition> downloadCohortDefinitionsByName(final String name) throws IOException {
        return cohortDefinitionDao.download(name, Constants.SEARCH_COHORT_RESOURCE);
    }

    /**
     * Save the current cohort definition object to the local lucene repository.
     *
     * @param cohortDefinition the cohort definition to be saved.
     * @return the saved cohort definition.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public CohortDefinition saveCohortDefinition(final CohortDefinition cohortDefinition) throws IOException {
        return cohortDefinitionDao.save(cohortDefinition, Constants.UUID_COHORT_RESOURCE);
    }

    /**
     * Update the current cohort definition object to the local lucene repository.
     *
     * @param cohortDefinition the cohort definition to be updated.
     * @return the updated cohort definition.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public CohortDefinition updateCohortDefinition(final CohortDefinition cohortDefinition) throws IOException {
        return cohortDefinitionDao.update(cohortDefinition, Constants.UUID_COHORT_RESOURCE);
    }

    /**
     * Get a single cohort definition record from the repository using the uuid.
     *
     * @param cohortDefinitionUuid the cohort definition uuid.
     * @return cohort definition with matching uuid or null when no cohort definition match the uuid.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should return cohort definition with matching uuid.
     * @should return null when no cohort definition match the uuid.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public CohortDefinition getCohortDefinitionByUuid(final String cohortDefinitionUuid) throws IOException {
        return cohortDefinitionDao.getByUuid(cohortDefinitionUuid);
    }

    /**
     * Get list of cohort definitions based on the name of the cohort definition. If empty string is passed, it will
     * search for all cohort definitions.
     *
     * @param name the partial name of the cohort definition.
     * @return list of all cohort definitions with matching uuid or empty list when no cohort definition match the name.
     * @throws org.apache.lucene.queryParser.ParseException
     *                             when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should return list of all cohort definitions with matching name.
     * @should return empty list when no cohort definition match the name.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<CohortDefinition> getCohortDefinitionsByName(final String name) throws IOException, ParseException {
        return cohortDefinitionDao.getByName(name);
    }

    /**
     * Get all cohort definitions saved in the local lucene repository.
     *
     * @return all registered cohort definition or empty list when no cohort definition is registered.
     * @throws org.apache.lucene.queryParser.ParseException
     *                             when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should return all registered cohort definitions.
     * @should return empty list when no cohort definition is registered.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<CohortDefinition> getAllCohortDefinitions() throws IOException, ParseException {
        return cohortDefinitionDao.getAll();
    }

    /**
     * Delete a single cohort definition record from the repository.
     *
     * @param cohort the cohort definition to be deleted.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should delete the cohort definition from lucene repository.
     */
    @Override
    public void deleteCohortDefinition(final CohortDefinition cohort) throws IOException {
        cohortDefinitionDao.delete(cohort, Constants.UUID_COHORT_RESOURCE);
    }

    /**
     * Evaluate and then download all patients' uuid under the current cohort definition identified by the cohort
     * definition's uuid and then convert them into the <code>EvaluatedCohort</code> object.
     *
     * @param cohortDefinitionUuid the cohort definition's uuid.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should evaluate and download all patients from the current cohort definition.
     */
    @Override
    public EvaluatedCohort evaluateCohortDefinition(final String cohortDefinitionUuid) throws IOException {
        List<EvaluatedCohort> evaluatedCohorts = evaluatedCohortDao.download(cohortDefinitionUuid, Constants.MEMBER_COHORT_RESOURCE);
        if (evaluatedCohorts.size() > 1) {
            throw new IOException("Unable to uniquely identify an evaluated cohort record.");
        } else if (evaluatedCohorts.size() == 0) {
            return null;
        }
        return evaluatedCohorts.get(0);
    }

    /**
     * Save the evaluated cohort object to the local lucene directory.
     *
     * @param evaluatedCohort the evaluated cohort object to be saved.
     * @return the saved evaluated cohort object.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    public EvaluatedCohort saveEvaluatedCohort(final EvaluatedCohort evaluatedCohort) throws IOException {
        return evaluatedCohortDao.save(evaluatedCohort, Constants.MEMBER_COHORT_RESOURCE);
    }

    /**
     * Update the evaluated cohort object to the local lucene directory.
     *
     * @param evaluatedCohort the evaluated cohort object to be updated.
     * @return the updated evaluated cohort object.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    public EvaluatedCohort updateEvaluatedCohort(final EvaluatedCohort evaluatedCohort) throws IOException {
        return evaluatedCohortDao.update(evaluatedCohort, Constants.MEMBER_COHORT_RESOURCE);
    }

    /**
     * Get <code>EvaluatedCohort</code> object from the evaluation of cohort definition with uuid value equals to
     * <code>cohortDefinitionUuid</code> which was downloaded and saved in the local lucene index's repository.
     *
     * @param cohortDefinitionUuid the cohort definition's uuid.
     * @return <code>EvaluatedCohort</code> data for the cohort definition.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should return list of all patients for the cohort definition.
     * @should return empty list when no patient are in the cohort definition.
     */
    @Override
    public EvaluatedCohort getEvaluatedCohort(final String cohortDefinitionUuid) throws IOException {
        return evaluatedCohortDao.getByCohortDefinitionUuid(cohortDefinitionUuid);
    }

    /**
     * Delete evaluated cohort data that's already saved in the lucene repository.
     *
     * @param evaluatedCohort the evaluated cohort to be deleted
     * @throws java.io.IOException when search api unable to process the resource.
     * @should delete evaluated cohort data from the lucene repository.
     */
    @Override
    public void deleteEvaluatedCohort(final EvaluatedCohort evaluatedCohort) throws IOException {
        evaluatedCohortDao.delete(evaluatedCohort, Constants.MEMBER_COHORT_RESOURCE);
    }
}
