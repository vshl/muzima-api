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
import com.muzima.api.model.Patient;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.ISO8601Util;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;

public class PatientAlgorithm extends BaseOpenmrsAlgorithm {

    private Logger logger = LoggerFactory.getLogger(PatientAlgorithm.class.getSimpleName());

    /**
     * Implementation of this method will define how the observation will be serialized from the JSON representation.
     *
     * @param json the json representation
     * @return the concrete observation object
     */
    @Override
    public Searchable deserialize(final String json) throws IOException {

        Patient patient = new Patient();

        // get the full json object representation and then pass this around to the next JsonPath.read()
        // this should minimize the time for the subsequent read() call
        Object jsonObject = JsonPath.read(json, "$");

        String uuid = JsonPath.read(jsonObject, "$['uuid']");
        patient.setUuid(uuid);

        String givenName = JsonPath.read(jsonObject, "$['personName.givenName']");
        patient.setGivenName(givenName);

        String middleName = JsonPath.read(jsonObject, "$['personName.middleName']");
        patient.setMiddleName(middleName);

        String familyName = JsonPath.read(jsonObject, "$['personName.familyName']");
        patient.setFamilyName(familyName);

        String identifier = JsonPath.read(jsonObject, "$['patientIdentifier.identifier']");
        patient.setIdentifier(identifier);

        String gender = JsonPath.read(jsonObject, "$['gender']");
        patient.setGender(gender);

        String birthdate = JsonPath.read(jsonObject, "$['birthdate']");
        try {
            patient.setBirthdate(ISO8601Util.toCalendar(birthdate).getTime());
        } catch (ParseException e) {
            logger.error(this.getClass().getSimpleName(), "Unable to parse date data from json payload.", e);
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
        jsonObject.put("uuid", patient.getUuid());
        jsonObject.put("personName.givenName", patient.getGivenName());
        jsonObject.put("personName.middleName", patient.getMiddleName());
        jsonObject.put("personName.familyName", patient.getFamilyName());
        jsonObject.put("patientIdentifier.identifier", patient.getIdentifier());
        jsonObject.put("gender", patient.getGender());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(patient.getBirthdate());
        jsonObject.put("birthdate", ISO8601Util.fromCalendar(calendar));
        return jsonObject.toJSONString();
    }
}
