/**
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.CohortMembershipDaoImpl;
import com.muzima.api.model.CohortMembership;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

@ImplementedBy(CohortMembershipDaoImpl.class)
public interface CohortMembershipDao extends OpenmrsDao<CohortMembership> {

	/**
	 * Get handler objects by their cohort's uuid.
	 *
	 * @param cohortUuid the cohort uuid.
	 * @return list of all handler objects for the cohort's uuid.
	 * @throws IOException when search api unable to process the resource.
	 */
	List<CohortMembership> getByCohortUuid(final String cohortUuid) throws IOException;

	List<CohortMembership> getByCohortUuid(final String cohortUuid, final Integer page, final Integer pageSize)
			throws IOException;

	Integer countCohortMemberships(final String cohortUuid) throws IOException;

	/**
	 * Search for cohort memberships matching the term
	 * @param term the term that should match
	 * @param cohortUuid the cohort uuid
	 * @return list of memberships matching the term
	 */
	List<CohortMembership> search(String term, String cohortUuid) throws IOException, ParseException;

	List<CohortMembership> search(String term) throws IOException, ParseException;
}
