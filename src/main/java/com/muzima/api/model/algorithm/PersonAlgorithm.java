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
import com.muzima.api.model.PersonName;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class PersonAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String PERSON_SIMPLE_REPRESENTATION = "(uuid,uuid)";
    public static final String PERSON_STANDARD_REPRESENTATION =
            "(uuid,gender,birthdate,names:" + PersonNameAlgorithm.PERSON_NAME_REPRESENTATION + ",uuid)";
    private PersonNameAlgorithm personNameAlgorithm;

    public PersonAlgorithm() {
        personNameAlgorithm = new PersonNameAlgorithm();
    }

    /**
     * Implementation of this method will define how the observation will be serialized from the JSON representation.
     *
     * @param serialized the json representation
     * @return the concrete observation object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Person person = new Person();
        person.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        person.setGender(JsonUtils.readAsString(serialized, "$['gender']"));
        person.setBirthdate(JsonUtils.readAsDate(serialized, "$['birthdate']"));
        List<Object> personNameObjects = JsonUtils.readAsObjectList(serialized, "$['names']");
        for (Object personNameObject : personNameObjects) {
            person.addName((PersonName) personNameAlgorithm.deserialize(String.valueOf(personNameObject)));
        }
        return person;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Person person = (Person) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", person.getUuid());
        JsonUtils.writeAsString(jsonObject, "gender", person.getGender());
        JsonUtils.writeAsDate(jsonObject, "birthdate", person.getBirthdate());
        JSONArray nameArray = new JSONArray();
        for (PersonName personName : person.getNames()) {
            String name = personNameAlgorithm.serialize(personName);
            nameArray.add(JsonPath.read(name, "$"));
        }
        jsonObject.put("names", nameArray);
        return jsonObject.toJSONString();
    }
}
