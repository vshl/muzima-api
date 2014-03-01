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
import com.muzima.api.dao.MemberDao;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortData;
import com.muzima.api.model.CohortMember;
import com.muzima.api.service.CohortService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;
import com.muzima.util.DateUtils;

import java.io.IOException;
import java.util.*;

public class CohortServiceImpl implements CohortService {

    @Inject
    private CohortDao cohortDao;

    @Inject
    private MemberDao memberDao;

    @Inject
    private CohortDataDao cohortDataDao;

    protected CohortServiceImpl() {
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadCohortByUuid(String)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public Cohort downloadCohortByUuid(final String uuid) throws IOException {
        Cohort cohort = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<Cohort> cohorts = cohortDao.download(parameter, Constants.UUID_STATIC_COHORT_RESOURCE);
        if (!CollectionUtil.isEmpty(cohorts)) {
            if (cohorts.size() > 1) {
                throw new IOException("Unable to uniquely identify a cohort record.");
            }
            cohort = cohorts.get(0);
        }
        return cohort;
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadCohortsByName(String)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<Cohort> downloadCohortsByName(final String name) throws IOException {
        return downloadCohortsByNameAndSyncDate(name, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadCohortsByName(String)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<Cohort> downloadCohortsByNameAndSyncDate(final String name, final Date syncDate) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        if (syncDate != null) {
            parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
        }
        return cohortDao.download(parameter, Constants.SEARCH_STATIC_COHORT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadDynamicCohortByUuid(String)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public Cohort downloadDynamicCohortByUuid(final String uuid) throws IOException {
        Cohort cohort = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<Cohort> cohorts = cohortDao.download(parameter, Constants.UUID_DYNAMIC_COHORT_RESOURCE);
        if (!CollectionUtil.isEmpty(cohorts)) {
            if (cohorts.size() > 1) {
                throw new IOException("Unable to uniquely identify a cohort record.");
            }
            cohort = cohorts.get(0);
        }
        return cohort;
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadDynamicCohortsByName(String)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<Cohort> downloadDynamicCohortsByName(final String name) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        return cohortDao.download(parameter, Constants.SEARCH_DYNAMIC_COHORT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#saveCohort(com.muzima.api.model.Cohort)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public void saveCohort(final Cohort cohort) throws IOException {
        if (cohort.isDynamic()) {
            cohortDao.save(cohort, Constants.UUID_DYNAMIC_COHORT_RESOURCE);
        } else {
            cohortDao.save(cohort, Constants.UUID_STATIC_COHORT_RESOURCE);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#saveCohorts(java.util.List)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public void saveCohorts(final List<Cohort> cohorts) throws IOException {
        List<Cohort> dynamicCohorts = new ArrayList<Cohort>();
        List<Cohort> staticCohorts = new ArrayList<Cohort>();
        for (Cohort cohort : cohorts) {
            if (cohort.isDynamic()) {
                dynamicCohorts.add(cohort);
            } else {
                staticCohorts.add(cohort);
            }
        }
        cohortDao.save(staticCohorts, Constants.UUID_STATIC_COHORT_RESOURCE);
        cohortDao.save(dynamicCohorts, Constants.UUID_DYNAMIC_COHORT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#updateCohort(com.muzima.api.model.Cohort)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public void updateCohort(final Cohort cohort) throws IOException {
        if (cohort.isDynamic()) {
            cohortDao.update(cohort, Constants.UUID_DYNAMIC_COHORT_RESOURCE);
        } else {
            cohortDao.update(cohort, Constants.UUID_STATIC_COHORT_RESOURCE);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#updateCohorts(java.util.List)
     */
    @Override
    public void updateCohorts(final List<Cohort> cohorts) throws IOException {
        List<Cohort> dynamicCohorts = new ArrayList<Cohort>();
        List<Cohort> staticCohorts = new ArrayList<Cohort>();
        for (Cohort cohort : cohorts) {
            if (cohort.isDynamic()) {
                dynamicCohorts.add(cohort);
            } else {
                staticCohorts.add(cohort);
            }
        }
        cohortDao.update(staticCohorts, Constants.UUID_STATIC_COHORT_RESOURCE);
        cohortDao.update(dynamicCohorts, Constants.UUID_DYNAMIC_COHORT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#getCohortByUuid(String)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public Cohort getCohortByUuid(final String uuid) throws IOException {
        return cohortDao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#countCohorts(String)
     */
    @Override
    public Integer countCohorts(final String name) throws IOException {
        return cohortDao.countByName(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#getCohortsByName(String)
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<Cohort> getCohortsByName(final String name) throws IOException {
        return cohortDao.getByName(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#getCohortsByName(String, Integer, Integer)
     */
    @Override
    public List<Cohort> getCohortsByName(final String name, final Integer page, final Integer pageSize) throws IOException {
        return cohortDao.getByName(name, page, pageSize);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.CohortService#countAllCohorts()
     */
    @Override
    public Integer countAllCohorts() throws IOException {
        return cohortDao.countAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.CohortService#getAllCohorts()
     */
    @Override
    @Authorization(privileges = {"View Cohort Privilege"})
    public List<Cohort> getAllCohorts() throws IOException {
        return cohortDao.getAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#deleteCohort(com.muzima.api.model.Cohort)
     */
    @Override
    public void deleteCohort(final Cohort cohort) throws IOException {
        if (cohort.isDynamic()) {
            cohortDao.delete(cohort, Constants.UUID_DYNAMIC_COHORT_RESOURCE);
        } else {
            cohortDao.delete(cohort, Constants.UUID_STATIC_COHORT_RESOURCE);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#deleteCohorts(java.util.List)
     */
    @Override
    public void deleteCohorts(final List<Cohort> cohorts) throws IOException {
        List<Cohort> dynamicCohorts = new ArrayList<Cohort>();
        List<Cohort> staticCohorts = new ArrayList<Cohort>();
        for (Cohort cohort : cohorts) {
            if (cohort.isDynamic()) {
                dynamicCohorts.add(cohort);
            } else {
                staticCohorts.add(cohort);
            }
        }
        cohortDao.delete(staticCohorts, Constants.UUID_STATIC_COHORT_RESOURCE);
        cohortDao.delete(dynamicCohorts, Constants.UUID_DYNAMIC_COHORT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadCohortData(String, boolean)
     */
    @Override
    public CohortData downloadCohortData(final String uuid, final boolean dynamic) throws IOException {
        return downloadCohortDataAndSyncDate(uuid, dynamic, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadCohortData(String, boolean)
     */
    @Override
    public CohortData downloadCohortDataAndSyncDate(final String uuid, final boolean dynamic, final Date syncDate) throws IOException {
        CohortData cohortData = null;
        String resourceName = Constants.STATIC_COHORT_DATA_RESOURCE;
        if (dynamic) {
            resourceName = Constants.DYNAMIC_COHORT_DATA_RESOURCE;
        }
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        if (syncDate != null) {
            parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
        }
        List<CohortData> cohortDataList = cohortDataDao.download(parameter, resourceName);
        if (!CollectionUtil.isEmpty(cohortDataList)) {
            if (cohortDataList.size() > 1) {
                throw new IOException("Unable to uniquely identify a cohort data record.");
            }
            cohortData = cohortDataList.get(0);
        }
        return cohortData;
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadCohortData(com.muzima.api.model.Cohort)
     */
    @Override
    public CohortData downloadCohortData(final Cohort cohort) throws IOException {
        return downloadCohortData(cohort.getUuid(), cohort.isDynamic());
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#downloadCohortData(com.muzima.api.model.Cohort)
     */
    @Override
    public CohortData downloadCohortDataAndSyncDate(final Cohort cohort, final Date syncDate) throws IOException {
        return downloadCohortData(cohort.getUuid(), cohort.isDynamic());
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#saveCohortMember(com.muzima.api.model.CohortMember)
     */
    public void saveCohortMember(final CohortMember cohortMember) throws IOException {
        memberDao.save(cohortMember, Constants.LOCAL_COHORT_MEMBER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#saveCohortMembers(java.util.List)
     */
    @Override
    public void saveCohortMembers(final List<CohortMember> cohortMembers) throws IOException {
        memberDao.save(cohortMembers, Constants.LOCAL_COHORT_MEMBER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#updateCohortMember(com.muzima.api.model.CohortMember)
     */
    public void updateCohortMember(final CohortMember cohortMember) throws IOException {
        memberDao.update(cohortMember, Constants.LOCAL_COHORT_MEMBER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#updateCohortMembers(java.util.List)
     */
    @Override
    public void updateCohortMembers(final List<CohortMember> cohortMembers) throws IOException {
        memberDao.update(cohortMembers, Constants.LOCAL_COHORT_MEMBER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#countCohortMembers(String)
     */
    @Override
    public Integer countCohortMembers(final String cohortUuid) throws IOException {
        return memberDao.countMembers(cohortUuid);
    }

    @Override
    public Integer countCohortMembers(final Cohort cohort) throws IOException {
        return memberDao.countMembers(cohort.getUuid());
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#getCohortMembers(String)
     */
    @Override
    public List<CohortMember> getCohortMembers(final String cohortUuid) throws IOException {
        return memberDao.getByCohortUuid(cohortUuid);
    }

    @Override
    public List<CohortMember> getCohortMembers(final Cohort cohort) throws IOException {
        return memberDao.getByCohortUuid(cohort.getUuid());
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#getCohortMembers(String, Integer, Integer)
     */
    @Override
    public List<CohortMember> getCohortMembers(final String cohortUuid, final Integer page,
                                               final Integer pageSize) throws IOException {
        return memberDao.getByCohortUuid(cohortUuid, page, pageSize);
    }

    @Override
    public List<CohortMember> getCohortMembers(final Cohort cohort, final Integer page,
                                               final Integer pageSize) throws IOException {
        return memberDao.getByCohortUuid(cohort.getUuid(), page, pageSize);
    }

    /**
     * {@inheritDoc}
     *
     * @see CohortService#deleteCohortMembers(String)
     */
    @Override
    public void deleteCohortMembers(final String cohortUuid) throws IOException {
        memberDao.delete(getCohortMembers(cohortUuid), Constants.LOCAL_COHORT_MEMBER_RESOURCE);
    }

    @Override
    public void deleteCohortMembers(final Cohort cohort) throws IOException {
        memberDao.delete(getCohortMembers(cohort.getUuid()), Constants.LOCAL_COHORT_MEMBER_RESOURCE);
    }
}
