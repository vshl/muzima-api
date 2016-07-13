/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.muzima.api.dao.ObservationDao;
import com.muzima.api.model.Concept;
import com.muzima.api.model.Observation;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObservationDaoImpl extends OpenmrsDaoImpl<Observation> implements ObservationDao {

    private static final String TAG = ObservationDao.class.getSimpleName();

    protected ObservationDaoImpl() {
        super(Observation.class);
    }

    /**
     * Search observations for patient with matching uuid of the question.
     *
     * @param patientUuid the uuid of the patient.
     * @param conceptUuid the uuid of the question of the observations.
     * @return all observations for the patient with question matching the search term.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public List<Observation> get(final String patientUuid, final String conceptUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter patientFilter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(patientFilter);
        }
        if (!StringUtil.isEmpty(conceptUuid)) {
            Filter conceptFilter = FilterFactory.createFilter("conceptUuid", conceptUuid);
            filters.add(conceptFilter);
        }
        List<Observation> obs = service.getObjects(filters, daoClass);
        return obs;
    }

    @Override
    public int count(final String patientUuid, final String conceptUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter patientFilter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(patientFilter);
        }
        if (!StringUtil.isEmpty(conceptUuid)) {
            Filter conceptFilter = FilterFactory.createFilter("conceptUuid", conceptUuid);
            filters.add(conceptFilter);
        }

        return service.countObjects(filters, daoClass);
    }

    @Override
    public List<Observation> get(final String patientUuid, final String conceptUuid,
                                 final Integer page, final Integer pageSize) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter patientFilter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(patientFilter);
        }
        if (!StringUtil.isEmpty(conceptUuid)) {
            Filter conceptFilter = FilterFactory.createFilter("conceptUuid", conceptUuid);
            filters.add(conceptFilter);
        }
        return service.getObjects(filters, daoClass, page, pageSize);
    }

    @Override
    public List<Observation> get(Concept concept) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (concept != null) {
            Filter conceptFilter = FilterFactory.createFilter("conceptUuid", concept.getUuid());
            filters.add(conceptFilter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<Observation> get(String formDataUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (formDataUuid != null) {
            Filter conceptFilter = FilterFactory.createFilter("formDataUuid", formDataUuid);
            filters.add(conceptFilter);
        }
        return service.getObjects(filters, daoClass);
    }
}
