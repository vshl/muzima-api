/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Credential;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.model.serialization.Algorithm;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class CredentialAlgorithm implements Algorithm {

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param json the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String json) throws IOException {
        Credential user = new Credential();

        Object jsonObject = JsonPath.read(json, "$");

        String uuid = JsonPath.read(jsonObject, "$['uuid']");
        user.setUuid(uuid);

        String userUuid = JsonPath.read(jsonObject, "$['user.uuid']");
        user.setUserUuid(userUuid);

        String username = JsonPath.read(jsonObject, "$['username']");
        user.setUsername(username);

        String password = JsonPath.read(jsonObject, "$['password']");
        user.setPassword(password);

        String seed = JsonPath.read(jsonObject, "$['salt']");
        user.setSalt(seed);

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
        Credential credential = (Credential) object;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", credential.getUuid());
        jsonObject.put("user.uuid", credential.getUserUuid());
        jsonObject.put("username", credential.getUsername());
        jsonObject.put("password", credential.getPassword());
        jsonObject.put("salt", credential.getSalt());
        return jsonObject.toJSONString();
    }
}
