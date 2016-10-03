/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import com.muzima.search.api.model.object.Searchable;

import java.util.Date;

/**
 * FormData is a class to persist a single reference of filled form. The status of the filling could be complete,
 * incomplete or sent. The class also hold reference to the FormTemplate from which the FormData originate, User who
 * fill the form and Patient to whom the form is associated with.
 */
public class FormData implements Searchable {

    private String uuid;

    private String status;

    private String discriminator;

    private String xmlPayload;

    private String jsonPayload;

    private String patientUuid;

    private String userUuid;

    private String templateUuid;

    private Date saveTime;

    private Date encounterDate;

    /**
     * Get the form data uuid.
     *
     * @return the form data uuid.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Set the form data uuid.
     *
     * @param uuid the form data uuid.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the filling status for the form.
     *
     * @return the filling status for the form.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the filling status for the form.
     *
     * @param status the filling status for the form.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get the discriminator information for this form data.
     *
     * @return the discriminator for this form data.
     */
    public String getDiscriminator() {
        return discriminator;
    }

    /**
     * Set the discriminator information for this form data.
     *
     * @param discriminator the discriminator for this form data.
     */
    public void setDiscriminator(final String discriminator) {
        this.discriminator = discriminator;
    }

    /**
     * Get the payload of the form data in JSON format.
     *
     * @return the payload of the form data in JSON format.
     */
    public String getJsonPayload() {
        return jsonPayload;
    }

    /**
     * Set the payload in JSON format of the form data.
     *
     * @param payload the payload of the form data.
     */
    public void setJsonPayload(String payload) {
        this.jsonPayload = payload;
    }

    /**
     * Get the uuid of the patient associated with this form data.
     *
     * @return the uuid of the patient associated with this form data.
     */
    public String getPatientUuid() {
        return patientUuid;
    }

    /**
     * Set the uuid of the patient associated with this form data.
     *
     * @param patientUuid the uuid of the patient associated with this form data.
     */
    public void setPatientUuid(final String patientUuid) {
        this.patientUuid = patientUuid;
    }

    /**
     * Get the uuid of the user who filled this form data
     *
     * @return the uuid of the user who filled this form data
     */
    public String getUserUuid() {
        return userUuid;
    }

    /**
     * Set the uuid of the user who filled this form data
     *
     * @param userUuid the uuid of the user who filled this form data
     */
    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }

    /**
     * Get the form data template uuid.
     *
     * @return the form data template uuid.
     */
    public String getTemplateUuid() {
        return templateUuid;
    }

    /**
     * Set the form data template uuid.
     *
     * @param templateUuid the form data template uuid.
     */
    public void setTemplateUuid(String templateUuid) {
        this.templateUuid = templateUuid;
    }

    /**
     * Get the XML Payload of the form data.
     *
     * @return the XML payload of the form data.
     */
    public String getXmlPayload() {
        return xmlPayload;
    }

    /**
     * Set the XML payload of the form data.
     *
     * @param xmlPayload the XML payload of the form data.
     */
    public void setXmlPayload(String xmlPayload) {
        this.xmlPayload = xmlPayload;
    }

    /**
     * Get the save time form data.
     *
     * @return the save time of the form data.
     */
    public Date getSaveTime() {
        return saveTime;
    }

    /**
     * Set the save time of the form data.
     *
     * @param saveTime the save time form data.
     */
    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    /**
     * Get the encounter date for the encounter form.
     *
     * @return the save time of the form data.
     */
    public Date getEncounterDate() {
        return encounterDate;
    }

    /**
     * Set the encounter date for the encounter form.
     *
     * @param encounterDate the save time form data.
     */
    public void setEncounterDate(Date encounterDate) {
        this.encounterDate = encounterDate;
    }
}
