/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.Location;
import com.muzima.api.service.impl.LocationServiceImpl;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Service handling all operation to the @{Location} actor/model
 */
@ImplementedBy(LocationServiceImpl.class)
public interface LocationService extends MuzimaInterface {

    /**
     * Download a single location record from the location rest resource into the local lucene repository.
     *
     * @param uuid the uuid of the location.
     * @throws IOException when search api unable to process the resource.
     * @should download location with matching uuid.
     */
    Location downloadLocationByUuid(final String uuid) throws IOException;

    /**
     * Download all locations with name similar to the partial location name passed in the parameter.
     *
     * @param name     the partial name of the location to be downloaded. When empty, will return all locations available.
     * @param syncDate time the locations where last synched to the server.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download all locations with partially matched name.
     * @should download all locations when name is empty.
     */
    List<Location> downloadLocationsByName(final String name, final Date syncDate) throws IOException;

    /**
     * Get a single location record from the local repository with matching uuid.
     *
     * @param uuid the location uuid
     * @return location with matching uuid or null when no location match the uuid
     * @throws IOException when search api unable to process the resource.
     * @should return location with matching uuid
     * @should return null when no location match the uuid
     */

    Location getLocationByUuid(final String uuid) throws IOException;

    /**
     * Download all locations with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the locations to be downloaded. When empty, will return all locations available.
     * @throws IOException when search api unable to process the resource.
     * @should download all locations with partially matched name.
     * @should download all locations when name is empty.
     */
    List<Location> downloadLocationsByName(final String name) throws IOException;

    /**
     * Save location to the local lucene repository.
     *
     * @param location the location to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save location to local data repository.
     */
    Location saveLocation(final Location location) throws IOException;

    /**
     * Save list of locations to the local lucene repository.
     *
     * @param locations the locations to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save locations to local data repository.
     */
    void saveLocations(final List<Location> locations) throws IOException;

    /**
     * Get all saved locations in the local repository.
     *
     * @return all registered locations or empty list when no location is registered.
     * @throws IOException when search api unable to process the resource.
     * @should return all registered locations.
     * @should return empty list when no location is registered.
     */
    List<Location> getAllLocations() throws IOException;

    /**
     * Count all location objects.
     *
     * @return the total number of location objects.
     * @throws IOException when search api unable to process the resource.
     */
    Integer countAllLocations() throws IOException;

    /**
     * Get list of locations with name similar to the search term.
     *
     * @param name the location name.
     * @return list of all location with matching name or empty list when no location match the name.
     * @throws org.apache.lucene.queryParser.ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all locations with matching name partially.
     * @should return empty list when no locations match the name.
     */
    List<Location> getLocationsByName(final String name) throws IOException, ParseException;

    /**
     * Delete a location from the local data repository.
     *
     * @param location the location to be deleted.
     * @throws IOException when the search api unable to process the resource.
     * @should delete location from local data repository.
     */
    void deleteLocation(final Location location) throws IOException;

    /**
     * Delete a list of locations from the local data repository.
     *
     * @param locations the locations to be deleted.
     * @throws IOException when the search api unable to process the resource.
     * @should delete list of locations from local data repository.
     */
    void deleteLocations(final List<Location> locations) throws IOException;

}
