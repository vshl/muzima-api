/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Location;
import com.muzima.search.api.util.StreamUtil;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class LocationAlgorithmTest {
    private static final String JSON_DIRECTORY = "./";

    private LocationAlgorithm locationAlgorithm;
    private String jsonLocation;

    @Before
    public void setup() throws IOException {
        readJsonFromFile();
        locationAlgorithm = new LocationAlgorithm();
    }

    private void readJsonFromFile() throws IOException {
        URL jsonUri = LocationAlgorithm.class.getResource(JSON_DIRECTORY);
        File jsonDirectory = new File(jsonUri.getPath());
        jsonLocation = StreamUtil.readAsString(new FileReader(new File(jsonDirectory, "location.json")));
    }

    @Test
    public void deserialize_shouldReadNameFromJson() throws IOException {
        Location location = (Location) locationAlgorithm.deserialize(jsonLocation);
        assertThat(location.getName(), is("ABC Town"));
    }

    @Test
    public void deserialize_shouldReadUUIDFromJson() throws IOException {
        Location location = (Location) locationAlgorithm.deserialize(jsonLocation);
        assertThat(location.getUuid(),is("0ca78602-737f-408d-8ced-386ad12abcdb"));
    }

    @Test
    public void serialize_shouldSerializeNameToJson() throws IOException {
        Location location = buildLocation();

        String formJson = locationAlgorithm.serialize(location);

        Object jsonObject = JsonPath.read(formJson, "$");
        assertThat((String) JsonPath.read(jsonObject, "name"), is("QWERTY Town"));
    }

    @Test
    public void serialize_shouldSerializeUUIDToJson() throws IOException {
        Location location = buildLocation();

        String formJson = locationAlgorithm.serialize(location);

        Object jsonObject = JsonPath.read(formJson, "$");
        assertThat((String) JsonPath.read(jsonObject, "uuid"), is("uuid"));
    }

    private Location buildLocation() {
        Location location = new Location();
        location.setName("QWERTY Town");
        location.setUuid("uuid");
        return location;
    }


}

