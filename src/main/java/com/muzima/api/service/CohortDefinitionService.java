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
package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.CohortDefinition;
import com.muzima.api.model.EvaluatedCohort;
import com.muzima.api.service.impl.CohortDefinitionServiceImpl;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Service handling all operation to the @{Cohort} actor/model
 */
@ImplementedBy(CohortDefinitionServiceImpl.class)
public interface CohortDefinitionService extends MuzimaInterface {

    /**
     * Download a single cohort definition record from the cohort definition rest resource and convert them into
     * <code>CohortDefinition</code> object.
     *
     * @param cohortDefinitionUuid the uuid of the cohort definition.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download cohort definition with matching uuid.
     */
    CohortDefinition downloadCohortDefinitionByUuid(final String cohortDefinitionUuid) throws IOException;

    /**
     * Download all cohort definitions with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the cohort definition to be downloaded. When empty, will return all cohort
     *             definitions available.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download all cohort definitions with partially matched name.
     * @should download all cohort definitions when name is empty.
     */
    List<CohortDefinition> downloadCohortDefinitionsByName(final String name) throws IOException;

    /**
     * Save the current cohort definition object to the local lucene repository.
     *
     * @param cohortDefinition the cohort definition to be saved.
     * @return the saved cohort definition.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    CohortDefinition saveCohortDefinition(final CohortDefinition cohortDefinition) throws IOException;

    /**
     * Update the current cohort definition object to the local lucene repository.
     *
     * @param cohortDefinition the cohort definition to be updated.
     * @return the updated cohort definition.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    CohortDefinition updateCohortDefinition(final CohortDefinition cohortDefinition) throws IOException;

    /**
     * Get a single cohort definition record from the repository using the uuid.
     *
     * @param cohortDefinitionUuid the cohort definition uuid.
     * @return cohort definition with matching uuid or null when no cohort definition match the uuid.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should return cohort definition with matching uuid.
     * @should return null when no cohort definition match the uuid.
     */
    CohortDefinition getCohortDefinitionByUuid(final String cohortDefinitionUuid) throws IOException;

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
    List<CohortDefinition> getCohortDefinitionsByName(final String name) throws IOException, ParseException;

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
    List<CohortDefinition> getAllCohortDefinitions() throws IOException, ParseException;

    /**
     * Delete a single cohort definition record from the repository.
     *
     * @param cohort the cohort definition to be deleted.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should delete the cohort definition from lucene repository.
     */
    void deleteCohortDefinition(final CohortDefinition cohort) throws IOException;

    /**
     * Evaluate and then download all patients' uuid under the current cohort definition identified by the cohort
     * definition's uuid and then convert them into the <code>EvaluatedCohort</code> object.
     *
     * @param cohortDefinitionUuid the cohort definition's uuid.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should evaluate and download all patients from the current cohort definition.
     */
    EvaluatedCohort evaluateCohortDefinition(final String cohortDefinitionUuid) throws IOException;

    /**
     * Save the evaluated cohort object to the local lucene directory.
     *
     * @param evaluatedCohort the evaluated cohort object to be saved.
     * @return the saved evaluated cohort object.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    EvaluatedCohort saveEvaluatedCohort(final EvaluatedCohort evaluatedCohort) throws IOException;

    /**
     * Update the evaluated cohort object to the local lucene directory.
     *
     * @param evaluatedCohort the evaluated cohort object to be updated.
     * @return the updated evaluated cohort object.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    EvaluatedCohort updateEvaluatedCohort(final EvaluatedCohort evaluatedCohort) throws IOException;

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
    EvaluatedCohort getEvaluatedCohort(final String cohortDefinitionUuid) throws IOException;

    /**
     * Delete evaluated cohort data that's already saved in the lucene repository.
     *
     * @param evaluatedCohort the evaluated cohort to be deleted
     * @throws java.io.IOException when search api unable to process the resource.
     * @should delete evaluated cohort data from the lucene repository.
     */
    void deleteEvaluatedCohort(final EvaluatedCohort evaluatedCohort) throws IOException;
}
