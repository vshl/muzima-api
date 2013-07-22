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
     * Download all notification with receiverUuid.
     *
     * @param receiverUuid the notification matching receiverUuid to be downloaded. When empty, will return all notifications available.
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
     * @return list of all notification with matching senderUuid or empty list .
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
     * @return list of all notification with matching receiverUuid or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should return list of all notifications with matching receiverUuid.
     * @should return empty list when no notification match the receiverUuid.
     */
    List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException, ParseException;




    /**
     * Delete a single notification object from the local repository.
     *
     * @param notification the notification object.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should delete the notification object from the local repository.
     */
    void deleteNotification(final Notification notification) throws IOException;
}
