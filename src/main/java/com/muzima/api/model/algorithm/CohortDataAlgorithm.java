/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortData;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Patient;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class CohortDataAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String DYNAMIC_COHORT_DATA_REPRESENTATION =
            "(definition:" + CohortAlgorithm.COHORT_STANDARD_REPRESENTATION + "," +
                    "members:" + PatientAlgorithm.PATIENT_STANDARD_REPRESENTATION + ")";
    public static final String STATIC_COHORT_DATA_REPRESENTATION =
            "(cohort:" + CohortAlgorithm.COHORT_STANDARD_REPRESENTATION + "," +
                    "patient:" + PatientAlgorithm.PATIENT_STANDARD_REPRESENTATION + ")";

    private final Logger logger = LoggerFactory.getLogger(CohortDataAlgorithm.class.getSimpleName());
    private CohortAlgorithm cohortAlgorithm;
    private PatientAlgorithm patientAlgorithm;

    public CohortDataAlgorithm() {
        this.cohortAlgorithm = new CohortAlgorithm();
        this.patientAlgorithm = new PatientAlgorithm();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>"+STATIC_COHORT_DATA_REPRESENTATION);
    }

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        CohortData cohortData = new CohortData();
        try {
            processStaticCohortDataObject(cohortData, serialized);
        } catch (InvalidPathException invalidStaticCohortException) {
            try {
                processDynamicCohortDataObject(cohortData, serialized);
            } catch (InvalidPathException invalidDynamicCohortException) {
                logger.error("Unable to tell if the data is dynamic or static cohort!", invalidDynamicCohortException);
            }
        }
        return cohortData;
    }

    private void processStaticCohortDataObject(final CohortData cohortData, final String serialized) throws IOException {
        Cohort cohort = new Cohort();
        List<Object> cohortObjects = JsonPath.read(serialized, "$['results'][*]['cohort']");
        for (Object cohortObject : cohortObjects) {
            cohort = (Cohort) cohortAlgorithm.deserialize(String.valueOf(cohortObject));
            if (!StringUtil.isEmpty(cohort.getUuid()) && !StringUtil.isEmpty(cohort.getName())) {
                break;
            }
        }
        cohort.setDynamic(false);
        cohortData.setCohort(cohort);

        List<Object> patientObjects = JsonPath.read(serialized, "$['results'][*]['patient']");
        for (Object patientObject : patientObjects) {
            Patient patient = (Patient) patientAlgorithm.deserialize(String.valueOf(patientObject));
            cohortData.addCohortMember(new CohortMember(cohort, patient));
            cohortData.addPatient(patient);
        }
    }

    private void processDynamicCohortDataObject(final CohortData cohortData, final String serialized) throws IOException {
        Object definitionObject = JsonPath.read(serialized, "$['definition']");
        Cohort cohort = (Cohort) cohortAlgorithm.deserialize(String.valueOf(definitionObject));
        cohort.setDynamic(true);
        cohortData.setCohort(cohort);

        List<Object> patientObjects = JsonPath.read(serialized, "$['members']");
        for (Object patientObject : patientObjects) {
            Patient patient = (Patient) patientAlgorithm.deserialize(String.valueOf(patientObject));
            cohortData.addCohortMember(new CohortMember(cohort, patient));
            cohortData.addPatient(patient);
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
