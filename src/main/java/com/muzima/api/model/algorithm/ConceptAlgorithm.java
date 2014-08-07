/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
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
                    "names:" + ConceptNameAlgorithm.CONCEPT_NAME_STANDARD_REPRESENTATION + ",uuid)";
    public static final String CONCEPT_NUMERIC_STANDARD_REPRESENTATION =
            "(uuid,units,precise," +
                    "datatype:" + ConceptTypeAlgorithm.CONCEPT_TYPE_STANDARD_REPRESENTATION + "," +
                    "names:" + ConceptNameAlgorithm.CONCEPT_NAME_STANDARD_REPRESENTATION + ",uuid)";

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
        concept.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        concept.setUnit(JsonUtils.readAsString(serialized, "$['units']"));
        concept.setPrecise(JsonUtils.readAsBoolean(serialized, "$['precise']"));
        Object conceptTypeObject = JsonUtils.readAsObject(serialized, "$['datatype']");
        concept.setConceptType((ConceptType) conceptTypeAlgorithm.deserialize(String.valueOf(conceptTypeObject)));
        List<Object> conceptNameObjects = JsonUtils.readAsObjectList(serialized, "$['names']");
        for (Object conceptNameObject : conceptNameObjects) {
            concept.addName((ConceptName) conceptNameAlgorithm.deserialize(String.valueOf(conceptNameObject)));
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
        JsonUtils.writeAsBoolean(jsonObject, "precise", concept.isPrecise());
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
