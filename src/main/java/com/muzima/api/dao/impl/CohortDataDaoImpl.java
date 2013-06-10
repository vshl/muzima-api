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

import com.muzima.api.dao.CohortDataDao;
import com.muzima.api.model.CohortData;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

public class CohortDataDaoImpl extends OpenmrsDaoImpl<CohortData> implements CohortDataDao {

    private static final String TAG = CohortDataDaoImpl.class.getSimpleName();

    protected CohortDataDaoImpl() {
        super(CohortData.class);
    }

    /**
     * Save the object to the local repository.
     *
     * @param object   the object to be saved.
     * @param resource the resource descriptor used for saving.
     * @return saved object.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public CohortData save(final CohortData object, final String resource) throws IOException {
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
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public CohortData update(final CohortData object, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Get the OpenMRS searchable object using the uuid.
     *
     * @param uuid the uuid of the searchable object.
     * @return the searchable object.
     * @throws java.io.IOException when search api unable to process the resource.
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
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public List<CohortData> getByName(final String name) throws ParseException, IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Get all searchable object for the particular type.
     *
     * @return list of all searchable object or empty list.
     * @throws org.apache.lucene.queryParser.ParseException
     *                             when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public List<CohortData> getAll() throws ParseException, IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * Delete the searchable object from the lucene repository.
     *
     * @param searchable the object to be deleted.
     * @param resource   the resource descriptor used to retrieve the object from the repository.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public CohortData delete(final CohortData searchable, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }
}
