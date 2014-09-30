/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import java.util.List;

public class User extends OpenmrsSearchable {

    private Person person;

    private String username;

    private List<Privilege> privileges;

    private List<Role> roles;

    private String systemId;

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    /**
     * Get the given name for the patient.
     *
     * @return the given name for the patient.
     */
    public String getGivenName() {
        return person.getGivenName();
    }

    /**
     * Get the middle name for the patient.
     *
     * @return the middle name for the patient.
     */
    public String getMiddleName() {
        return person.getMiddleName();
    }

    /**
     * Get the family name for the patient.
     *
     * @return the family name for the patient.
     */
    public String getFamilyName() {
        return person.getFamilyName();
    }

    /**
     * Get the username of the user.
     *
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     *
     * @param username the username of the user.
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Get the list of all privileges for the user.
     *
     * @return the list of all privileges for the user.
     */
    public List<Privilege> getPrivileges() {
        return privileges;
    }

    /**
     * Set the list of all privileges for the user.
     *
     * @param privileges the list of all privileges for the user.
     */
    public void setPrivileges(final List<Privilege> privileges) {
        this.privileges = privileges;
    }

    /**
     * Get the list of all roles for the user.
     *
     * @return the list of all roles for the user.
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Set the list of all roles for the user.
     *
     * @param roles the list of all roles for the user.
     */
    public void setRoles(final List<Role> roles) {
        this.roles = roles;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
