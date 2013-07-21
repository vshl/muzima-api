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
package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.model.Notification;
import com.muzima.api.service.NotificationService;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {

    @Inject
    private com.muzima.api.dao.NotificationDao notificationDao;

    protected NotificationServiceImpl() {
    }

    /**
     * Download a single Notification record from the notification rest resource into the local lucene repository.
     *
     * @param senderUuid the uuid of the sender.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should download notification with matching uuid.
     */
    @Override
    public Notification downloadNotificationBySender(final String senderUuid) throws IOException {
        List<Notification> notifications = notificationDao.download(senderUuid, Constants.UUID_NOTIFICATION_RESOURCE);
        if (notification.size() > 1) {
            throw new IOException("Unable to uniquely identify a form record.");
        } else if (notification.size() == 0) {
            return null;
        }
        return notification.get(0);
    }

    /**
     * Download a single Notification record from the notification rest resource into the local lucene repository.
     *
     * @param receiverUuid the uuid of the receiver.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should download notification with matching receiverUuid.
     */
    @Override
    public Notification downloadNotificationByReceiver(final String receiverUuid) throws IOException {
        List<Notification> notifications = notificationDao.download(receiverUuid, Constants.UUID_NOTIFICATION_RESOURCE);
        if (notification.size() > 1) {
            throw new IOException("Unable to uniquely identify a Notification record.");
        } else if (notification.size() == 0) {
            return null;
        }
        return notification.get(0);
    }

    /**
     * Download all notifications matching notificationUuid.
     *
     * @param notificationUuid the partial name of the cohort to be downloaded. When empty, will return all cohorts available.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download all notifications matching notificationuuid.
     * @should download all notification when name is empty.
     */

    public Notification downloadNotification(final String notificationUuid) throws IOException {
        return notifcationDao.download(notificationUuid, Constants.SEARCH_NOTIFICATION_RESOURCE);
    }


    /**
     * Save notification to the local lucene repository.
     *
     * @param notification the notification to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void saveNotification(final Notification notification) throws IOException {
        notificationDao.save(notification, Constants.UUID_NOTIFICATION_RESOURCE);
    }

    /**
     * Save notifications to the local lucene repository.
     *
     * @param notification the notifications to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void saveNotification(final List<Notification> notification) throws IOException {
        notificationDao.save(notification, Constants.UUID_NOTIFICATION_RESOURCE);
    }

   /**
     * Get a single notification record from the local repository with matching uuid.
     *
     * @param notificationUuid the notification uuid
     * @return notification with matching uuid or null when no notification match the uuid
     * @throws java.io.IOException when search api unable to process the resource.
     * @should return notification with matching uuid
     * @should return null when no notification match the uuid
     */
    @Override
    public Notification getNotificationByUuid(final String notificationUuid) throws IOException {
        return notificationDao.getByUuid(notificationUuid);
    }



    /**
     * Get list of notification with sender similar to the search term.
     *
     * @param senderUuid the sender uuid.
     * @return list of all notification with matching name or empty list when no notification match the senderuuid.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should return list of all notification with matching name partially.
     * @should return empty list when no notification match the name.
     */
    @Override
    public List<Notification> getNotificationBySender(final String senderUuid) throws IOException, ParseException {
        return notificationDao.getBySender(senderUuid);
    }

    /**
     * Get list of patients with name similar to the search term.
     *
     * @param receiverUuid the sender uuid.
     * @return list of all patients with matching name or empty list when no notification match the uuid.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should return list of all notifications with matching receiverUuid.
     * @should return empty list when no notification match the name.
     */
    @Override
    public List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException, ParseException {
        return notificationDao.getByReceiver(receiverUuid);
    }


    /**
     * Delete a single notification object from the local repository.
     *
     * @param notification the notification object.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should delete the notification object from the local repository.
     */
    @Override
    public void deleteNotification(final Notification notification) throws IOException {
        notificationDao.delete(notification, Constants.UUID_NOTIFICATION_RESOURCE);
    }
}
