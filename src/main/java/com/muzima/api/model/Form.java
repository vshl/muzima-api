/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

/**
 * A form is a class to hold form reference in the server. Each form will have one to one connection with a
 * FormTemplate.
 */
public class Form extends OpenmrsSearchable implements Comparable<Form> {

    private String name;

    private String description;

    private String discriminator;

    private Tag[] tags;

    private String version;

    private boolean retired;

    /**
     * Get the name for the cohort.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the cohort.
     *
     * @param name the name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the description of the form.
     *
     * @return the description of the form.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the form.
     *
     * @param description the description of the form.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(final String discriminator) {
        this.discriminator = discriminator;
    }

    /**
     * Get the tags of the form.
     *
     * @return the tags of the form.
     */
    public Tag[] getTags() {
        return tags;
    }

    /**
     * Set the tags of the form.
     *
     * @param tags the tags of the form.
     */
    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    /**
     * Get the version of the form.
     *
     * @return the version of the form.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version of the form.
     *
     * @param version the version of the form.
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(final boolean retired) {
        this.retired = retired;
    }

    @Override
    public int compareTo(Form form) {
        if (this.getName() != null && form.getName() != null) {
            return this.getName().toLowerCase().compareTo(form.getName().toLowerCase());
        }
        return 0;
    }
}
