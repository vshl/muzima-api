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
import com.muzima.api.dao.CohortDao;
import com.muzima.api.dao.CohortDataDao;
import com.muzima.api.dao.CohortDefinitionDao;
import com.muzima.api.dao.MemberDao;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortData;
import com.muzima.api.model.CohortDefinition;
import com.muzima.api.model.CohortMember;
import com.muzima.api.service.CohortService;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

public class CohortServiceImpl implements CohortService {

    @Inject
    private CohortDao cohortDao;

    @Inject
    private MemberDao memberDao;

    @Inject
    private CohortDataDao cohortDataDao;

    @Inject
    private CohortDefinitionDao cohortDefinitionDao;

    protected CohortServiceImpl() {
    }

    /**
     * Download a single cohort record from the cohort rest resource and convert them into Cohort object.
     *
     * @param uuid the uuid of the cohort.
     * @throws IOException when search api unable to process the resource.
     * @should download cohort with matching uuid.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public Cohort downloadCohortByUuid(final String uuid) throws IOException {
        List<Cohort> cohorts = cohortDao.download(uuid, Constants.UUID_COHORT_RESOURCE);
        if (cohorts.size() > 1) {
            throw new IOException("Unable to uniquely identify a cohort record.");
        } else if (cohorts.size() == 0) {
            return null;
        }
        return cohorts.get(0);
    }

    /**
     * Download all cohorts with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the cohort to be downloaded. When empty, will return all cohorts available.
     * @throws IOException when search api unable to process the resource.
     * @should download all cohorts with partially matched name.
     * @should download all cohorts when name is empty.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<Cohort> downloadCohortsByName(final String name) throws IOException {
        return cohortDao.download(name, Constants.SEARCH_COHORT_RESOURCE);
    }

    /**
     * Save the current cohort object to the local lucene repository.
     *
     * @param cohort the cohort to be saved.
     * @return the saved cohort.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public Cohort saveCohort(final Cohort cohort) throws IOException {
        return cohortDao.save(cohort, Constants.UUID_COHORT_RESOURCE);
    }

    /**
     * Update the current cohort object to the local lucene repository.
     *
     * @param cohort the cohort to be updated.
     * @return the updated cohort.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public Cohort updateCohort(final Cohort cohort) throws IOException {
        return cohortDao.update(cohort, Constants.UUID_COHORT_RESOURCE);
    }

    /**
     * Get a single cohort record from the repository using the uuid.
     *
     * @param uuid the cohort uuid.
     * @return cohort with matching uuid or null when no cohort match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return cohort with matching uuid.
     * @should return null when no cohort match the uuid.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public Cohort getCohortByUuid(final String uuid) throws IOException {
        return cohortDao.getByUuid(uuid);
    }

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
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<Cohort> getCohortsByName(final String name) throws IOException, ParseException {
        return cohortDao.getByName(name);
    }

    /**
     * @return all registered cohort or empty list when no cohort is registered.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return all registered cohorts.
     * @should return empty list when no cohort is registered.
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<Cohort> getAllCohorts() throws IOException, ParseException {
        return cohortDao.getAll();
    }

    /**
     * Delete a single cohort record from the repository.
     *
     * @param cohort the cohort to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete the cohort from lucene repository.
     */
    @Override
    public void deleteCohort(final Cohort cohort) throws IOException {
        cohortDao.delete(cohort, Constants.UUID_COHORT_RESOURCE);
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
        List<CohortDefinition> cohorts = cohortDefinitionDao.download(
                cohortDefinitionUuid, Constants.UUID_COHORT_DEFINITION_RESOURCE);
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
        return cohortDefinitionDao.download(name, Constants.SEARCH_COHORT_DEFINITION_RESOURCE);
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
        return cohortDefinitionDao.save(cohortDefinition, Constants.UUID_COHORT_DEFINITION_RESOURCE);
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
        return cohortDefinitionDao.update(cohortDefinition, Constants.UUID_COHORT_DEFINITION_RESOURCE);
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
        cohortDefinitionDao.delete(cohort, Constants.UUID_COHORT_DEFINITION_RESOURCE);
    }

    /**
     * Download data for the cohort or cohort definition identified by the uuid. The flag for dynamic will determine
     * whether the API should download the data from the reporting resource or the static cohort resource.
     * <p/>
     * This method call will return a cohort data object which will hold the cohort information, the member information,
     * and the patients information. The member will create a mapping between the patient and the cohort.
     * <p/>
     * Client code should save elements of the cohort data as saving the cohort data object itself is not allowed.
     *
     * @param uuid    the uuid of the cohort or the cohort definition.
     * @param dynamic flag whether to use reporting module or static cohort resource.
     * @return the cohort data based on the uuid.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public CohortData downloadCohortData(final String uuid, final boolean dynamic) throws IOException {
        String resourceName = Constants.STATIC_COHORT_DATA_RESOURCE;
        if (dynamic) {
            resourceName = Constants.DYNAMIC_COHORT_DATA_RESOURCE;
        }
        List<CohortData> cohortDataList = cohortDataDao.download(uuid, resourceName);
        if (cohortDataList.size() > 1) {
            throw new IOException("Unable to uniquely identify a cohort record.");
        } else if (cohortDataList.size() == 0) {
            return null;
        }
        return cohortDataList.get(0);
    }

    /**
     * Save the member object to the local lucene directory.
     *
     * @param cohortMember the member object to be saved.
     * @return the saved member object.
     * @throws IOException when search api unable to process the resource.
     */
    public CohortMember saveCohortMember(final CohortMember cohortMember) throws IOException {
        return memberDao.save(cohortMember, Constants.LOCAL_COHORT_MEMBER_RESOURCE);
    }

    /**
     * Update the member object to the local lucene directory.
     *
     * @param cohortMember the member object to be updated.
     * @return the updated member object.
     * @throws IOException when search api unable to process the resource.
     */
    public CohortMember updateCohortMember(final CohortMember cohortMember) throws IOException {
        return memberDao.update(cohortMember, Constants.LOCAL_COHORT_MEMBER_RESOURCE);
    }

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
    @Override
    public List<CohortMember> getCohortMembers(final String cohortUuid) throws IOException {
        return memberDao.getByCohortUuid(cohortUuid);
    }

    /**
     * Delete all members for the current cohort identified by the cohort's uuid.
     *
     * @param cohortUuid the cohort's uuid.
     * @throws IOException when search api unable to process the resource.
     * @should delete all patients for the cohort from the local repository.
     */
    @Override
    public void deleteCohortMembers(final String cohortUuid) throws IOException {
        for (CohortMember cohortMember : getCohortMembers(cohortUuid)) {
            memberDao.delete(cohortMember, Constants.LOCAL_COHORT_MEMBER_RESOURCE);
        }
    }
}
