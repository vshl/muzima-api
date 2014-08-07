/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.model;

/**
 * A tag is a class to hold tag reference in the server. Each form can have multiple tags attached to it.
 */
public class Tag extends OpenmrsSearchable {

    private String name;

    /**
     * Get the name for the tag.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the tag.
     *
     * @param name the name to tag.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        if (getUuid() != null ? !getUuid().equals(tag.getUuid()) : tag.getUuid() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getUuid() != null ? getUuid().hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
