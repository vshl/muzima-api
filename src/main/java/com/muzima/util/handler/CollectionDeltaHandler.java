/*
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.util.handler;

import com.muzima.api.service.MuzimaInterface;

import java.util.Collection;

public interface CollectionDeltaHandler<T> extends MuzimaInterface {

	/**
	 * Returns the difference between two collections for new Collection member objects
	 * @param downloadedCollection downloaded Collection objects
	 * @param localCollection local lucene repository Collection member objects
	 * @return list of new Collection member objects that does not exist in the local repository or empty list
	 */
	Collection<T> getNewCollection(final Collection<T> downloadedCollection, final Collection<T> localCollection)
			throws Exception;

	/**
	 * Returns the difference between the two collections for updated Collection member objects
	 * @param downloadedCollection downloaded Collection objects
	 * @param localCollection local lucene repository Collection member objects
	 * @return list of updated Collection member objects that exist in the local repository or empty list
	 */
	Collection<T> getUpdatedCollection(final Collection<T> downloadedCollection,
			final Collection<T> localCollection);
}
