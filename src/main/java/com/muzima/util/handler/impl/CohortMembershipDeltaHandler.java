/*
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.util.handler.impl;

import com.muzima.api.model.CohortMembership;
import com.muzima.util.handler.CollectionDeltaHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CohortMembershipDeltaHandler implements CollectionDeltaHandler<CohortMembership> {

	private static final Logger logger = LoggerFactory.getLogger(CohortMembershipDeltaHandler.class.getSimpleName());

	/**
	 * Returns the difference between two collections for new Cohort Membership Collection member objects
	 *
	 * @param downloadedCollection downloaded Cohort Membership Collection objects
	 * @param localCollection      local lucene repository Cohort Membership Collection member objects
	 * @return list of new Cohort Membership Collection member objects that does not exist in the local repository or empty list
	 */
	@Override
	public ArrayList<CohortMembership> getNewCollection(Collection<CohortMembership> downloadedCollection,
			Collection<CohortMembership> localCollection) {
		Set<CohortMembership> downloadedMemberships = new HashSet<CohortMembership>(downloadedCollection);
		downloadedMemberships.removeAll(localCollection);

		Iterator<CohortMembership> localIter = localCollection.iterator();
		Iterator<CohortMembership> downloadedIter = downloadedMemberships.iterator();
		while (downloadedIter.hasNext()) {
			CohortMembership downloadedMembership = downloadedIter.next();
			while (localIter.hasNext()) {
				CohortMembership localMembership = localIter.next();
				if (localMembership.contains(downloadedMembership.getUuid())) {
					downloadedIter.remove();
				}
			}
		}
		return new ArrayList<CohortMembership>(downloadedMemberships);
	}

	/**
	 * Returns the difference between the two collections for updated Cohort Membership Collection member objects
	 *
	 * @param downloadedCollection downloaded Cohort Membership Collection objects
	 * @param localCollection      local lucene repository Cohort Membership Collection member objects
	 * @return list of updated Cohort Membership Collection member objects that exist in the local repository or empty list
	 */
	@Override
	public ArrayList<CohortMembership> getUpdatedCollection(Collection<CohortMembership> downloadedCollection,
			Collection<CohortMembership> localCollection) {
		ArrayList<CohortMembership> updatedMemberships = new ArrayList<CohortMembership>();
		Set<CohortMembership> downloadedMemberships = new HashSet<CohortMembership>(downloadedCollection);
		downloadedMemberships.removeAll(localCollection);

		Iterator<CohortMembership> localIter = localCollection.iterator();
		Iterator<CohortMembership> downloadedIter = downloadedMemberships.iterator();
		while (downloadedIter.hasNext()) {
			CohortMembership downloadedMembership = downloadedIter.next();
			while (localIter.hasNext()) {
				CohortMembership localMembership = localIter.next();
				if (localMembership.contains(downloadedMembership.getUuid())) {
					updatedMemberships.add(downloadedMembership);
				}
			}
		}
		return updatedMemberships;
	}
}

