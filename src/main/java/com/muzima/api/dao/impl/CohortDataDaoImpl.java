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
package com.muzima.api.dao.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.CohortDataDao;
import com.muzima.api.model.CohortData;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Patient;
import com.muzima.search.api.context.ServiceContext;
import com.muzima.search.api.model.object.Searchable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CohortDataDaoImpl extends OpenmrsDaoImpl<CohortData> implements CohortDataDao {

    @Inject
    private ServiceContext serviceContext;

    private static final String TAG = CohortDataDaoImpl.class.getSimpleName();

    protected CohortDataDaoImpl() {
        super(CohortData.class);
    }

    /**
     * Download the searchable object matching the uuid. This process involve executing the REST call, pulling the
     * resource and then saving it to local lucene repository.
     *
     * @param resourceParams the parameters to be passed to search object to filter the searchable object.
     * @param resource       resource descriptor used to convert the resource to the correct object.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public List<CohortData> download(final Map<String, String> resourceParams, final String resource) throws IOException {
        CohortData consolidatedCohortData = new CohortData();
        List<Patient> patients = consolidatedCohortData.getPatients();
        List<CohortMember> members = consolidatedCohortData.getCohortMembers();
        List<Searchable> searchableList = service.loadObjects(resourceParams, serviceContext.getResource(resource));
        for (Searchable searchable : searchableList) {
            CohortData cohortData = (CohortData) searchable;
            patients.addAll(cohortData.getPatients());
            members.addAll(cohortData.getCohortMembers());
        }
        return Arrays.asList(consolidatedCohortData);
    }

    /**
     * Save the object to the local repository.
     *
     * @param object   the object to be saved.
     * @param resource the resource descriptor used for saving.
     * @return saved object.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void save(final CohortData object, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Save list of objects to the local repository. Use this save method when you want to save multiple objects
     * at the same time.
     *
     * @param objects  the objects to be saved.
     * @param resource the resource descriptor used for saving.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void save(final List<CohortData> objects, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Update the saved object in the local repository.
     *
     * @param object   the object to be updated.
     * @param resource the resource descriptor used for saving.
     * @return updated object.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void update(final CohortData object, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Update list of objects in the local repository. Use this save method when you want to update multiple objects
     * at the same time.
     *
     * @param objects  the objects to be updated.
     * @param resource the resource descriptor used for updating.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void update(final List<CohortData> objects, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Get the OpenMRS searchable object using the uuid.
     *
     * @param uuid the uuid of the searchable object.
     * @return the searchable object.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public CohortData getByUuid(final String uuid) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Get cohort by the name of the cohort. Passing empty string will returns all registered cohorts.
     *
     * @param name the partial name of the cohort or empty string.
     * @return the list of all matching cohort on the cohort name.
     * @throws org.apache.lucene.queryParser.ParseException
     *                             when query parser from lucene unable to parse the query string.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public List<CohortData> getByName(final String name) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Get all searchable object for the particular type.
     *
     * @return list of all searchable object or empty list.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public List<CohortData> getAll() throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Delete the searchable object from the lucene repository.
     *
     * @param searchable the object to be deleted.
     * @param resource   the resource descriptor used to retrieve the object from the repository.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void delete(final CohortData searchable, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Delete list of objects from the local repository. Use this save method when you want to delete multiple
     * objects at the same time.
     *
     * @param objects  the objects to be deleted.
     * @param resource the resource descriptor used for deleting.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void delete(final List<CohortData> objects, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }
}
