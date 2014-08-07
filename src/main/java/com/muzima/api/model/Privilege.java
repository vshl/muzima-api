/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

/**
 * TODO: Write brief description about the class here.
 */
public class Privilege extends OpenmrsSearchable {

    private String name;

    /**
     * Get the name associated with this privilege.
     *
     * @return the name associated with this privilege.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name associated with this privilege.
     *
     * @param name the name associated with this privilege.
     */
    public void setName(String name) {
        this.name = name;
    }
}
