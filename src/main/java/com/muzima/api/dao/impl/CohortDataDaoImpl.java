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

    private static final String TAG = CohortDataDaoImpl.class.getSimpleName();
    @Inject
    private ServiceContext serviceContext;

    protected CohortDataDaoImpl() {
        super(CohortData.class);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#download(java.util.Map, String)
     */
    @Override
    public List<CohortData> download(final Map<String, String> resourceParams, final String resource) throws IOException {
        CohortData consolidatedCohortData = new CohortData();
        List<Patient> patients = consolidatedCohortData.getPatients();
        List<CohortMember> members = consolidatedCohortData.getCohortMembers();
        List<Searchable> searchableList = service.loadObjects(resourceParams, serviceContext.getResource(resource));
        for (Searchable searchable : searchableList) {
            CohortData cohortData = (CohortData) searchable;
            consolidatedCohortData.setCohort(cohortData.getCohort());
            patients.addAll(cohortData.getPatients());
            members.addAll(cohortData.getCohortMembers());
        }
        return Arrays.asList(consolidatedCohortData);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#save(com.muzima.search.api.model.object.Searchable, String)
     */
    @Override
    public void save(final CohortData object, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#save(java.util.List, String)
     */
    @Override
    public void save(final List<CohortData> objects, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#update(com.muzima.search.api.model.object.Searchable, String)
     */
    @Override
    public void update(final CohortData object, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#update(java.util.List, String)
     */
    @Override
    public void update(final List<CohortData> objects, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#getByUuid(String)
     */
    @Override
    public CohortData getByUuid(final String uuid) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#countByName(String)
     */
    @Override
    public Integer countByName(final String name) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#getByName(String)
     */
    @Override
    public List<CohortData> getByName(final String name) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#getByName(String, Integer, Integer)
     */
    @Override
    public List<CohortData> getByName(final String name, final Integer page, final Integer pageSize) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#getAll()
     */
    @Override
    public List<CohortData> getAll() throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#getAll(Integer, Integer)
     */
    @Override
    public List<CohortData> getAll(final Integer page, final Integer pageSize) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#delete(com.muzima.search.api.model.object.Searchable, String)
     */
    @Override
    public void delete(final CohortData searchable, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#delete(java.util.List, String)
     */
    @Override
    public void delete(final List<CohortData> objects, final String resource) throws IOException {
        throw new IOException(
                "Cohort data object is just place holder for download purpose! " +
                        "No actual cohort data object will be saved in the lucene's document repository.");
    }
}
