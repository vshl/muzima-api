/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Patient;
import com.muzima.api.model.PatientIdentifier;
import com.muzima.api.model.PersonAttribute;
import com.muzima.api.model.PersonName;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class PatientAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String PATIENT_SIMPLE_REPRESENTATION = "(uuid)";
    public static final String PATIENT_STANDARD_REPRESENTATION =
            "(uuid,voided,gender,birthdate," +
                    "names:" + PersonNameAlgorithm.PERSON_NAME_REPRESENTATION + "," +
                    "identifiers:" + PatientIdentifierAlgorithm.PATIENT_IDENTIFIER_REPRESENTATION + "," +
                    "attributes:" + PersonAttributeAlgorithm.PERSON_ATTRIBUTE_REPRESENTATION + ",)";
    private PersonNameAlgorithm personNameAlgorithm;
    private PatientIdentifierAlgorithm patientIdentifierAlgorithm;
    private PersonAttributeAlgorithm personAttributeAlgorithm;

    public PatientAlgorithm() {
        this.personNameAlgorithm = new PersonNameAlgorithm();
        this.patientIdentifierAlgorithm = new PatientIdentifierAlgorithm();
        this.personAttributeAlgorithm = new PersonAttributeAlgorithm();
    }

    /*
    * Implementation of this method will define how the observation will be serialized from the JSON representation.
    *
    * @param serialized the json representation
    * @return the concrete observation object
    */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Patient patient = new Patient();
        patient.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        patient.setVoided(JsonUtils.readAsBoolean(serialized, "$['voided']"));
        patient.setGender(JsonUtils.readAsString(serialized, "$['gender']"));
        patient.setBirthdate(JsonUtils.readAsDate(serialized, "$['birthdate']"));
        List<Object> personNameObjects = JsonUtils.readAsObjectList(serialized, "$['names']");
        for (Object personNameObject : personNameObjects) {
            patient.addName((PersonName) personNameAlgorithm.deserialize(String.valueOf(personNameObject)));
        }
        List<Object> identifierObjects = JsonUtils.readAsObjectList(serialized, "$['identifiers']");
        for (Object identifierObject : identifierObjects) {
            patient.addIdentifier(
                    (PatientIdentifier) patientIdentifierAlgorithm.deserialize(String.valueOf(identifierObject)));
        }
        List<Object> attributesObjects = JsonUtils.readAsObjectList(serialized, "$['attributes']");
        for (Object attributeObject : attributesObjects) {
            patient.addattribute(
                    (PersonAttribute) personAttributeAlgorithm.deserialize(String.valueOf(attributeObject)));
        }
        return patient;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Patient patient = (Patient) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", patient.getUuid());
        JsonUtils.writeAsBoolean(jsonObject, "voided", patient.isVoided());
        JsonUtils.writeAsString(jsonObject, "gender", patient.getGender());
        JsonUtils.writeAsDate(jsonObject, "birthdate", patient.getBirthdate());
        JSONArray nameArray = new JSONArray();
        for (PersonName personName : patient.getNames()) {
            String name = personNameAlgorithm.serialize(personName);
            nameArray.add(JsonPath.read(name, "$"));
        }
        jsonObject.put("names", nameArray);
        JSONArray identifierArray = new JSONArray();
        for (PatientIdentifier identifier : patient.getIdentifiers()) {
            String name = patientIdentifierAlgorithm.serialize(identifier);
            identifierArray.add(JsonPath.read(name, "$"));
        }
        jsonObject.put("identifiers", identifierArray);
        JSONArray attributeArray = new JSONArray();
        for (PersonAttribute attribute : patient.getAtributes()) {
            String name = personAttributeAlgorithm.serialize(attribute);
            attributeArray.add(JsonPath.read(name, "$"));
        }
        jsonObject.put("attributes", attributeArray);
        return jsonObject.toJSONString();
    }
}
