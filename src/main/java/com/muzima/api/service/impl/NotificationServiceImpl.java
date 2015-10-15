/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.NotificationDao;
import com.muzima.api.model.Notification;
import com.muzima.api.service.NotificationService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NotificationServiceImpl implements NotificationService {

    @Inject
    private NotificationDao notificationDao;

    protected NotificationServiceImpl() {
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#downloadNotificationByUuid(String)
     */
    public Notification downloadNotificationByUuid(final String notificationUuid) throws IOException {
        Notification notification = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", notificationUuid);
        }};
        List<Notification> notifications = notificationDao.download(parameter, Constants.UUID_NOTIFICATION_RESOURCE);
        if (!CollectionUtil.isEmpty(notifications)) {
            if (notifications.size() > 1) {
                throw new IOException("Unable to uniquely identify a notification record.");
            }
            notification = notifications.get(0);
        }
        return notification;
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#downloadNotificationBySender(String)
     */
    @Override
    public List<Notification> downloadNotificationBySender(final String senderUuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("sender", senderUuid);
        }};
        return notificationDao.download(parameter, Constants.SENDER_NOTIFICATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#downloadNotificationByReceiver(String)
     */
    @Override
    public List<Notification> downloadNotificationByReceiver(final String receiverUuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("receiver", receiverUuid);
        }};
        return notificationDao.download(parameter, Constants.RECEIVER_NOTIFICATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#saveNotification(com.muzima.api.model.Notification)
     */
    @Override
    public void saveNotification(final Notification notification) throws IOException {

        if (StringUtil.isEmpty(notification.getUuid())) {
            String uuid = UUID.randomUUID().toString();
            notification.setUuid(uuid);
            notificationDao.save(notification, Constants.UUID_NOTIFICATION_RESOURCE);
        } else
            notificationDao.update(notification, Constants.UUID_NOTIFICATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#saveNotifications(java.util.List
     */
    @Override
    public void saveNotifications(final List<Notification> notifications) throws IOException {
        notificationDao.save(notifications, Constants.UUID_NOTIFICATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#getNotificationByUuid(String)
     */
    @Override
    public Notification getNotificationByUuid(final String notificationUuid) throws IOException {
        return notificationDao.getByUuid(notificationUuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#getNotificationBySource(String)
     */
    @Override
    public Notification getNotificationBySource(final String source) throws IOException {
        return notificationDao.getNotificationBySource(source);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#getNotificationBySender(String)
     */
    @Override
    public List<Notification> getNotificationBySender(final String senderUuid) throws IOException {
        return notificationDao.getNotificationBySender(senderUuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#getNotificationBySender(String, String)
     */
    @Override
    public List<Notification> getNotificationBySender(String senderUuid, String status) throws IOException {
        return notificationDao.getNotificationBySender(senderUuid, status);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#getNotificationByReceiver(String)
     */
    @Override
    public List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException {
        return notificationDao.getNotificationByReceiver(receiverUuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#getNotificationByReceiver(String, String)
     */
    @Override
    public List<Notification> getNotificationByReceiver(String receiverUuid, String status) throws IOException {
        return notificationDao.getNotificationByReceiver(receiverUuid, status);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#getNotificationByPatient(String, String, String)
     */
    @Override
    public List<Notification> getNotificationByPatient(String patientUuid, String receiverUuid, String status) throws IOException {
        return notificationDao.getNotificationByPatient(patientUuid, receiverUuid, status);
    }

    /**
     * {@inheritDoc}
     *
     * @see NotificationService#deleteNotification(com.muzima.api.model.Notification)
     */
    @Override
    public void deleteNotification(final Notification notification) throws IOException {
        notificationDao.delete(notification, Constants.UUID_NOTIFICATION_RESOURCE);
    }

    @Override
    public void deleteNotifications(List<Notification> notifications) throws IOException {
        notificationDao.delete(notifications, Constants.UUID_NOTIFICATION_RESOURCE);
    }
}
