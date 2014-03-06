package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.Notification;
import com.muzima.api.service.impl.NotificationServiceImpl;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Service handling all operation to the @{Notification} actor/model
 * <p/>
 * TODO: add ability to search based on lucene like query syntax (merging name and identifier).
 */
@ImplementedBy(NotificationServiceImpl.class)
public interface NotificationService extends MuzimaInterface {

    /**
     * Download a single notification record from the notification rest resource into the local lucene repository.
     *
     * @param notificationUuid the uuid of the notification.
     * @throws IOException when search api unable to process the resource.
     * @should download notification with matching uuid.
     */
    Notification downloadNotificationByUuid(final String notificationUuid) throws IOException;

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
    List<Notification> downloadNotificationBySender(final String senderUuid) throws IOException, ParseException;

    /**
     * Download all notifications for a particular receiver.
     *
     * @param receiverUuid the notification matching receiverUuid to be downloaded. When empty, will return all notifications available.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should download all notification with matching receiver's uuid.
     * @should download all notifications when receiver's uuid is empty.
     */
    List<Notification> downloadNotificationByReceiver(final String receiverUuid) throws IOException, ParseException;

    /**
     * Save notification to the local lucene repository.
     *
     * @param notification the notifications to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save notification to local data repository.
     */
    void saveNotification(final Notification notification) throws IOException;

    /**
     * Save notifications to the local lucene repository.
     *
     * @param notifications the notifications to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save notifications to local data repository.
     */
    void saveNotifications(final List<Notification> notifications) throws IOException;

    /**
     * Get a single notification record from the local repository with matching uuid.
     *
     * @param notificationUuid the notification uuid.
     * @return notification with matching uuid or null when no notification match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return notification with matching uuid.
     * @should return null when no notification matching the uuid.
     */
    public Notification getNotificationByUuid(final String notificationUuid) throws IOException;

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
    List<Notification> getNotificationBySender(final String senderUuid) throws IOException, ParseException;

    /**
     * Get list of notification for a particular sender from the local lucene repository.
     *
     * @param senderUuid the sender uuid.
     * @param status  the status of notifications
     * @return list of all notification with matching receiverUuid or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all notifications with matching receiver's uuid.
     * @should return empty list when no notification match the receiver's uuid.
     */
    List<Notification> getNotificationBySender(final String senderUuid, final String status) throws IOException, ParseException;

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
    List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException, ParseException;

    /**
     * Get list of notification for a particular receiver from the local lucene repository.
     *
     * @param receiverUuid the receiver uuid.
     * @param status  the status of notifications
     * @return list of all notification with matching receiverUuid or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all notifications with matching receiver's uuid.
     * @should return empty list when no notification match the receiver's uuid.
     */
    List<Notification> getNotificationByReceiver(final String receiverUuid, final String status) throws IOException, ParseException;

    /**
     * Delete a single notification object from the local repository.
     *
     * @param notification the notification object.
     * @throws IOException when search api unable to process the resource.
     * @should delete the notification object from the local repository.
     */
    void deleteNotification(final Notification notification) throws IOException;

    void deleteNotifications(final List<Notification> notifications)  throws IOException;
}
