/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class CohortData extends OpenmrsSearchable {

    private Cohort cohort;

    private List<CohortMember> cohortMembers;

    private List<Patient> patients;

    /**
     * Get the cohort for the cohort data.
     *
     * @return the cohort.
     */
    public Cohort getCohort() {
        return cohort;
    }

    /**
     * Set the cohort for the cohort data.
     *
     * @param cohort the cohort.
     */
    public void setCohort(final Cohort cohort) {
        this.cohort = cohort;
    }

    /**
     * Add a new member object into the cohort data object.
     *
     * @param cohortMember the new member object to be added to the cohort data.
     */
    public void addCohortMember(final CohortMember cohortMember) {
        getCohortMembers().add(cohortMember);
    }

    /**
     * Get the members for this cohort data object.
     *
     * @return the members for this cohort data object.
     */
    public List<CohortMember> getCohortMembers() {
        if (cohortMembers == null) {
            cohortMembers = new ArrayList<CohortMember>();
        }
        return cohortMembers;
    }

    /**
     * Set the members for this cohort data object.
     *
     * @param cohortMembers the members for this cohort data object.
     */
    public void setCohortMembers(final List<CohortMember> cohortMembers) {
        this.cohortMembers = cohortMembers;
    }

    /**
     * Add the new patient object into the cohort data object.
     *
     * @param patient the patient object to be added.
     */
    public void addPatient(final Patient patient) {
        getPatients().add(patient);
    }

    /**
     * Get the patients for this cohort data object.
     *
     * @return the patients for this cohort data object.
     */
    public List<Patient> getPatients() {
        if (patients == null) {
            patients = new ArrayList<Patient>();
        }
        return patients;
    }

    /**
     * Set the patients for this cohort data object.
     *
     * @param patients the patients for this cohort data object.
     */
    public void setPatients(final List<Patient> patients) {
        this.patients = patients;
    }
}
