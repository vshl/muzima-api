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
package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.NotificationDaoImpl;
import com.muzima.api.model.Notification;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

@ImplementedBy(NotificationDaoImpl.class)
public interface NotificationDao extends OpenmrsDao<Notification> {

    /**
     * Download a single notification record from the patient rest resource into the local lucene repository.
     *
     * @param notificationUuid the uuid of the notification.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download notification with matching uuid.
     */
    Notification downloadNotificationByUuid(final String notificationUuid)) throws IOException;

    /**
     * Download all notification with name similar to the partial sender passed in the parameter.
     *
     * @param senderUuid the partial senderUuid of the notification to be downloaded. When empty, will return all notifications available.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should download all notifications with matched senderUuid.
     * @should download all notification when senderUuid is empty.
     */
    List<Notification> downloadNotificationBySender(final String senderUuid) throws IOException, ParseException;




    /**
     * Search observations for patient with matching partial search term.
     *
     * @param notificationUuid the uuid of the notification.
     * @param term        the search term for the question of the observations.
     * @return all observations for the patient with question matching the search term.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     */
    List<Notification> search(final String notificationUuid, final String term) throws IOException;

    /**
     * Search notifications for errors with matching uuid of the question.
     *
     * @param receiverUuid the uuid of the patient.
     * @param senderUuid the uuid of the question of the observations.
     * @return all observations for the patient with question matching the search term.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     */
    List<Notification> get(final String receiverUuid, final String senderUuid) throws IOException;


    /**
     * Download all notification with receiverUuid.
     *
     * @param receiverUuid the partial name of the patient to be downloaded. When empty, will return all patients available.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should download all notification with partially matched name.
     * @should download all notifications when receiverUuid is empty.
     */
    List<Notification> downloadNotificationByReceiver(final String receiverUuid) throws IOException, ParseException;


    /**
     * Save notification to the local lucene repository.
     *
     * @param notification the notifications to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    void saveNotification(final Notification notification) throws IOException;

    /**
     * Save notifications to the local lucene repository.
     *
     * @param notification the notifications to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    void saveNotification(final List<Notification> notification) throws IOException;


    /**
     * Get list of notification with senderUuid.
     *
     * @param senderUuid the sender uuid.
     * @return list of all patients with matching name or empty list when no patient match the name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should return list of all notifications  with matching senderUuid.
     * @should return empty list when no notification match the senderUuid.
     */
    List<Notification> getNotificationBySender(final String senderUuid) throws IOException, ParseException;

    /**
     * Get list of notification with receiverUuid.
     *
     * @param receiverUuid the receiver uuid.
     * @return list of all patients with matching name or empty list when no patient match the name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should return list of all notifications with matching receiverUuid.
     * @should return empty list when no notification match the receiverUuid.
     */
    List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException, ParseException;




    /**
     * Delete a single patient object from the local repository.
     *
     * @param notification the notification object.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should delete the notification object from the local repository.
     */
    void deleteNotification(final Notification notification) throws IOException;
}
