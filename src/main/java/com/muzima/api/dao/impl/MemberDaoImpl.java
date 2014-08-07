/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.muzima.api.dao.CredentialDao;
import com.muzima.api.dao.MemberDao;
import com.muzima.api.model.CohortMember;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl extends SearchableDaoImpl<CohortMember> implements MemberDao {

    private static final String TAG = CredentialDao.class.getSimpleName();

    protected MemberDaoImpl() {
        super(CohortMember.class);
    }

    /**
     * Count the number of cohort member records for a cohort in the local
     * lucene repository.
     *
     * @param uuid the uuid of the cohort.
     * @return number of cohort members in the lucene repository.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public Integer countMembers(final String uuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(uuid)) {
            Filter filter = FilterFactory.createFilter("cohortUuid", uuid);
            filters.add(filter);
        }
        return service.countObjects(filters, CohortMember.class);
    }

    @Override
    public Integer countByPatientUUID(String patientUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter filter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(filter);
            return service.countObjects(filters, daoClass);
        }
        return 0;
    }

    /**
     * Get cohort by the name of the cohort. Passing empty string will returns all registered cohorts.
     *
     * @param cohortUuid the partial name of the cohort or empty string.
     * @return the list of all matching cohort on the cohort name.
     * @throws IOException when search api unable to process the resource.
     */
    public List<CohortMember> getByCohortUuid(final String cohortUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(cohortUuid)) {
            Filter filter = FilterFactory.createFilter("cohortUuid", cohortUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<CohortMember> getByCohortUuid(final String cohortUuid, final Integer page,
                                              final Integer pageSize) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(cohortUuid)) {
            Filter filter = FilterFactory.createFilter("cohortUuid", cohortUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass, page, pageSize);
    }
}
