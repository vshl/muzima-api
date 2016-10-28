package com.muzima.api.model;

public class SetupConfiguration extends OpenmrsSearchable {

    private String name;

    private String description;

    private boolean retired;

    /**
     * Get the name for the configuration.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the configuration.
     *
     * @param name the name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the description of the configuration.
     *
     * @return the description of the configuration.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the configuration.
     *
     * @param description the description of the configuration.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(final boolean retired) {
        this.retired = retired;
    }
}
