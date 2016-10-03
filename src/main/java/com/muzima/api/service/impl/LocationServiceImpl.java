/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.LocationDao;
import com.muzima.api.model.Location;
import com.muzima.api.service.LocationService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;
import com.muzima.util.DateUtils;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationServiceImpl implements LocationService {

    @Inject
    private LocationDao locationDao;

    protected LocationServiceImpl() {
    }

    @Override
    public Location downloadLocationByUuid(final String uuid) throws IOException {
        Location location = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<Location> locations = locationDao.download(parameter, Constants.UUID_LOCATION_RESOURCE);
        if (!CollectionUtil.isEmpty(locations)) {
            if (locations.size() > 1) {
                throw new IOException("Unable to uniquely identify a location record.");
            }
            location = locations.get(0);
        }
        return location;
    }

    @Override
    public List<Location> downloadLocationsByName(final String name, Date syncDate) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        if (syncDate != null) {
            parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
        }
        return sortNameAscending(locationDao.download(parameter, Constants.SEARCH_LOCATION_RESOURCE));
    }

    @Override
    public Location getLocationByUuid(String uuid) throws IOException {
        return locationDao.getByUuid(uuid);
    }

    @Override
    public List<Location> downloadLocationsByName(String name) throws IOException {
        return downloadLocationsByName(name,null);
    }

    @Override
    public Location saveLocation(Location location) throws IOException {
        if(!locationExists(location)){
            locationDao.save(location,Constants.UUID_LOCATION_RESOURCE);
            return location;
        }
        return null;
    }

    @Override
    public void saveLocations(List<Location> locations) throws IOException {
        locationDao.save(locations, Constants.UUID_LOCATION_RESOURCE);

    }

    @Override
    public List<Location> getAllLocations() throws IOException {
        return sortNameAscending(locationDao.getAll());
    }

    @Override
    public Integer countAllLocations() throws IOException {
        return locationDao.countAll();
    }

    @Override
    public List<Location> getLocationsByName(String name) throws IOException, ParseException {
        return sortNameAscending(locationDao.getMatchingLocationsByName(name));
    }

    @Override
    public void deleteLocation(Location location) throws IOException {
        locationDao.delete(location,Constants.UUID_LOCATION_RESOURCE);
    }

    @Override
    public void deleteLocations(List<Location> locations) throws IOException {
        locationDao.delete(locations,Constants.UUID_LOCATION_RESOURCE);
    }

    private boolean locationExists(Location location) throws IOException {
        return locationDao.getByUuid(location.getUuid()) != null;
    }

    private List<Location> sortNameAscending(List<Location> all) {
        Collections.sort(all);
        return all;
    }
}
