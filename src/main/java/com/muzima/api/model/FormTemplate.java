/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

/**
 * FormTemplate is a single reference to the physical raw data that can be rendered for form filling process. The result
 * of a form filling process is the FormData object. FormTemplate hold a reference to the Form object.
 */
public class FormTemplate extends OpenmrsSearchable {

    private String html;

    private String metaJson;

    private String modelXml;

    private String modelJson;

    /**
     * Get the form html.
     *
     * @return the form html.
     */
    public String getHtml() {
        return html;
    }

    /**
     * Set the form html.
     *
     * @param html the form html.
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * Get the form metaJson.
     *
     * @return the form metaJson.
     */
    public String getMetaJson() {
        return metaJson;
    }

    /**
     * Set the form metaJson.
     *
     * @param metaJson the form metaJson.
     */
    public void setMetaJson(String metaJson) {
        this.metaJson = metaJson;
    }

    /**
     * Get the form modelXml.
     *
     * @return the form modelXml.
     */
    public String getModelXml() {
        return modelXml;
    }

    /**
     * Set the form model.
     *
     * @param modelXml the form modelXml.
     */
    public void setModelXml(String modelXml) {
        this.modelXml = modelXml;
    }

    /**
     * Get the form model json.
     *
     * @return the form model json.
     */
    public String getModelJson() {
        return modelJson;
    }

    /**
     * Set the form model json.
     *
     * @param modelJson the form model json.
     */
    public void setModelJson(String modelJson) {
        this.modelJson = modelJson;
    }

    public boolean isHTMLForm() {
        return getModelXml() == null || getModelJson() == null;
    }
}
