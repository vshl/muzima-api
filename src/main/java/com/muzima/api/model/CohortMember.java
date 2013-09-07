/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
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
