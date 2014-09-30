/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String USER_REPRESENTATION =
            "(uuid,person:" + PersonAlgorithm.PERSON_STANDARD_REPRESENTATION + "," +
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
        user.setUsername(username);

        String systemId = JsonUtils.readAsString(json, "$['systemId']");
        user.setSystemId(systemId);

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
        jsonObject.put("systemId", user.getSystemId());

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

        jsonObject.put("person", personAlgorithm.serialize(user.getPerson()));

        return jsonObject.toJSONString();
    }
}
