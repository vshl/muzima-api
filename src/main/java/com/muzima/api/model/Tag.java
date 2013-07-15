package com.muzima.api.model;

/**
 * A tag is a class to hold tag reference in the server. Each form can have multiple tags attached to it.
 */
public class Tag extends OpenmrsSearchable {

    private String uuid;

    private String name;

    /**
     * Get the uuid for the tag.
     *
     * @return the uuid.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Set the uuid for the tag.
     *
     * @param uuid the uuid to set.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
}
