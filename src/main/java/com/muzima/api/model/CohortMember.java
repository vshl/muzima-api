/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import com.muzima.search.api.util.StringUtil;

/**
 * The Member class will reference to uuid of all patients for which the Member is associated with.
 * <br/>
 * This class is an exception from all of the other model classes where it's a local object but have the name similar
 * with the same remote resource. This class must not be associated with the <code>CohortMemberResolver</code>.
 */
public class CohortMember extends OpenmrsSearchable {

    private Cohort cohort;

    private Patient patient;

    public CohortMember() {
    }

    public CohortMember(final Cohort cohort, final Patient patient) {
        this.cohort = cohort;
        this.patient = patient;
    }

    /**
     * Get the cohort for this cohort member.
     *
     * @return the cohort.
     */
    public Cohort getCohort() {
        return cohort;
    }

    /**
     * Set the cohort for this cohort member.
     *
     * @param cohort the cohort.
     */
    public void setCohort(final Cohort cohort) {
        this.cohort = cohort;
    }

    /**
     * Get the patient for this cohort member.
     *
     * @return the patient.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Set the patient for this cohort member.
     *
     * @param patient the patient.
     */
    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    /**
     * Get the cohort uuid associated with this cohort member.
     *
     * @return the cohort uuid associated with this cohort member.
     */
    public String getCohortUuid() {
        String cohortUuid = StringUtil.EMPTY;
        if (cohort != null) {
            cohortUuid = cohort.getUuid();
        }
        return cohortUuid;
    }

    /**
     * Set the cohort uuid associated with this cohort member.
     *
     * @param cohortUuid the cohort uuid associated with this cohort member.
     */
    public void setCohortUuid(final String cohortUuid) {
        if (cohort != null) {
            cohort.setUuid(cohortUuid);
        }
    }

    /**
     * Get the uuid of the patient.
     *
     * @return the uuid of the patient.
     */
    public String getPatientUuid() {
        String patientUuid = StringUtil.EMPTY;
        if (patient != null) {
            patientUuid = patient.getUuid();
        }
        return patientUuid;
    }

    /**
     * Set the uuid of the patient.
     *
     * @param patientUuid the the uuid of the patient.
     */
    public void setPatientUuid(final String patientUuid) {
        if (patient != null) {
            patient.setUuid(patientUuid);
        }
    }
}
