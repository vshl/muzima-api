/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muzima.api.model;

/**
 * Cohort is a structure to hold collection of patients. Cohort will have a one to one connection with a Member object
 * where we can find the uuid of patients in the cohort.
 */
public class Notification extends OpenmrsSearchable {

    private String uuid;

    private String subject;

    private String sender;

    private String receiver;

    private String payload;

    /**
     * Get the uuid for the notification.
     *
     * @return the uuid.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Set the uuid for the notification.
     *
     * @param uuid the uuid to set.
     */
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

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
     * Get the sender for the notification.
     *
     * @return the sender.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Set the subject for the notification.
     *
     * @param sender the name to set.
     */
    public void setSender(final String sender) {
        this.sender = sender;
    }

    /**
     * Get the receiver for the notification.
     *
     * @return the receiver.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Set the receiver for the Notification.
     *
     * @param receiver the name to set.
     */
    public void setReceiver(final String receiver) {
        this.receiver = receiver;
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
        this.payload= payload;
    }
}
