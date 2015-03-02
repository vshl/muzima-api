/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.muzima.api.dao.LocationDao;
import com.muzima.api.model.Location;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

public class LocationDaoImpl extends OpenmrsDaoImpl<Location> implements LocationDao {

    private static final String TAG = FormDaoImpl.class.getSimpleName();

    protected LocationDaoImpl() {
        super(Location.class);
    }

    @Override
    public Location getByLocationByName(String locationName) throws ParseException, IOException {
        Location location = null;
        StringBuilder query = new StringBuilder();
        if (!StringUtil.isEmpty(locationName)) {
            query.append("name:").append(locationName);
        }
        List<Location> locations = service.getObjects(query.toString(), daoClass);
        if (!CollectionUtil.isEmpty(locations)) {
            if (locations.size() > 1) {
                throw new IOException("Unable to uniquely identify a location using the identifier");
            }
            location = locations.get(0);
        }
        return location;
    }

    @Override
    public List<Location> getMatchingLocationsByName(String name) throws ParseException, IOException {
        StringBuilder query = new StringBuilder();
        if (!StringUtil.isEmpty(name)) {
            query.append("name:").append(name);
        }
        return service.getObjects(query.toString(), daoClass);
    }
}