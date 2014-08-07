/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.muzima.api.model.EncounterType;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

/**
 * TODO: Write brief description about the class here.
 */
public class EncounterTypeAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String ENCOUNTER_TYPE_STANDARD_REPRESENTATION = "(uuid,name)";

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        EncounterType encounterType = new EncounterType();
        encounterType.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        encounterType.setName(JsonUtils.readAsString(serialized, "$['name']"));
        return encounterType;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        EncounterType encounterType = (EncounterType) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", encounterType.getUuid());
        JsonUtils.writeAsString(jsonObject, "name", encounterType.getName());
        return jsonObject.toJSONString();
    }
}
