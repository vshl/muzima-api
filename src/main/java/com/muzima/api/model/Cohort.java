/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

/**
 * Cohort is a structure to hold collection of patients. Cohort will have a one to one connection with a Member object
 * where we can find the uuid of patients in the cohort.
 */
public class Cohort extends OpenmrsSearchable {

    private boolean dynamic;

    private boolean voided;

    private String name;

    /**
     * Flag whether the cohort is coming from dynamic or static data.
     *
     * @return true if the cohort is dynamic.
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * Set whether the cohort is dynamic or static.
     *
     * @param dynamic the flag for the cohort.
     */
    public void setDynamic(final boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isVoided() {
        return voided;
    }

    public void setVoided(final boolean voided) {
        this.voided = voided;
    }

    /**
     * Get the name for the cohort.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the cohort.
     *
     * @param name the name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }
}
