/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import com.muzima.search.api.model.object.Searchable;

import java.io.Serializable;

/**
 * Every OpenMRS object will have uri associated with it. This is not being used right now.
 */
public abstract class OpenmrsSearchable implements Searchable, Serializable {

    private String uri;

    private String uuid;

    /**
     * Get the openmrs object's uri.
     *
     * @return the uri;
     */
    public String getUri() {
        return uri;
    }

    /**
     * Set the openmrs object's uri.
     *
     * @param uri the uri.
     */
    public void setUri(final String uri) {
        this.uri = uri;
    }

    /**
     * Get the uuid for the object.
     *
     * @return the uuid.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Set the uuid for the object.
     *
     * @param uuid the uuid to set.
     */
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    /**
     * Hash code implementation will be based on the uuid value of the object.
     *
     * @return the hash code of the object.
     */
    public int hashCode() {
        if (getUuid() == null)
            return super.hashCode();
        return getUuid().hashCode();
    }

    /**
     * Object equality implementation will be based on the uuid of the object.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof OpenmrsSearchable))
            return false;
        OpenmrsSearchable other = (OpenmrsSearchable) obj;
        return getUuid() != null && getUuid().equals(other.getUuid());
    }
}
