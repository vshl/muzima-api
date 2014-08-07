/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import com.muzima.search.api.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class Person extends OpenmrsSearchable {

    public static final String DISPLAY_NAME_FOR_ENCOUNTER_FOR_OBSERVATIONS_WITH_NULL_UUID = "";

    private String gender;

    private Date birthdate;

    private List<PersonName> names;

    private boolean voided;

    /**
     * Get the patient gender
     *
     * @return the patient gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set the patient gender
     *
     * @param gender the patient gender
     */
    public void setGender(final String gender) {
        this.gender = gender;
    }

    /**
     * Get the patient birthdate
     *
     * @return the birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Set the patient birthdate
     *
     * @param birthdate the patient birthdate
     */
    public void setBirthdate(final Date birthdate) {
        this.birthdate = birthdate;
    }

    public void addName(final PersonName personName) {
        getNames().add(personName);
    }

    public List<PersonName> getNames() {
        if (names == null) {
            names = new ArrayList<PersonName>();
        }
        return names;
    }

    public void setNames(final List<PersonName> names) {
        this.names = names;
    }

    /**
     * Get the given name for the patient.
     *
     * @return the given name for the patient.
     */
    public String getGivenName() {
        String givenName = StringUtil.EMPTY;
        for (PersonName name : getNames()) {
            givenName = name.getGivenName();
            if (name.isPreferred()) {
                return givenName;
            }
        }
        return givenName;
    }

    /**
     * Get the middle name for the patient.
     *
     * @return the middle name for the patient.
     */
    public String getMiddleName() {
        String middleName = StringUtil.EMPTY;
        for (PersonName name : getNames()) {
            middleName = name.getMiddleName();
            if (name.isPreferred()) {
                return middleName;
            }
        }
        return middleName;
    }

    /**
     * Get the family name for the patient.
     *
     * @return the family name for the patient.
     */
    public String getFamilyName() {
        String familyName = StringUtil.EMPTY;
        for (PersonName name : getNames()) {
            familyName = name.getFamilyName();
            if (name.isPreferred()) {
                return familyName;
            }
        }
        return familyName;
    }

    public String getDisplayName() {
        if (getFamilyName() == null && getGivenName() == null && getMiddleName() == null)
            return DISPLAY_NAME_FOR_ENCOUNTER_FOR_OBSERVATIONS_WITH_NULL_UUID;
        return getFamilyName() + ", " + getGivenName() + " " + getMiddleName();
    }

    public boolean isVoided() {
        return voided;
    }

    public void setVoided(final boolean voided) {
        this.voided = voided;
    }
}
