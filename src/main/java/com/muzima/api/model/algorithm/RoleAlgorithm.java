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

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Privilege;
import com.muzima.api.model.Role;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class RoleAlgorithm extends BaseOpenmrsAlgorithm {

    private PrivilegeAlgorithm privilegeAlgorithm;

    public RoleAlgorithm() {
        this.privilegeAlgorithm = new PrivilegeAlgorithm();
    }

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Role role = new Role();
        role.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        role.setName(JsonUtils.readAsString(serialized, "$['name']"));
        List<Object> privilegeObjectArray = JsonUtils.readAsObjectList(serialized, "$['privileges']");
        for (Object privilegeObject : privilegeObjectArray) {
            role.add((Privilege) privilegeAlgorithm.deserialize(String.valueOf(privilegeObject)));
        }
        return role;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Role role = (Role) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", role.getUuid());
        JsonUtils.writeAsString(jsonObject, "name", role.getName());
        JSONArray privilegeArray = new JSONArray();
        for (Privilege privilege : role.getPrivileges()) {
            String privilegeString = privilegeAlgorithm.serialize(privilege);
            privilegeArray.add(JsonPath.read(privilegeString, "$"));
        }
        jsonObject.put("privileges", privilegeArray);
        return jsonObject.toJSONString();
    }
}
