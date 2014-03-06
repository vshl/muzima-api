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
import com.muzima.api.model.Person;
import com.muzima.api.model.Privilege;
import com.muzima.api.model.Role;
import com.muzima.api.model.User;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String USER_REPRESENTATION =
            "(uuid,person:" + PersonAlgorithm.PERSON_SIMPLE_REPRESENTATION + "," +
                    "username,systemId,roles:(uuid,name),privileges:(uuid,name))";

    private PersonAlgorithm personAlgorithm;
    private RoleAlgorithm roleAlgorithm;
    private PrivilegeAlgorithm privilegeAlgorithm;

    public UserAlgorithm() {
        this.personAlgorithm = new PersonAlgorithm();
        this.roleAlgorithm = new RoleAlgorithm();
        this.privilegeAlgorithm = new PrivilegeAlgorithm();
    }

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param json the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String json) throws IOException {
        User user = new User();
        user.setUuid(JsonUtils.readAsString(json, "$['uuid']"));
        // read the person object
        Object personObject = JsonUtils.readAsObject(json, "$['person']");
        user.setPerson((Person) personAlgorithm.deserialize(String.valueOf(personObject)));

        String username;
        username = JsonUtils.readAsString(json, "$['username']");
        if (StringUtil.isEmpty(username))
            username = JsonUtils.readAsString(json, "$['systemId']");
        user.setUsername(username);

        List<Object> privilegeObjectArray = JsonUtils.readAsObjectList(json, "$['privileges']");
        List<Privilege> privileges = new ArrayList<Privilege>();
        for (Object privilegeObject : privilegeObjectArray) {
            privileges.add((Privilege) privilegeAlgorithm.deserialize(String.valueOf(privilegeObject)));
        }
        user.setPrivileges(privileges);

        List<Object> roleObjectArray = JsonUtils.readAsObjectList(json, "$['roles']");
        List<Role> roles = new ArrayList<Role>();
        for (Object roleObject : roleObjectArray) {
            roles.add((Role) roleAlgorithm.deserialize(String.valueOf(roleObject)));
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

        JSONArray privilegeArray = new JSONArray();
        for (Privilege privilege : user.getPrivileges()) {
            String privilegeString = privilegeAlgorithm.serialize(privilege);
            privilegeArray.add(JsonPath.read(privilegeString, "$"));
        }
        jsonObject.put("privileges", privilegeArray);

        JSONArray roleArray = new JSONArray();
        for (Role role : user.getRoles()) {
            String roleString = roleAlgorithm.serialize(role);
            roleArray.add(JsonPath.read(roleString, "$"));
        }
        jsonObject.put("roles", roleArray);

        return jsonObject.toJSONString();
    }
}
