/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class Location extends OpenmrsSearchable implements Comparable<Location> {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


    @Override
    public int compareTo(Location location) {
        if (this.getName() != null && location.getName() != null) {
            return this.getName().toLowerCase().compareTo(location.getName().toLowerCase());
        }
        return 0;
    }

}
