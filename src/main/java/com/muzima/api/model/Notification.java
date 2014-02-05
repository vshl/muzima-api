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
 * A Notification class is a class to hold notification  properties in the server. A notification
 * will have a uuid, subject, sender, receiver, subject and payload.
 */

public class Notification extends OpenmrsSearchable {

    private String subject;

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
     * Get the status of the notification information.
     *
     * @return the status of the notification information.
     */
    public String getStatus() { return status; }

    /**
     * Set the status of the notification information.
     *
     * @param status the status of the notification information.
     */
    public void setStatus(final String status) { this.status = status; }

    /**
     * Get the source of the notification information.
     *
     * @return the source of the notification information.
     */
    public String getSource() { return source; }

    /**
     * Set the source of the notification information.
     *
     * @param source the source of the notification information.
     */
    public void setSource(final String source) { this.source = source; }

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
