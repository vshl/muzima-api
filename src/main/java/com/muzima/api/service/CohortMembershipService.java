/*
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortMembership;
import com.muzima.api.service.impl.CohortMembershipServiceImpl;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Service handling all operation to the @{Cohort} actor/model
 */
@ImplementedBy(CohortMembershipServiceImpl.class)
public interface CohortMembershipService extends MuzimaInterface {

	/**
	 * @param uuid Cohort uuid
	 * @return CohortData or null
	 * @throws IOException
	 */
	List<CohortMembership> downloadCohortMemberships(final String uuid, final Date syncDate) throws IOException;

	/**
	 * @param cohort Cohort object
	 * @return CohortData or null
	 * @throws IOException
	 */
	List<CohortMembership> downloadCohortMemberships(final Cohort cohort, final Date syncDate) throws IOException;

	/**
	 * Save the cohort handler object to the local lucene directory.
	 *
	 * @param membership the handler object to be saved.
	 * @throws IOException when search api unable to process the resource.
	 * @should save the cohort member object to local lucene repository.
	 */
	void saveCohortMembership(final CohortMembership membership) throws IOException;

	/**
	 * Save the cohort handler objects to the local lucene directory.
	 *
	 * @param memberships the handler objects to be saved.
	 * @throws IOException when search api unable to process the resource.
	 * @should save list of cohort memberships to local lucene repository.
	 */
	void saveCohortMemberships(final List<CohortMembership> memberships) throws IOException;

	/**
	 * Update the cohort member object to the local lucene directory.
	 *
	 * @param membership the handler object to be updated.
	 * @throws IOException when search api unable to process the resource.
	 * @should replace the cohort membership in local lucene repository.
	 */
	void updateCohortMembership(final CohortMembership membership) throws IOException;

	/**
	 * Update the cohort member objects to the local lucene directory.
	 *
	 * @param memberships the handler objects to be updated.
	 * @throws IOException when search api unable to process the resource.
	 * @should replace the cohort memberships in local lucene repository.
	 */
	void updateCohortMemberships(final List<CohortMembership> memberships) throws IOException;

	/**
	 * Get all memberships under the current cohort identified by the cohort's uuid which already saved in the local
	 * repository.
	 *
	 * @param cohortUuid the cohort's uuid.
	 * @return list of all patients' uuid under current cohort uuid or empty list when no patient are in the cohort.
	 * @throws IOException when search api unable to process the resource.
	 * @should return list of all members for the cohort.
	 * @should return empty list when no member are in the cohort.
	 */
	List<CohortMembership> getCohortMemberships(final String cohortUuid) throws IOException;

	List<CohortMembership> getCohortMemberships(final Cohort cohort) throws IOException;

	/**
	 * Get all cohort memberships under the current cohort identified by the cohort's uuid which already saved in the local
	 * repository.
	 *
	 * @param cohortUuid the cohort's uuid.
	 * @param page       the current page.
	 * @param pageSize   the maximum number of objects in the page.
	 * @return list of all patients' uuid under current cohort uuid or empty list when no patient are in the cohort.
	 * @throws IOException when search api unable to process the resource.
	 * @should return list of all members for the cohort.
	 * @should return empty list when no member are in the cohort.
	 */
	List<CohortMembership> getCohortMemberships(final String cohortUuid, final Integer page,
			final Integer pageSize) throws IOException;

	List<CohortMembership> getCohortMemberships(final Cohort cohort, final Integer page,
			final Integer pageSize) throws IOException;

	List<CohortMembership> getCohortMemberships(final Integer page, final Integer pageSize) throws IOException;

	/**
	 * Get all saved cohort memberships in the local repository
	 * @return all cohort handler or empty list
	 * @throws IOException when search api unable to process the resource
	 */
	List<CohortMembership> getAllCohortMemberships() throws IOException;

	/**
	 * Delete all members for the current cohort identified by the cohort's uuid.
	 *
	 * @param cohortUuid the cohort's uuid.
	 * @throws IOException when search api unable to process the resource.
	 * @should delete all members for the cohort from the local repository.
	 */
	void deleteCohortMemberships(final String cohortUuid) throws IOException;

	void deleteCohortMemberships(final Cohort cohort) throws IOException;

	/**
	 * Count the total number of cohort memberships of a cohort in the lucene repository.
	 *
	 * @param cohortUuid the cohort uuid.
	 * @return total number of cohort handler for the cohort.
	 * @throws IOException when search api unable to process the resource.
	 */
	Integer countCohortMemberships(final String cohortUuid) throws IOException;

	Integer countCohortMemberships(final Cohort cohort) throws IOException;

	/**
	 * Count all cohort handler objects
	 *
	 * @return the total number of cohort handler objects
	 * @throws IOException when search api unable to process the resource
	 */
	Integer countAllCohortMemberships() throws IOException;

	/**
	 * Search for memberships with matching characteristic on the name or identifier with the search term,
	 * within the give cohort.
	 *
	 * @param term search term
	 * @param cohortUuid the cohort uuid
	 * @return list of cohort memberships with matching search term or empty list
	 */
	List<CohortMembership> searchCohortMemberships(final String term, final String cohortUuid)
			throws IOException, ParseException;

	List<CohortMembership> searchCohortMemberships(final String term) throws IOException, ParseException;
}
