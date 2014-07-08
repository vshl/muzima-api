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
