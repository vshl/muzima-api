/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

public class PatientIdentifier extends OpenmrsSearchable {

    private boolean preferred;

    private String identifier;

    private PatientIdentifierType identifierType;

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(final boolean preferred) {
        this.preferred = preferred;
    }

    /**
     * Get the patient identifier
     *
     * @return the patient identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Set the patient identifier
     *
     * @param identifier the patient identifier
     */
    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public PatientIdentifierType getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(final PatientIdentifierType identifierType) {
        this.identifierType = identifierType;
    }
}
