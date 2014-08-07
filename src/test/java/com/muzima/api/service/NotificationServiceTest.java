/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Notification;
import com.muzima.util.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * TODO: Write brief description about the class here.
 */
@Ignore
public class NotificationServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceTest.class.getSimpleName());


    private Notification staticNotification;
    private List<Notification> staticNotifications;

    private Notification dynamicNotification;
    private List<Notification> dynamicNotifications;

    private Context context;
    private NotificationService notificationService;
    private String receiverUuid = "f8609d51-f8e5-4ecc-a105-5e987fc1b9ae";

    private static int nextInt(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    @Before
    public void prepare() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/muzima/" + UUID.randomUUID().toString();
        ContextFactory.setProperty(Constants.LUCENE_DIRECTORY_PATH, path);
        context = ContextFactory.createContext();
        context.openSession();
        if (!context.isAuthenticated()) {
            context.authenticate("admin", "test", "http://localhost:8080/openmrs");
        }
        notificationService = context.getService(NotificationService.class);
    }

    @After
    public void cleanUp() throws Exception {
        String lucenePath = ContextFactory.getProperty(Constants.LUCENE_DIRECTORY_PATH);
        File luceneDirectory = new File(lucenePath);
        for (String filename : luceneDirectory.list()) {
            File file = new File(luceneDirectory, filename);
            Assert.assertTrue(file.delete());
        }
        context.deauthenticate();
        context.closeSession();
    }
//    /**
//     * @verifies download notification with matching uuid.
//     * @see NotificationService#downloadNotificationByUuid(String)
//     */
//    @Test
//    public void downloadNotificationByUuid_shouldDownloadNotificationWithMatchingUuid() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies download all notifications with matching sender's uuid.
//     * @see NotificationService#downloadNotificationBySender(String)
//     */
//    @Test
//    public void downloadNotificationBySender_shouldDownloadAllNotificationsWithMatchingSendersUuid() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies download all notification when sender's uuid is empty.
//     * @see NotificationService#downloadNotificationBySender(String)
//     */
//    @Test
//    public void downloadNotificationBySender_shouldDownloadAllNotificationWhenSendersUuidIsEmpty() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies download all notification with matching receiver's uuid.
//     * @see NotificationService#downloadNotificationByReceiver(String)
//     */
//    @Test
//    public void downloadNotificationByReceiver_shouldDownloadAllNotificationWithMatchingReceiversUuid() throws Exception {
//        dynamicNotifications = notificationService.downloadNotificationByReceiver(receiverUuid) ;
//        assertThat(dynamicNotifications, hasSize(1));
//    }
//
//    /**
//     * @verifies download all notifications when receiver's uuid is empty.
//     * @see NotificationService#downloadNotificationByReceiver(String)
//     */
//    @Test
//    public void downloadNotificationByReceiver_shouldDownloadAllNotificationsWhenReceiversUuidIsEmpty() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies save notification to local data repository.
//     * @see NotificationService#saveNotification(com.muzima.api.model.Notification)
//     */
//    @Test
//    public void saveNotification_shouldSaveNotificationToLocalDataRepository() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//

    /**
     * @verifies save notifications to local data repository.
     * @see NotificationService#saveNotifications(java.util.List)
     */
    @Test
    public void saveNotifications_shouldSaveNotificationsToLocalDataRepository() throws Exception {
        List<Notification> notifications = notificationService.getNotificationByReceiver(receiverUuid);
        int notificationCounter = notifications.size();
        System.out.println("notificationCounter = " + notificationCounter);
        dynamicNotifications = notificationService.downloadNotificationByReceiver(receiverUuid);
        System.out.println("Downloaded notifications = " + dynamicNotifications.size());
        notificationService.saveNotifications(dynamicNotifications);
        assertThat(notificationCounter + dynamicNotifications.size(), equalTo(1));

        //equalTo(notificationService.getNotificationByReceiver(receiverUuid).size()));
    }
//
//    /**
//     * @verifies return notification with matching uuid.
//     * @see NotificationService#getNotificationByUuid(String)
//     */
//    @Test
//    public void getNotificationByUuid_shouldReturnNotificationWithMatchingUuid() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies return null when no notification matching the uuid.
//     * @see NotificationService#getNotificationByUuid(String)
//     */
//    @Test
//    public void getNotificationByUuid_shouldReturnNullWhenNoNotificationMatchingTheUuid() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies return list of all notifications  with matching sender's uuid.
//     * @see NotificationService#getNotificationBySender(String)
//     */
//    @Test
//    public void getNotificationBySender_shouldReturnListOfAllNotificationsWithMatchingSendersUuid() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies return empty list when no notification match the sender's uuid.
//     * @see NotificationService#getNotificationBySender(String)
//     */
//    @Test
//    public void getNotificationBySender_shouldReturnEmptyListWhenNoNotificationMatchTheSendersUuid() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies return list of all notifications with matching receiver's uuid.
//     * @see NotificationService#getNotificationByReceiver(String)
//     */
//    @Test
//    public void getNotificationByReceiver_shouldReturnListOfAllNotificationsWithMatchingReceiversUuid() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
//    /**
//     * @verifies return empty list when no notification match the receiver's uuid.
//     * @see NotificationService#getNotificationByReceiver(String)
//     */
//    @Test
//    public void getNotificationByReceiver_shouldReturnEmptyListWhenNoNotificationMatchTheReceiversUuid() throws Exception {
//        //TODO auto-generated
//        Assert.fail("Not yet implemented");
//    }
//
    /**
     * @verifies delete the notification object from the local repository.
     * @see NotificationService#deleteNotification(com.muzima.api.model.Notification)
     */
//    @Test
//    public void deleteNotification_shouldDeleteTheNotificationObjectFromTheLocalRepository() throws Exception {
    //TODO auto-generated
//        assertThat(notificationService.getNotificationByReceiver("f8609d51-f8e5-4ecc-a105-5e987fc1b9ae"), hasSize(0));
//        formService.saveForms(forms);
//        List<Form> savedForms = formService.getAllForms();
//        assertThat(savedForms, hasSize(forms.size()));
//        formService.deleteForms(savedForms);
//        assertThat(formService.getAllForms(), hasSize(0));
//    }
}
