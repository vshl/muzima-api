/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muzima.api.model;

/**
 * TODO: Write brief description about the class here.
 */
public class PersonName extends OpenmrsSearchable {

    private boolean preferred;

    private String givenName;

    private String middleName;

    private String familyName;

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(final boolean preferred) {
        this.preferred = preferred;
    }

    /**
     * Get the given name for the patient.
     *
     * @return the given name for the patient.
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Set the given name for the patient.
     *
     * @param givenName the given name for the patient.
     */
    public void setGivenName(final String givenName) {
        this.givenName = givenName;
    }

    /**
     * Get the middle name for the patient.
     *
     * @return the middle name for the patient.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Set the middle name for the patient.
     *
     * @param middleName the middle name for the patient.
     */
    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    /**
     * Get the family name for the patient.
     *
     * @return the family name for the patient.
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Set the family name for the patient.
     *
     * @param familyName the family name for the patient.
     */
    public void setFamilyName(final String familyName) {
        this.familyName = familyName;
    }
}
