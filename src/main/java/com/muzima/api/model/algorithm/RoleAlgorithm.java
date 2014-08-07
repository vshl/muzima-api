/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
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

    public static final String ROLE_REPRESENTATION = "(uuid,name,privileges:(name,uuid))";

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
