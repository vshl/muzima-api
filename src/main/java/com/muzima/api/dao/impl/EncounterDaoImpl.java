/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.muzima.api.dao.EncounterDao;
import com.muzima.api.model.Encounter;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class EncounterDaoImpl extends OpenmrsDaoImpl<Encounter> implements EncounterDao {

    protected EncounterDaoImpl() {
        super(Encounter.class);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterDao#getEncountersByPatientUuid(String)
     */
    @Override
    public List<Encounter> getEncountersByPatientUuid(final String patientUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter filter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

    public List<Encounter> getEncountersByFormDataUuid(final String formDataUuid) throws IOException{
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(formDataUuid)) {
            Filter filter = FilterFactory.createFilter("formDataUuid", formDataUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterDao#countEncountersByPatientUuid(String)
     */
    @Override
    public int countEncountersByPatientUuid(final String patientUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter filter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(filter);
        }
        return service.countObjects(filters, daoClass);
    }
}
