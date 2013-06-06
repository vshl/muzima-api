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
import com.muzima.api.model.Cohort;
import com.muzima.api.model.Member;
import com.muzima.api.service.impl.CohortServiceImpl;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Service handling all operation to the @{Cohort} actor/model
 */
@ImplementedBy(CohortServiceImpl.class)
public interface CohortService extends MuzimaInterface {

    /**
     * Download a single cohort record from the cohort rest resource and convert them into Cohort object.
     *
     * @param uuid the uuid of the cohort.
     * @throws IOException when search api unable to process the resource.
     * @should download cohort with matching uuid.
     */
    Cohort downloadCohortByUuid(final String uuid) throws IOException;

    /**
     * Download all cohorts with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the cohort to be downloaded. When empty, will return all cohorts available.
     * @throws IOException when search api unable to process the resource.
     * @should download all cohort with partially matched name.
     * @should download all cohort when name is empty.
     */
    List<Cohort> downloadCohortsByName(final String name) throws IOException;

    /**
     * Save the current cohort object to the local lucene repository.
     *
     * @param cohort the cohort to be saved.
     * @return the saved cohort.
     * @throws IOException when search api unable to process the resource.
     */
    Cohort saveCohort(final Cohort cohort) throws IOException;

    /**
     * Update the current cohort object to the local lucene repository.
     *
     * @param cohort the cohort to be updated.
     * @return the updated cohort.
     * @throws IOException when search api unable to process the resource.
     */
    Cohort updateCohort(final Cohort cohort) throws IOException;

    /**
     * Get a single cohort record from the repository using the uuid.
     *
     * @param uuid the cohort uuid.
     * @return cohort with matching uuid or null when no cohort match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return cohort with matching uuid.
     * @should return null when no cohort match the uuid.
     */
    Cohort getCohortByUuid(final String uuid) throws IOException;

    /**
     * Get list of cohorts based on the name of the cohort. If empty string is passed, it will search for all cohorts.
     *
     * @param name the partial name of the cohort.
     * @return list of all cohorts with matching uuid or empty list when no cohort match the name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all cohorts with matching name.
     * @should return empty list when no cohort match the name.
     */
    List<Cohort> getCohortsByName(final String name) throws IOException, ParseException;

    /**
     * @return all registered cohort or empty list when no cohort is registered.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return all registered cohorts.
     * @should return empty list when no cohort is registered.
     */
    List<Cohort> getAllCohorts() throws IOException, ParseException;

    /**
     * Delete a single cohort record from the repository.
     *
     * @param cohort the cohort to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete the cohort from lucene repository.
     */
    void deleteCohort(final Cohort cohort) throws IOException;

    /**
     * Download all patients' uuid under the current cohort identified by the cohort uuid and save them in to the local
     * repository.
     *
     * @param cohortUuid the cohort's uuid.
     * @throws IOException when search api unable to process the resource.
     * @should download all patients from the current cohort identified by the cohort's uuid.
     */
    List<Member> downloadMembers(final String cohortUuid) throws IOException;

    /**
     * Save the member object to the local lucene directory.
     *
     * @param member the member object to be saved.
     * @return the saved member object.
     * @throws IOException when search api unable to process the resource.
     */
    Member saveMember(final Member member) throws IOException;

    /**
     * Update the member object to the local lucene directory.
     *
     * @param member the member object to be updated.
     * @return the updated member object.
     * @throws IOException when search api unable to process the resource.
     */
    Member updateMember(final Member member) throws IOException;

    /**
     * Get all patients' uuid under the current cohort identified by the cohort's uuid which already saved in the local
     * repository.
     *
     * @param cohortUuid the cohort's uuid.
     * @return list of all patients' uuid under current cohort uuid or empty list when no patient are in the cohort.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all patients for the cohort.
     * @should return empty list when no patient are in the cohort.
     */
    List<Member> getMembers(final String cohortUuid) throws IOException;

    /**
     * Delete all patients for the current cohort identified by the cohort's uuid.
     *
     * @param cohortUuid the cohort's uuid.
     * @throws IOException when search api unable to process the resource.
     * @should delete all patients for the cohort from the local repository.
     */
    void deleteMembers(final String cohortUuid) throws IOException;
}
