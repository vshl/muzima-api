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

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.PersonAttribute;
import com.muzima.api.model.PersonAttributeType;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class PersonAttributeAlgorithm extends BaseOpenmrsAlgorithm {
    public static final String PERSON_ATTRIBUTE_REPRESENTATION = "(value," +
                    "attributeType:" + PersonAttributeTypeAlgorithm.PERSON_ATTRIBUTE_TYPE_REPRESENTATION  + ")";

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
        personAttribute.setValue(JsonUtils.readAsString(serialized, "$['value']"));
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
        JsonUtils.writeAsString(jsonObject, "value", personAttribute.getValue());
        String identifierType = personAttributeTypeAlgorithm.serialize(personAttribute.getAttributeType());
        jsonObject.put("attributeType", JsonPath.read(identifierType, "$"));
        return jsonObject.toJSONString();
    }
}
