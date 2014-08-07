/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class Role extends OpenmrsSearchable {

    private String name;

    private List<Privilege> privileges;

    /**
     * Get the name of the role.
     *
     * @return the name of the role.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the role.
     *
     * @param name the name of the role.
     */
    public void setName(String name) {
        this.name = name;
    }

    public void add(final Privilege privilege) {
        getPrivileges().add(privilege);
    }

    /**
     * Get all privileges under this role.
     *
     * @return all privileges under this role.
     */
    public List<Privilege> getPrivileges() {
        if (privileges == null) {
            privileges = new ArrayList<Privilege>();
        }
        return privileges;
    }

    /**
     * Set all privileges under this role.
     *
     * @param privileges all privileges under this role.
     */
    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}
