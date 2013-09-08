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
import com.muzima.api.model.Concept;
import com.muzima.api.model.ConceptName;
import com.muzima.api.model.ConceptType;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class ConceptAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String CONCEPT_SIMPLE_REPRESENTATION = "(uuid)";
    public static final String CONCEPT_STANDARD_REPRESENTATION =
            "(uuid," +
                    "datatype:" + ConceptTypeAlgorithm.CONCEPT_TYPE_STANDARD_REPRESENTATION + "," +
                    "names:" + ConceptNameAlgorithm.CONCEPT_NAME_STANDARD_REPRESENTATION + ")";
    public static final String CONCEPT_NUMERIC_STANDARD_REPRESENTATION =
            "(uuid,units," +
                    "datatype:" + ConceptTypeAlgorithm.CONCEPT_TYPE_STANDARD_REPRESENTATION + "," +
                    "names:" + ConceptNameAlgorithm.CONCEPT_NAME_STANDARD_REPRESENTATION + ")";

    private ConceptTypeAlgorithm conceptTypeAlgorithm;
    private ConceptNameAlgorithm conceptNameAlgorithm;

    public ConceptAlgorithm() {
        this.conceptTypeAlgorithm = new ConceptTypeAlgorithm();
        this.conceptNameAlgorithm = new ConceptNameAlgorithm();
    }

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Concept concept = new Concept();
        Object jsonObject = JsonPath.read(serialized, "$");
        concept.setUuid(JsonUtils.readAsString(jsonObject, "$['uuid']"));
        concept.setUnit(JsonUtils.readAsString(jsonObject, "$['units']"));
        Object conceptTypeObject = JsonPath.read(jsonObject, "$['datatype']");
        concept.setConceptType((ConceptType) conceptTypeAlgorithm.deserialize(conceptTypeObject.toString()));
        List<Object> conceptNameObjects = JsonPath.read(jsonObject, "$['names']");
        for (Object conceptNameObject : conceptNameObjects) {
            concept.addName((ConceptName) conceptNameAlgorithm.deserialize(conceptNameObject.toString()));
        }
        return concept;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Concept concept = (Concept) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", concept.getUuid());
        JsonUtils.writeAsString(jsonObject, "units", concept.getUnit());
        String conceptType = conceptTypeAlgorithm.serialize(concept.getConceptType());
        jsonObject.put("datatype", JsonPath.read(conceptType, "$"));
        JSONArray jsonArray = new JSONArray();
        for (ConceptName conceptName : concept.getConceptNames()) {
            String name = conceptNameAlgorithm.serialize(conceptName);
            jsonArray.add(JsonPath.read(name, "$"));
        }
        jsonObject.put("names", jsonArray);
        return jsonObject.toJSONString();
    }
}
