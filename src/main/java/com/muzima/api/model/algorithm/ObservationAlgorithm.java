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

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Concept;
import com.muzima.api.model.Encounter;
import com.muzima.api.model.Observation;
import com.muzima.api.model.Person;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.ISO8601Util;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Calendar;

public class ObservationAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String NON_CODED_OBSERVATION_REPRESENTATION =
            "(uuid,obsDatetime,valueText,valueNumeric,valueDatetime,valueCoded," +
                    "encounter:" + EncounterAlgorithm.ENCOUNTER_SIMPLE_REPRESENTATION + "," +
                    "person:" + PersonAlgorithm.PERSON_SIMPLE_REPRESENTATION + "," +
                    "concept:" + ConceptAlgorithm.CONCEPT_SIMPLE_REPRESENTATION + ")";
    public static final String CODED_OBSERVATION_REPRESENTATION =
            "(uuid,obsDatetime,valueText,valueNumeric,valueDatetime," +
                    "valueCoded:" + ConceptAlgorithm.CONCEPT_STANDARD_REPRESENTATION + "," +
                    "encounter:" + EncounterAlgorithm.ENCOUNTER_SIMPLE_REPRESENTATION + "," +
                    "person:" + PersonAlgorithm.PERSON_SIMPLE_REPRESENTATION + "," +
                    "concept:" + ConceptAlgorithm.CONCEPT_SIMPLE_REPRESENTATION + ")";

    private Logger logger = LoggerFactory.getLogger(ObservationAlgorithm.class.getSimpleName());
    private PersonAlgorithm personAlgorithm;
    private ConceptAlgorithm conceptAlgorithm;
    private EncounterAlgorithm encounterAlgorithm;

    public ObservationAlgorithm() {
        this.personAlgorithm = new PersonAlgorithm();
        this.conceptAlgorithm = new ConceptAlgorithm();
        this.encounterAlgorithm = new EncounterAlgorithm();
    }

    /**
     * Implementation of this method will define how the observation will be serialized from the JSON representation.
     *
     * @param serialized the json representation
     * @return the concrete observation object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Observation observation = new Observation();
        observation.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        observation.setObservationDatetime(JsonUtils.readAsDate(serialized, "$['obsDatetime']"));
        // values, ignored when they are not exists in the resource
        observation.setValueText(JsonUtils.readAsString(serialized, "$['valueText']"));
        observation.setValueNumeric(JsonUtils.readAsNumeric(serialized, "$['valueNumeric']"));
        observation.setValueDatetime(JsonUtils.readAsDate(serialized, "$['valueDatetime']"));
        // value coded need to be handled separately because we can't create the custom structure of value coded!
        Object valueCodedObject = JsonUtils.readAsObject(serialized, "$['valueCoded']");
        observation.setValueCoded((Concept) conceptAlgorithm.deserialize(String.valueOf(valueCodedObject)));
        // some observation might not have the encounter associated with it!
        Object encounterObject = JsonUtils.readAsObject(serialized, "$['encounter']");
        observation.setEncounter((Encounter) encounterAlgorithm.deserialize(String.valueOf(encounterObject)));
        Object conceptObject = JsonUtils.readAsObject(serialized, "$['concept']");
        observation.setConcept((Concept) conceptAlgorithm.deserialize(String.valueOf(conceptObject)));
        Object personObject = JsonUtils.readAsObject(serialized, "$['person']");
        observation.setPerson((Person) personAlgorithm.deserialize(String.valueOf(personObject)));
        return observation;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Observation observation = (Observation) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", observation.getUuid());
        JsonUtils.writeAsDate(jsonObject, "obsDatetime", observation.getObservationDatetime());
        JsonUtils.writeAsString(jsonObject, "valueText", observation.getValueText());
        JsonUtils.writeAsNumeric(jsonObject, "valueNumeric", observation.getValueNumeric());
        JsonUtils.writeAsDate(jsonObject, "valueDatetime", observation.getValueDatetime());
        String valueCoded = conceptAlgorithm.serialize(observation.getValueCoded());
        jsonObject.put("valueCoded", JsonPath.read(valueCoded, "$"));
        String encounter = encounterAlgorithm.serialize(observation.getEncounter());
        jsonObject.put("encounter", JsonPath.read(encounter, "$"));
        String concept = conceptAlgorithm.serialize(observation.getConcept());
        jsonObject.put("concept", JsonPath.read(concept, "$"));
        String person = personAlgorithm.serialize(observation.getPerson());
        jsonObject.put("person", JsonPath.read(person, "$"));
        return jsonObject.toJSONString();
    }
}
