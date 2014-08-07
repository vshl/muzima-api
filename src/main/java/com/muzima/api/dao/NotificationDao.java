/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.NotificationDaoImpl;
import com.muzima.api.model.Notification;

import java.io.IOException;
import java.util.List;

@ImplementedBy(NotificationDaoImpl.class)
public interface NotificationDao extends OpenmrsDao<Notification> {

    /**
     * Get all notifications for a particular sender from the Lucene repository identified by the sender uuid.
     *
     * @param senderUuid the sender's uuid.
     * @return list of all notification with matching sender uuid or empty list when no notification match the
     * sender's uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all notifications  with matching sender's uuid..
     * @should return empty list when no notification match the sender's uuid..
     */
    List<Notification> getNotificationBySender(final String senderUuid) throws IOException;

    List<Notification> getNotificationBySender(final String senderUuid, final Integer page,
                                               final Integer pageSize) throws IOException;

    List<Notification> getNotificationBySender(final String senderUuid, final String status) throws IOException;

    /**
     * Get all notifications for a particular receiver from the Lucene repository identified by the receiver uuid.
     *
     * @param receiverUuid the receiver's uuid.
     * @return list of all notification with matching receiver uuid or empty list when no notification match the
     * receiver's uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all notifications with matching receiver's uuid..
     * @should return empty list when no notification match the receiver's uuid..
     */
    List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException;

    List<Notification> getNotificationByReceiver(final String receiverUuid, final String status) throws IOException;

    List<Notification> getNotificationByReceiver(final String receiverUuid, final Integer page,
                                                 final Integer pageSize) throws IOException;

    Notification getNotificationBySource(final String source) throws IOException;


}
