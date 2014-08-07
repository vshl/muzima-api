/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import com.muzima.search.api.model.object.Searchable;

/**
 * Credential is a class to hold and persist authentication information for a user locally. User will be allowed to
 * access the system when a credential record for that user is found in the local repository.
 */
public class Credential implements Searchable {

    private String uuid;

    private String userUuid;

    private String username;

    private String salt;

    private String password;

    /**
     * Get the uuid of the credential.
     *
     * @return the uuid of the credential.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Set the uuid of the credential.
     *
     * @param uuid the uuid of the credential.
     */
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the uuid of the user with this credential.
     *
     * @return the uuid of the user with this credential.
     */
    public String getUserUuid() {
        return userUuid;
    }

    /**
     * Set the uuid of the user with this credential.
     *
     * @param userUuid the uuid of the user with this credential.
     */
    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }

    /**
     * Get the username who own this credential.
     *
     * @return the username who own this credential.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username who own this credential.
     *
     * @param username the username who own this credential.
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Get the salt for hashing the password.
     *
     * @return the salt for hashing the password.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Set the salt for hashing the password.
     *
     * @param salt the salt for hashing the password.
     */
    public void setSalt(final String salt) {
        this.salt = salt;
    }

    /**
     * Get the hashed password.
     *
     * @return the hashed password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the hashed password.
     *
     * @param password the hashed password.
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
