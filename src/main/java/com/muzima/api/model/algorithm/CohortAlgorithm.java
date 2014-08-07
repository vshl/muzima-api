/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.muzima.api.model.Cohort;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class CohortAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String COHORT_STANDARD_REPRESENTATION = "(uuid,voided,name)";

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Cohort cohort = new Cohort();
        cohort.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        cohort.setVoided(JsonUtils.readAsBoolean(serialized, "$['voided']"));
        cohort.setName(JsonUtils.readAsString(serialized, "$['name']"));
        cohort.setDynamic(JsonUtils.readAsBoolean(serialized, "$['dynamic']"));
        return cohort;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Cohort cohort = (Cohort) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", cohort.getUuid());
        JsonUtils.writeAsBoolean(jsonObject, "voided", cohort.isVoided());
        JsonUtils.writeAsString(jsonObject, "name", cohort.getName());
        JsonUtils.writeAsBoolean(jsonObject, "dynamic", cohort.isDynamic());
        return jsonObject.toJSONString();
    }
}
