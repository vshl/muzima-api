/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import java.util.Date;

/**
 * A Notification class is a class to hold notification  properties in the server. A notification
 * will have a uuid, subject, sender, receiver, subject and payload.
 */

public class Notification extends OpenmrsSearchable {

    private String subject;

    private Date dateCreated;

    private String status;

    private String source;

    private String payload;

    private Person sender;

    private Person receiver;

    /**
     * Get the subject for the notification.
     *
     * @return the subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject for the notification.
     *
     * @param subject the name to set.
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * Get the date the notification was created
     *
     * @return the subject.
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the date the notification was created.
     *
     * @param dateCreated the name to set.
     */
    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Get the status of the notification information.
     *
     * @return the status of the notification information.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of the notification information.
     *
     * @param status the status of the notification information.
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Get the source of the notification information.
     *
     * @return the source of the notification information.
     */
    public String getSource() {
        return source;
    }

    /**
     * Set the source of the notification information.
     *
     * @param source the source of the notification information.
     */
    public void setSource(final String source) {
        this.source = source;
    }

    /**
     * Get the payload for the notification.
     *
     * @return the payload.
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Set the payload for the Notification.
     *
     * @param payload the name to set
     */
    public void setPayload(final String payload) {
        this.payload = payload;
    }

    /**
     * Get the sender for the notification.
     *
     * @return the sender.
     */
    public Person getSender() {
        return sender;
    }

    /**
     * Set the subject for the notification.
     *
     * @param sender the name to set.
     */
    public void setSender(final Person sender) {
        this.sender = sender;
    }

    /**
     * Get the receiver for the notification.
     *
     * @return the receiver.
     */
    public Person getReceiver() {
        return receiver;
    }

    /**
     * Set the receiver for the Notification.
     *
     * @param receiver the name to set.
     */
    public void setReceiver(final Person receiver) {
        this.receiver = receiver;
    }
}
