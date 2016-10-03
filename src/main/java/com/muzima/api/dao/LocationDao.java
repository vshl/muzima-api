/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.LocationDaoImpl;
import com.muzima.api.model.Location;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

@ImplementedBy(LocationDaoImpl.class)
public interface LocationDao extends OpenmrsDao<Location> {

    /**
     * Get a location by the user name of the location.
     *
     * @param locationName the name of the location
     * @return location with matching location.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    Location getByLocationByName(final String locationName) throws ParseException, IOException;

    /**
     * Get locations by the name of the location. Passing empty string will returns all registered locations.
     *
     * @param name the partial name of the location or empty string.
     * @return the list of all matching locations on the location name.
     * @throws IOException when search api unable to process the resource.
     */
    List<Location> getMatchingLocationsByName(final String name) throws ParseException, IOException;

}
