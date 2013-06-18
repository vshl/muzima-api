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
import com.muzima.api.model.User;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.StringUtil;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAlgorithm extends BaseOpenmrsAlgorithm {

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param json the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String json) throws IOException {
        User user = new User();

        Object jsonObject = JsonPath.read(json, "$");

        String uuid = JsonPath.read(jsonObject, "$['uuid']");
        user.setUuid(uuid);

        String givenName = JsonPath.read(jsonObject, "$['person.personName.givenName']");
        user.setGivenName(givenName);

        String middleName = JsonPath.read(jsonObject, "$['person.personName.middleName']");
        user.setMiddleName(middleName);

        String familyName = JsonPath.read(jsonObject, "$['person.personName.familyName']");
        user.setFamilyName(familyName);

        String username;
        username = JsonPath.read(jsonObject, "$['username']");
        if (StringUtil.isEmpty(username))
            username = JsonPath.read(jsonObject, "$['systemId']");
        user.setUsername(username);

        List<Object> privilegeObjectArray = JsonPath.read(jsonObject, "$['privileges']");
        List<Privilege> privileges = new ArrayList<Privilege>();
        for (Object privilegeObject : privilegeObjectArray) {
            Privilege privilege = new Privilege();

            String privilegeUuid = JsonPath.read(privilegeObject, "$['uuid']");
            privilege.setUuid(privilegeUuid);

            String privilegeName = JsonPath.read(privilegeObject, "$['name']");
            privilege.setName(privilegeName);

            privileges.add(privilege);
        }
        user.setPrivileges(privileges);

        List<Object> roleObjectArray = JsonPath.read(jsonObject, "$['roles']");
        List<Role> roles = new ArrayList<Role>();
        for (Object roleObject : roleObjectArray) {
            Role role = new Role();

            String privilegeUuid = JsonPath.read(roleObject, "$['uuid']");
            role.setUuid(privilegeUuid);

            String privilegeName = JsonPath.read(roleObject, "$['name']");
            role.setName(privilegeName);

            roles.add(role);
        }
        user.setRoles(roles);

        return user;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        User user = (User) object;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", user.getUuid());
        jsonObject.put("person.personName.givenName", user.getGivenName());
        jsonObject.put("person.personName.middleName", user.getMiddleName());
        jsonObject.put("person.personName.familyName", user.getFamilyName());
        jsonObject.put("username", user.getUsername());
        jsonObject.put("systemId", user.getUsername());

        JSONArray privilegeObjectArray = new JSONArray();
        for (Privilege privilege : user.getPrivileges()) {
            JSONObject privilegeObject = new JSONObject();
            privilegeObject.put("uuid", privilege.getUuid());
            privilegeObject.put("name", privilege.getName());
            privilegeObjectArray.add(privilegeObject);
        }
        jsonObject.put("privileges", privilegeObjectArray);

        JSONArray roleObjectArray = new JSONArray();
        for (Role role : user.getRoles()) {
            JSONObject roleObject = new JSONObject();
            roleObject.put("uuid", role.getUuid());
            roleObject.put("name", role.getName());
            roleObjectArray.add(roleObject);
        }
        jsonObject.put("roles", roleObjectArray);

        return jsonObject.toJSONString();
    }
}
