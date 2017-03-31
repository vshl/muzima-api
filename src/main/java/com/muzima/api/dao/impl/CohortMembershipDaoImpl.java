/**
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.dao.impl;

import com.muzima.api.dao.CohortMembershipDao;
import com.muzima.api.dao.PatientDao;
import com.muzima.api.model.CohortMembership;
import com.muzima.search.api.context.ServiceContext;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.StringUtil;
import org.apache.lucene.queryParser.ParseException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CohortMembershipDaoImpl extends OpenmrsDaoImpl<CohortMembership> implements CohortMembershipDao {

	private static final String TAG = CohortMembershipDaoImpl.class.getSimpleName();

	@Inject
	private ServiceContext serviceContext;

	@Inject
	private PatientDao patientDao;

	protected CohortMembershipDaoImpl() {
		super(CohortMembership.class);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.muzima.api.dao.OpenmrsDao#download(java.util.Map, String)
	 */
	@Override
	public List<CohortMembership> download(final Map<String, String> resourceParams, final String resource)
			throws IOException {
		List<CohortMembership> memberships = new ArrayList<CohortMembership>();
		for (Searchable searchable : service.loadObjects(resourceParams, serviceContext.getResource(resource))) {
			memberships.add((CohortMembership) searchable);
		}
		return memberships;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipDao#getByCohortUuid(String)
	 */
	@Override
	public List<CohortMembership> getByCohortUuid(final String cohortUuid) throws IOException {
		List<Filter> filters = new ArrayList<Filter>();
		if (!StringUtil.isEmpty(cohortUuid)) {
			Filter filter = FilterFactory.createFilter("cohortUuid", cohortUuid);
			filters.add(filter);
		}
		return service.getObjects(filters, daoClass);
	}

	@Override
	public List<CohortMembership> getByCohortUuid(final String cohortUuid,
			final Integer page, final Integer pageSize) throws IOException {
		List<Filter> filters = new ArrayList<Filter>();
		if (!StringUtil.isEmpty(cohortUuid)) {
			Filter filter = FilterFactory.createFilter("cohortUuid", cohortUuid);
			filters.add(filter);
		}
		return service.getObjects(filters, daoClass, page, pageSize);
	}

	@Override
	public Integer countCohortMemberships(final String cohortUuid) throws IOException {
		List<Filter> filters = new ArrayList<Filter>();
		if (!StringUtil.isEmpty(cohortUuid)) {
			Filter filter = FilterFactory.createFilter("cohortUuid", cohortUuid);
			filters.add(filter);
		}
		return service.countObjects(filters, CohortMembership.class);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see CohortMembershipDao#search(String, String)
	 */
	@Override
	public List<CohortMembership> search(String term, String cohortUuid) throws IOException, ParseException {
		List<Filter> filters = new ArrayList<Filter>();
		if (!StringUtil.isEmpty(cohortUuid)) {
			Filter filter = FilterFactory.createFilter("cohortUuid", cohortUuid);
			filters.add(filter);
		}
		List<CohortMembership> localMemberships = service.getObjects(filters, daoClass);
		List<CohortMembership> searchedMemberships = search(term);
		List<CohortMembership> matchedMemberships = new ArrayList<CohortMembership>();
		for (CohortMembership searchedMembership : searchedMemberships) {
			for (CohortMembership localMembership : localMemberships) {
				if (localMembership.getPatient().getUuid().equalsIgnoreCase(searchedMembership.getPatient().getUuid())) {
					matchedMemberships.add(localMembership);
					break;
				}
			}
		}
		return matchedMemberships;
	}

	@Override
	public List<CohortMembership> search(String term) throws IOException, ParseException {
		if (!StringUtil.isEmpty(term)) {
			if ("true".equalsIgnoreCase(term) || "false".equalsIgnoreCase(term)) {
				boolean status = Boolean.parseBoolean(term);
				List<CohortMembership> memberships = new ArrayList<CohortMembership>();
				for (CohortMembership membership : service.getObjects(StringUtil.EMPTY, daoClass)) {
					if (membership.isActive() == status) {
						memberships.add(membership);
					}
				}
				return memberships;
			} else if (containsDigit(term)) {
				StringBuilder query = new StringBuilder();
				query.append("identifier:").append(term).append("*");
				return service.getObjects(query.toString(), daoClass);
			} else {
				StringBuilder query = new StringBuilder();
				query.append("givenName:").append(term).append("*").append(" OR ");
				query.append("middleName:").append(term).append("*").append(" OR ");
				query.append("familyName:").append(term).append("*");
				return service.getObjects(query.toString(), daoClass);
			}
		}
		return service.getObjects(StringUtil.EMPTY, daoClass);
	}

	private boolean containsDigit(final String term) {
		for (char c : term.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}
}
