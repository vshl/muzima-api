/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Cohort;
import com.muzima.search.api.model.object.Searchable;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CohortAlgorithm extends BaseOpenmrsAlgorithm {

    private final Logger logger = LoggerFactory.getLogger(CohortAlgorithm.class.getSimpleName());

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param json the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String json) throws IOException {
        Cohort cohort = new Cohort();

        Object jsonObject = JsonPath.read(json, "$");

        boolean dynamic = false;
        try {
            dynamic = (Boolean) JsonPath.read(jsonObject, "$['dynamic']");
        } catch (InvalidPathException e) {
            logger.error("REST resource doesn't contains dynamic information. Exiting!");
        }
        cohort.setDynamic(dynamic);

        String uuid = JsonPath.read(jsonObject, "$['uuid']");
        cohort.setUuid(uuid);

        String name = JsonPath.read(jsonObject, "$['name']");
        cohort.setName(name);

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
        // serialize the minimum needed to identify an object for deletion purposes.
        Cohort cohort = (Cohort) object;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", cohort.getUuid());
        jsonObject.put("name", cohort.getName());
        jsonObject.put("dynamic", cohort.isDynamic());
        return jsonObject.toJSONString();
    }
}
