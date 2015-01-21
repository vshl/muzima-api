/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.PersonAttribute;
import com.muzima.api.model.PersonAttributeType;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class PersonAttributeAlgorithm extends BaseOpenmrsAlgorithm {
    public static final String PERSON_ATTRIBUTE_REPRESENTATION = "(uuid,hydratedObject," +
            "attributeType:" + PersonAttributeTypeAlgorithm.PERSON_ATTRIBUTE_TYPE_REPRESENTATION + ",)";

    private PersonAttributeTypeAlgorithm personAttributeTypeAlgorithm;

    public PersonAttributeAlgorithm() {
        this.personAttributeTypeAlgorithm = new PersonAttributeTypeAlgorithm();
    }

    /**
     * Implementation of this method will define how the observation will be serialized from the JSON representation.
     *
     * @param serialized the json representation
     * @return the concrete observation object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        personAttribute.setAttribute(JsonUtils.readAsString(serialized, "$['hydratedObject']"));
        Object attributeTypeObject = JsonUtils.readAsObject(serialized, "$['attributeType']");
        PersonAttributeType attributeType =
                (PersonAttributeType) personAttributeTypeAlgorithm.deserialize(String.valueOf(attributeTypeObject));
        personAttribute.setAttributeType(attributeType);
        return personAttribute;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        PersonAttribute personAttribute = (PersonAttribute) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", personAttribute.getUuid());
        JsonUtils.writeAsString(jsonObject, "hydratedObject", personAttribute.getAttribute());
        String identifierType = personAttributeTypeAlgorithm.serialize(personAttribute.getAttributeType());
        jsonObject.put("attributeType", JsonPath.read(identifierType, "$"));
        return jsonObject.toJSONString();
    }
}
