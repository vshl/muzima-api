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
import com.muzima.api.dao.NotificationDao;
import com.muzima.api.model.Notification;
import com.muzima.api.service.NotificationService;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationServiceImpl implements NotificationService {

    @Inject
    private NotificationDao notificationDao;

    protected NotificationServiceImpl() {
    }

    /**
     * Download a single notification record from the notification rest resource into the local lucene repository.
     *
     * @param notificationUuid the uuid of the notification.
     * @throws IOException when search api unable to process the resource.
     * @should download notification with matching uuid.
     */
    public Notification downloadNotificationByUuid(final String notificationUuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("uuid", notificationUuid);
        }};
        List<Notification> notifications = notificationDao.download(parameter, Constants.UUID_NOTIFICATION_RESOURCE);
        if (notifications.size() > 1) {
            throw new IOException("Unable to uniquely identify a notification record.");
        } else if (notifications.size() == 0) {
            return null;
        }
        return notifications.get(0);
    }

    /**
     * Download all notifications from a particular sender.
     *
     * @param senderUuid the sender's uuid of the notification to be downloaded. When empty, will return all
     *                   notifications available.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should download all notifications with matching sender's uuid.
     * @should download all notification when sender's uuid is empty.
     */
    @Override
    public List<Notification> downloadNotificationBySender(final String senderUuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("sender", senderUuid);
        }};
        return notificationDao.download(parameter, Constants.SENDER_NOTIFICATION_RESOURCE);
    }

    /**
     * Download all notifications for a particular receiver.
     *
     * @param receiverUuid the notification matching receiverUuid to be downloaded. When empty, will return all notifications available.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should download all notification with matching receiver's uuid.
     * @should download all notifications when receiver's uuid is empty.
     */
    @Override
    public List<Notification> downloadNotificationByReceiver(final String receiverUuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("receiver", receiverUuid);
        }};
        return notificationDao.download(parameter, Constants.RECEIVER_NOTIFICATION_RESOURCE);
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
     * @param notifications the notifications to be saved.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void saveNotification(final List<Notification> notifications) throws IOException {
        notificationDao.save(notifications, Constants.UUID_NOTIFICATION_RESOURCE);
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
     * Get list of notification from a particular sender from the local lucene repository.
     *
     * @param senderUuid the sender uuid.
     * @return list of all notification with matching sender's uuid or empty list .
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all notifications  with matching sender's uuid.
     * @should return empty list when no notification match the sender's uuid.
     */
    @Override
    public List<Notification> getNotificationBySender(final String senderUuid) throws IOException, ParseException {
        return notificationDao.getNotificationBySender(senderUuid);
    }

    /**
     * Get list of notification for a particular receiver from the local lucene repository.
     *
     * @param receiverUuid the receiver uuid.
     * @return list of all notification with matching receiverUuid or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all notifications with matching receiver's uuid.
     * @should return empty list when no notification match the receiver's uuid.
     */
    @Override
    public List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException, ParseException {
        return notificationDao.getNotificationByReceiver(receiverUuid);
    }

    /**
     * Delete a single notification object from the local repository.
     *
     * @param notification the notification object.
     * @throws IOException when search api unable to process the resource.
     * @should delete the notification object from the local repository.
     */
    @Override
    public void deleteNotification(final Notification notification) throws IOException {
        notificationDao.delete(notification, Constants.UUID_NOTIFICATION_RESOURCE);
    }
}
