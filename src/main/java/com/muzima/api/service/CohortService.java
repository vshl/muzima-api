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
import com.muzima.api.annotation.Authorization;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortData;
import com.muzima.api.model.CohortMember;
import com.muzima.api.service.impl.CohortServiceImpl;

import java.io.IOException;
import java.util.Date;
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
     * @should download all cohorts with partially matched name.
     * @should download all cohorts when name is empty.
     */
    List<Cohort> downloadCohortsByName(final String name) throws IOException;

    /**
     * Download all cohorts with name similar to the partial name and sync date passed in the parameter.
     *
     * @param name the partial name of the cohort to be downloaded. When empty, will return all cohorts available.
     * @param syncDate last sync date of the cohort.
     * @throws IOException when search api unable to process the resource.
     * @should download all cohorts with partially matched name.
     * @should download all cohorts when name is empty.
     */
    List<Cohort> downloadCohortsByNameAndSyncDate(final String name, final Date syncDate) throws IOException;

    /**
     * Download a single cohort definition record from the cohort definition rest resource and convert them into
     * <code>CohortDefinition</code> object.
     *
     * @param uuid the uuid of the cohort definition.
     * @throws IOException when search api unable to process the resource.
     * @should download cohort definition with matching uuid.
     */
    Cohort downloadDynamicCohortByUuid(final String uuid) throws IOException;

    /**
     * Download all cohort definitions with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the cohort definition to be downloaded. When empty, will return all cohort
     *             definitions available.
     * @throws IOException when search api unable to process the resource.
     * @should download all cohort definitions with partially matched name.
     * @should download all cohort definitions when name is empty.
     */
    List<Cohort> downloadDynamicCohortsByName(final String name) throws IOException;

    /**
     * Save the current cohort object to the local lucene repository.
     *
     * @param cohort the cohort to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save cohort object into local lucene data repository.
     */
    void saveCohort(final Cohort cohort) throws IOException;

    /**
     * Save the current cohort objects to the local lucene repository.
     *
     * @param cohorts the cohorts to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save list of all cohort objects into local lucene data repository.
     */
    void saveCohorts(final List<Cohort> cohorts) throws IOException;

    /**
     * Update the current cohort object to the local lucene repository.
     *
     * @param cohort the cohort to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace old cohort with the matching uuid with the new cohort object.
     */
    void updateCohort(final Cohort cohort) throws IOException;

    /**
     * Update the current cohorts object to the local lucene repository.
     *
     * @param cohorts the cohorts to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace all old cohorts with  matching uuid with the new cohort object.
     */
    void updateCohorts(final List<Cohort> cohorts) throws IOException;

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
     * Count the total number of cohorts in the local lucene repository.
     *
     * @param name the partial name of the cohorts.
     * @return number of cohorts with matching the partial name.
     * @throws IOException when search api unable to process the resource.
     * @should return number of cohort in the local repository with matching name.
     */
    Integer countCohorts(final String name) throws IOException;

    /**
     * Get list of cohorts based on the name of the cohort. If empty string is passed, it will search for all cohorts.
     *
     * @param name the partial name of the cohort.
     * @return list of all cohorts with matching uuid or empty list when no cohort match the name.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all cohorts with matching name.
     * @should return empty list when no cohort match the name.
     */
    List<Cohort> getCohortsByName(final String name) throws IOException;

    /**
     * Get list of cohorts based on the name of the cohort. If empty string is passed, it will search for all cohorts.
     *
     * @param name     the partial name of the cohort.
     * @param page     the current page.
     * @param pageSize the maximum number of objects in the page.
     * @return list of all cohorts with matching uuid or empty list when no cohort match the name.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all cohorts with matching name.
     * @should return empty list when no cohort match the name.
     */
    List<Cohort> getCohortsByName(final String name, final Integer page, final Integer pageSize) throws IOException;

    /**
     * Count the total number of cohorts in the local lucene repository.
     *
     * @return number of cohorts.
     * @throws IOException when search api unable to process the resource.
     * @should return number of cohort in the local repository.
     */
    Integer countAllCohorts() throws IOException;

    /**
     * @return all registered cohort or empty list when no cohort is registered.
     * @throws IOException when search api unable to process the resource.
     * @should return all registered cohorts.
     * @should return empty list when no cohort is registered.
     */
    List<Cohort> getAllCohorts() throws IOException;

    /**
     * Delete a single cohort record from the repository.
     *
     * @param cohort the cohort to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete the cohort from lucene repository.
     */
    void deleteCohort(final Cohort cohort) throws IOException;

    /**
     * Delete multiple cohorts from the repository.
     *
     * @param cohorts the cohorts to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete all cohorts from lucene repository.
     */
    void deleteCohorts(final List<Cohort> cohorts) throws IOException;

    /**
     * Download data for the cohort identified by the uuid of the cohort. The dynamic
     * field will be used to determine whether the API should download the data from
     * the reporting resource (dynamic cohort) or the static cohort resource.
     * <p/>
     * This method call will return a cohort data object which will hold the cohort
     * information, the member information, and the patients information. The member
     * will create a mapping between the patient and the cohort.
     *
     * @param uuid    the uuid of the cohort or the cohort definition.
     * @param dynamic flag whether to use reporting module or static cohort resource.
     * @return the cohort data based on the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should download cohort data identified by the uuid and dynamic field.
     */
    CohortData downloadCohortData(final String uuid, final boolean dynamic) throws IOException;

    CohortData downloadCohortDataAndSyncDate(String uuid, boolean dynamic, Date syncDate) throws IOException;

    /**
     * Download data for the cohort. The API will check the dynamic field of the
     * cohort to determine whether the API should download the data from the reporting
     * resource (dynamic cohort) or the static cohort resource.
     * <p/>
     * This method call will return a cohort data object which will hold the cohort
     * information, the member information, and the patients information. The member
     * will create a mapping between the patient and the cohort.
     *
     * @param cohort the cohort object to be downloaded
     * @return the cohort data based on the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should download cohort data for the cohort object
     */
    CohortData downloadCohortData(final Cohort cohort) throws IOException;

    CohortData downloadCohortDataAndSyncDate(Cohort cohort, Date syncDate) throws IOException;

    /**
     * Save the cohort member object to the local lucene directory.
     *
     * @param cohortMember the member object to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save the cohort member object to local lucene repository.
     */
    void saveCohortMember(final CohortMember cohortMember) throws IOException;

    /**
     * Save the cohort member objects to the local lucene directory.
     *
     * @param cohortMembers the member objects to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save list of cohort members to local lucene repository.
     */
    void saveCohortMembers(final List<CohortMember> cohortMembers) throws IOException;

    /**
     * Update the cohort member object to the local lucene directory.
     *
     * @param cohortMember the member object to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace the cohort member in local lucene repository.
     */
    void updateCohortMember(final CohortMember cohortMember) throws IOException;

    /**
     * Update the cohort member objects to the local lucene directory.
     *
     * @param cohortMembers the member objects to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace the cohort members in local lucene repository.
     */
    void updateCohortMembers(final List<CohortMember> cohortMembers) throws IOException;

    /**
     * Count the total number of cohort members of a cohort in the lucene repository.
     *
     * @param cohortUuid the cohort uuid.
     * @return total number of cohort members for the cohort.
     * @throws IOException when search api unable to process the resource.
     */
    Integer countCohortMembers(final String cohortUuid) throws IOException;

    Integer countCohortMembers(final Cohort cohort) throws IOException;

    /**
     * Get all members under the current cohort identified by the cohort's uuid which already saved in the local
     * repository.
     *
     * @param cohortUuid the cohort's uuid.
     * @return list of all patients' uuid under current cohort uuid or empty list when no patient are in the cohort.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all members for the cohort.
     * @should return empty list when no member are in the cohort.
     */
    List<CohortMember> getCohortMembers(final String cohortUuid) throws IOException;

    List<CohortMember> getCohortMembers(final Cohort cohort) throws IOException;

    /**
     * Get all members under the current cohort identified by the cohort's uuid which already saved in the local
     * repository.
     *
     * @param cohortUuid the cohort's uuid.
     * @param page       the current page.
     * @param pageSize   the maximum number of objects in the page.
     * @return list of all patients' uuid under current cohort uuid or empty list when no patient are in the cohort.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all members for the cohort.
     * @should return empty list when no member are in the cohort.
     */
    List<CohortMember> getCohortMembers(final String cohortUuid, final Integer page,
                                        final Integer pageSize) throws IOException;

    List<CohortMember> getCohortMembers(final Cohort cohort, final Integer page,
                                        final Integer pageSize) throws IOException;

    /**
     * Delete all members for the current cohort identified by the cohort's uuid.
     *
     * @param cohortUuid the cohort's uuid.
     * @throws IOException when search api unable to process the resource.
     * @should delete all members for the cohort from the local repository.
     */
    void deleteCohortMembers(final String cohortUuid) throws IOException;

    void deleteCohortMembers(final Cohort cohort) throws IOException;
}
