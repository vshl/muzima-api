/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.muzima.api.model.algorithm;

import com.muzima.api.model.Privilege;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class PrivilegeAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String PRIVILEGE_REPRESENTATION = "(uuid,name)";

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Privilege privilege = new Privilege();
        privilege.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        privilege.setName(JsonUtils.readAsString(serialized, "$['name']"));
        return privilege;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Privilege privilege = (Privilege) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", privilege.getUuid());
        JsonUtils.writeAsString(jsonObject, "name", privilege.getUuid());
        return jsonObject.toJSONString();
    }
}
