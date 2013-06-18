package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortData;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Patient;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.ISO8601Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class CohortDataAlgorithm extends BaseOpenmrsAlgorithm {

    private final Logger logger = LoggerFactory.getLogger(CohortDataAlgorithm.class.getSimpleName());

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        CohortData cohortData = new CohortData();
        Object cohortDataObject = JsonPath.read(serialized, "$");
        try {
            cohortData.setDynamic(false);
            processStaticCohortDataObject(cohortData, cohortDataObject);
        } catch (InvalidPathException invalidStaticCohortException) {
            try {
                cohortData.setDynamic(true);
                processDynamicCohortDataObject(cohortData, cohortDataObject);
            } catch (InvalidPathException invalidDynamicCohortException) {
                logger.error("Unable to tell if the data is dynamic or static cohort!", invalidDynamicCohortException);
            }
        }
        return cohortData;
    }

    private void processStaticCohortDataObject(final CohortData cohortData, final Object serialized) {

        Cohort cohort = new Cohort();
        Object cohortObject = JsonPath.read(serialized, "$['results'][0]['cohort']");

        String cohortUuid = JsonPath.read(cohortObject, "$['uuid']");
        cohort.setUuid(cohortUuid);

        String cohortName = JsonPath.read(cohortObject, "$['name']");
        cohort.setName(cohortName);

        cohortData.setCohort(cohort);

        List<Object> patientObjectArray = JsonPath.read(serialized, "$['results'][*]['patient']");
        for (Object patientObject : patientObjectArray) {
            Patient patient = new Patient();

            String patientUuid = JsonPath.read(patientObject, "$['uuid']");
            patient.setUuid(patientUuid);

            String givenName = JsonPath.read(patientObject, "$['personName.givenName']");
            patient.setGivenName(givenName);

            String middleName = JsonPath.read(patientObject, "$['personName.middleName']");
            patient.setMiddleName(middleName);

            String familyName = JsonPath.read(patientObject, "$['personName.familyName']");
            patient.setFamilyName(familyName);

            String identifier = JsonPath.read(patientObject, "$['patientIdentifier.identifier']");
            patient.setIdentifier(identifier);

            String gender = JsonPath.read(patientObject, "$['gender']");
            patient.setGender(gender);

            String birthdate = JsonPath.read(patientObject, "$['birthdate']");
            try {
                patient.setBirthdate(ISO8601Util.toCalendar(birthdate).getTime());
            } catch (ParseException e) {
                logger.error(this.getClass().getSimpleName(), "Unable to parse date data from json payload.", e);
            }
            cohortData.addPatient(patient);

            CohortMember cohortMember = new CohortMember();
            cohortMember.setPatientUuid(patientUuid);
            cohortMember.setCohortUuid(cohortUuid);
            cohortData.addCohortMember(cohortMember);
        }
    }

    private void processDynamicCohortDataObject(final CohortData cohortData, final Object serialized) {
        Cohort cohort = new Cohort();

        String cohortUuid = JsonPath.read(serialized, "$['definition.uuid']");
        cohort.setUuid(cohortUuid);

        String cohortName = JsonPath.read(serialized, "$['definition.name']");
        cohort.setName(cohortName);

        cohortData.setCohort(cohort);

        List<Object> patientObjectArray = JsonPath.read(serialized, "$['members']");
        for (Object patientObject : patientObjectArray) {
            Patient patient = new Patient();

            String patientUuid = JsonPath.read(patientObject, "$['uuid']");
            patient.setUuid(patientUuid);

            String givenName = JsonPath.read(patientObject, "$['personName.givenName']");
            patient.setGivenName(givenName);

            String middleName = JsonPath.read(patientObject, "$['personName.middleName']");
            patient.setMiddleName(middleName);

            String familyName = JsonPath.read(patientObject, "$['personName.familyName']");
            patient.setFamilyName(familyName);

            String identifier = JsonPath.read(patientObject, "$['patientIdentifier.identifier']");
            patient.setIdentifier(identifier);

            String gender = JsonPath.read(patientObject, "$['gender']");
            patient.setGender(gender);

            String birthdate = JsonPath.read(patientObject, "$['birthdate']");
            try {
                patient.setBirthdate(ISO8601Util.toCalendar(birthdate).getTime());
            } catch (ParseException e) {
                logger.error(this.getClass().getSimpleName(), "Unable to parse date data from json payload.", e);
            }
            cohortData.addPatient(patient);

            CohortMember cohortMember = new CohortMember();
            cohortMember.setPatientUuid(patientUuid);
            cohortMember.setCohortUuid(cohortUuid);
            cohortData.addCohortMember(cohortMember);
        }
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        throw new IOException("Serializing the cohort data object is not supported right now!");
    }
}
