package com.muzima.api.service;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Write brief description about the class here.
 */
public class CohortDataTest {

    private final Logger logger = LoggerFactory.getLogger(CohortDataTest.class.getSimpleName());

    @Test
    public void parseCohortData() throws Exception {

        JSONObject dynamicCohortDataJson = new JSONObject();
        dynamicCohortDataJson.put("definition.uuid", "8a3b7bbf-cefc-4ea9-8ff1-45b5a274a689");
        dynamicCohortDataJson.put("definition.name", "Example Age Query");

        JSONObject dynamicPatientJson = new JSONObject();
        dynamicPatientJson.put("uuid", "dd5580a4-1691-11df-97a5-7038c432aabf");
        dynamicPatientJson.put("gender", "M");
        dynamicPatientJson.put("birthdate", "1997-09-19T00:00:00.000-0500");
        dynamicPatientJson.put("personName.givenName", "Sammuel");
        dynamicPatientJson.put("personName.middleName", "Isadia");
        dynamicPatientJson.put("personName.familyName", "Jotham");
        dynamicPatientJson.put("patientIdentifier.identifier", "117CH-1");
        dynamicPatientJson.put("patientIdentifier.identifierType.name", "Old AMPATH Medical Record Number");

        JSONArray dynamicPatientArrayJson = new JSONArray();
        dynamicPatientArrayJson.add(dynamicPatientJson);
        dynamicCohortDataJson.put("members", dynamicPatientArrayJson);

        String dynamicCohortData = dynamicCohortDataJson.toJSONString();
        System.out.println(dynamicCohortData);

        JSONObject staticCohortDataJson = new JSONObject();
        JSONArray staticResultArrayJson = new JSONArray();

        JSONObject staticCohortJson = new JSONObject();
        staticCohortJson.put("uuid", "0ca78602-737f-408d-8ced-386ad12367db");
        staticCohortJson.put("name", "Male 19 - 23");

        JSONObject staticPatientJson = new JSONObject();
        staticPatientJson.put("uuid", "dd5580a4-1691-11df-97a5-7038c432aabf");
        staticPatientJson.put("gender", "M");
        staticPatientJson.put("birthdate", "1997-09-19T00:00:00.000-0500");
        staticPatientJson.put("personName.givenName", "Sammuel");
        staticPatientJson.put("personName.middleName", "Isadia");
        staticPatientJson.put("personName.familyName", "Jotham");
        staticPatientJson.put("patientIdentifier.identifier", "117CH-1");
        staticPatientJson.put("patientIdentifier.identifierType.name", "Old AMPATH Medical Record Number");

        JSONObject resultArrayElementJson = new JSONObject();
        resultArrayElementJson.put("cohort", staticCohortJson);
        resultArrayElementJson.put("patient", staticPatientJson);
        staticResultArrayJson.add(resultArrayElementJson);

        staticCohortDataJson.put("results", staticResultArrayJson);

        String staticCohortData = staticCohortDataJson.toJSONString();
        System.out.println(staticCohortData);

        Filter staticCohortDataFilter = Filter.filter(Criteria.where("results").exists(true));
        try {
            Object staticCohortDataObject = JsonPath.read(staticCohortData, "$", staticCohortDataFilter);
            System.out.println(staticCohortDataObject);
            Object dynamicCohortDataObject = JsonPath.read(dynamicCohortData, "$", staticCohortDataFilter);
            System.out.println(dynamicCohortDataObject);
        } catch (IllegalArgumentException e) {
            logger.info("Unable to understand the filter", e);
        }
    }
}
