/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Location;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class LocationServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(LocationServiceTest.class.getSimpleName());
    private Context context;
    private LocationService locationService;
    private Location location;
    private List<Location> locations;

    private static int nextInt(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    @Before
    public void prepare() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/muzima/" + UUID.randomUUID().toString();
        ContextFactory.setProperty(Constants.LUCENE_DIRECTORY_PATH, path);
        context = ContextFactory.createContext();
        context.setPreferredLocale("en");
        context.openSession();
        if (!context.isAuthenticated()) {
            context.authenticate("admin", "test", "http://demo2.muzima.org", false);
        }

        locationService = context.getLocationService();
        locations = locationService.downloadLocationsByName("Unknown Location");
        location = locations.get(nextInt(locations.size()));
    }

    @After
    public void cleanUp() throws Exception {
        String lucenePath = ContextFactory.getProperty(Constants.LUCENE_DIRECTORY_PATH);
        File luceneDirectory = new File(lucenePath);
        for (String filename : luceneDirectory.list()) {
            File file = new File(luceneDirectory, filename);
            Assert.assertTrue(file.delete());
        }
        context.deauthenticate();
        context.closeSession();
    }

    /**
     * @verifies download location with matching uuid.
     * @see LocationService#downloadLocationsByName(String)
     */
    @Test
    public void downloadLocationByName_shouldDownloadLocationWithMatchingName() throws Exception {
        String name = location.getName();
        String partialName = name.substring(name.length() - 1);
        List<Location> downloadedLocations = locationService.downloadLocationsByName(partialName);
        for (Location downloadedLocation : downloadedLocations) {
            assertThat(downloadedLocation.getName(), containsString(partialName));
        }
    }

    /**
     * @verifies download location with matching uuid.
     * @see LocationService#downloadLocationByUuid(String)
     */
    @Test
    public void downloadLocationByUuid_shouldDownloadLocationWithMatchingUuid() throws Exception {
        Location downloadedLocation = locationService.downloadLocationByUuid(location.getUuid());
        assertThat(downloadedLocation, samePropertyValuesAs(location));
    }

    /**
     * @verifies save location to local data repository.
     * @see LocationService#saveLocation(com.muzima.api.model.Location)
     */
    @Test
    public void saveLocation_shouldSaveLocationToLocalDataRepository() throws Exception {
        int locationCounter = locationService.countAllLocations();
        locationService.saveLocation(location);
        assertThat(locationService.countAllLocations(), equalTo(locationCounter + 1));
        locationService.saveLocation(location);
        assertThat(locationService.countAllLocations(), equalTo(locationCounter + 1));
    }

    /**
     * @verifies save location to local data repository.
     * @see LocationService#saveLocations(java.util.List) (java.util.List)
     */
    @Test
    public void savePatients_shouldSaveLocationsToLocalDataRepository() throws Exception {
        int locationCounter = locationService.countAllLocations();
        locationService.saveLocations(locations);
        assertThat(locationService.countAllLocations(), equalTo(locationCounter + locations.size()));
        locationService.saveLocations(locations);
        assertThat(locationService.countAllLocations(), equalTo(locationCounter + locations.size()));
    }

    /**
     * @verifies return all registered location.
     * @see com.muzima.api.service.LocationService#getAllLocations()
     */
    @Test
    public void getAllLocations_shouldReturnAllRegisteredLocations() throws Exception {
        assertThat(locationService.countAllLocations(), equalTo(0));
        locationService.saveLocations(locations);
        List<Location> savedLocations = locationService.getLocationsByName(StringUtil.EMPTY);
        assertThat(savedLocations, hasSize(locations.size()));
    }
}
