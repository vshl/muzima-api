/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.muzima.api.model.Location;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

/**
 * TODO: Write brief description about the class here.
 */
public class LocationAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String LOCATION_STANDARD_REPRESENTATION = "(uuid,name,id)";

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Location location = new Location();
        location.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        location.setName(JsonUtils.readAsString(serialized, "$['name']"));
        location.setId(JsonUtils.readAsInteger(serialized, "$['id']"));
        return location;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Location location = (Location) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", location.getUuid());
        JsonUtils.writeAsString(jsonObject, "name", location.getName());
        JsonUtils.writeAsInteger(jsonObject, "id", location.getId());
        return jsonObject.toJSONString();
    }
}
