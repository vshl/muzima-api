/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.MemberDaoImpl;
import com.muzima.api.model.CohortMember;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(MemberDaoImpl.class)
public interface MemberDao extends SearchableDao<CohortMember> {

    /**
     * Count the number of cohort member records for a cohort in the local
     * lucene repository.
     *
     * @param uuid the uuid of the cohort.
     * @return number of cohort members in the lucene repository.
     */
    Integer countMembers(final String uuid) throws IOException;

    /**
     * Get member objects by their cohort's uuid.
     *
     * @param cohortUuid the cohort uuid.
     * @return list of all member objects for the cohort's uuid.
     * @throws IOException when search api unable to process the resource.
     */
    List<CohortMember> getByCohortUuid(final String cohortUuid) throws IOException;

    Integer countByPatientUUID(final String patientUuid) throws IOException;

    List<CohortMember> getByCohortUuid(final String cohortUuid, final Integer page,
                                       final Integer pageSize) throws IOException;
}
