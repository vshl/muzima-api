/*
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.CohortMembershipDao;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortMembership;
import com.muzima.api.service.CohortMembershipService;
import com.muzima.util.Constants;
import com.muzima.util.DateUtils;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CohortMembershipServiceImpl implements CohortMembershipService {

	@Inject
	private CohortMembershipDao membershipDao;

	/**
	 * @see CohortMembershipService#downloadCohortMemberships(String, Date)
	 */
	@Override
	public List<CohortMembership> downloadCohortMemberships(final String uuid, final Date syncDate) throws IOException {
		String membershipResourceName = Constants.COHORT_MEMBERSHIP_DATA_RESOURCE;
		Map<String, String> parameter = new HashMap<String, String>() {{
			put("uuid", uuid);
		}};
		if (syncDate != null) {
			parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
		}
		return membershipDao.download(parameter, membershipResourceName);
	}

	/**
	 * @see CohortMembershipService#downloadCohortMemberships(Cohort, Date)
	 */
	@Override
	public List<CohortMembership> downloadCohortMemberships(final Cohort cohort, final Date syncDate) throws IOException {
		return downloadCohortMemberships(cohort.getUuid(), syncDate);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#saveCohortMembership(CohortMembership)
	 */
	@Override
	public void saveCohortMembership(final CohortMembership membership) throws IOException {
		membershipDao.save(membership, Constants.COHORT_MEMBERSHIP_DATA_RESOURCE);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#saveCohortMemberships(java.util.List)
	 */
	@Override
	public void saveCohortMemberships(final List<CohortMembership> memberships) throws IOException {
		membershipDao.save(memberships, Constants.COHORT_MEMBERSHIP_DATA_RESOURCE);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#updateCohortMembership(CohortMembership)
	 */
	@Override
	public void updateCohortMembership(final CohortMembership membership) throws IOException {
		membershipDao.update(membership, Constants.COHORT_MEMBERSHIP_DATA_RESOURCE);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#updateCohortMemberships(java.util.List)
	 */
	@Override
	public void updateCohortMemberships(final List<CohortMembership> memberships) throws IOException {
		membershipDao.update(memberships, Constants.COHORT_MEMBERSHIP_DATA_RESOURCE);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#getCohortMemberships(String)
	 */
	@Override
	public List<CohortMembership> getCohortMemberships(final String cohortUuid) throws IOException {
		return membershipDao.getByCohortUuid(cohortUuid);
	}

	@Override
	public List<CohortMembership> getCohortMemberships(final Cohort cohort) throws IOException {
		return membershipDao.getByCohortUuid(cohort.getUuid());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#getCohortMemberships(Cohort, Integer, Integer)
	 */
	@Override
	public List<CohortMembership> getCohortMemberships(final String cohortUuid, final Integer page, final Integer pageSize)
			throws IOException {
		return membershipDao.getByCohortUuid(cohortUuid, page, pageSize);
	}

	@Override
	public List<CohortMembership> getCohortMemberships(final Cohort cohort, final Integer page, final Integer pageSize)
			throws IOException {
		return membershipDao.getByCohortUuid(cohort.getUuid(), page, pageSize);
	}

	@Override
	public List<CohortMembership> getCohortMemberships(Integer page, Integer pageSize) throws IOException {
		return membershipDao.getAll(page, pageSize);
	}

	@Override
	public List<CohortMembership> getAllCohortMemberships() throws IOException {
		return membershipDao.getAll();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#deleteCohortMemberships(Cohort)
	 */
	@Override
	public void deleteCohortMemberships(final String cohortUuid) throws IOException {
		membershipDao.delete(getCohortMemberships(cohortUuid), Constants.COHORT_MEMBERSHIP_DATA_RESOURCE);
	}

	@Override
	public void deleteCohortMemberships(final Cohort cohort) throws IOException {
		membershipDao.delete(getCohortMemberships(cohort.getUuid()), Constants.COHORT_MEMBERSHIP_DATA_RESOURCE);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#countCohortMemberships(String)
	 */
	@Override
	public Integer countCohortMemberships(final String cohortUuid) throws IOException {
		return membershipDao.countCohortMemberships(cohortUuid);
	}

	@Override
	public Integer countCohortMemberships(final Cohort cohort) throws IOException {
		return membershipDao.countCohortMemberships(cohort.getUuid());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#countAllCohortMemberships()
	 */
	@Override
	public Integer countAllCohortMemberships() throws IOException {
		return membershipDao.countAll();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipService#searchCohortMemberships(String, String)
	 */
	@Override
	public List<CohortMembership> searchCohortMemberships(String term, String cohortUuid) throws IOException,
			ParseException {
		return membershipDao.search(term, cohortUuid);
	}

	@Override
	public List<CohortMembership> searchCohortMemberships(String term) throws IOException, ParseException {
		return membershipDao.search(term);
	}

}
