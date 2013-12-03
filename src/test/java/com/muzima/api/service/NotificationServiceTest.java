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
package com.muzima.api.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * TODO: Write brief description about the class here.
 */
@Ignore
public class NotificationServiceTest {
    /**
     * @verifies download notification with matching uuid.
     * @see NotificationService#downloadNotificationByUuid(String)
     */
    @Test
    public void downloadNotificationByUuid_shouldDownloadNotificationWithMatchingUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies download all notifications with matching sender's uuid.
     * @see NotificationService#downloadNotificationBySender(String)
     */
    @Test
    public void downloadNotificationBySender_shouldDownloadAllNotificationsWithMatchingSendersUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies download all notification when sender's uuid is empty.
     * @see NotificationService#downloadNotificationBySender(String)
     */
    @Test
    public void downloadNotificationBySender_shouldDownloadAllNotificationWhenSendersUuidIsEmpty() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies download all notification with matching receiver's uuid.
     * @see NotificationService#downloadNotificationByReceiver(String)
     */
    @Test
    public void downloadNotificationByReceiver_shouldDownloadAllNotificationWithMatchingReceiversUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies download all notifications when receiver's uuid is empty.
     * @see NotificationService#downloadNotificationByReceiver(String)
     */
    @Test
    public void downloadNotificationByReceiver_shouldDownloadAllNotificationsWhenReceiversUuidIsEmpty() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies save notification to local data repository.
     * @see NotificationService#saveNotification(com.muzima.api.model.Notification)
     */
    @Test
    public void saveNotification_shouldSaveNotificationToLocalDataRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies save notifications to local data repository.
     * @see NotificationService#saveNotifications(java.util.List)
     */
    @Test
    public void saveNotifications_shouldSaveNotificationsToLocalDataRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return notification with matching uuid.
     * @see NotificationService#getNotificationByUuid(String)
     */
    @Test
    public void getNotificationByUuid_shouldReturnNotificationWithMatchingUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return null when no notification matching the uuid.
     * @see NotificationService#getNotificationByUuid(String)
     */
    @Test
    public void getNotificationByUuid_shouldReturnNullWhenNoNotificationMatchingTheUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return list of all notifications  with matching sender's uuid.
     * @see NotificationService#getNotificationBySender(String)
     */
    @Test
    public void getNotificationBySender_shouldReturnListOfAllNotificationsWithMatchingSendersUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return empty list when no notification match the sender's uuid.
     * @see NotificationService#getNotificationBySender(String)
     */
    @Test
    public void getNotificationBySender_shouldReturnEmptyListWhenNoNotificationMatchTheSendersUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return list of all notifications with matching receiver's uuid.
     * @see NotificationService#getNotificationByReceiver(String)
     */
    @Test
    public void getNotificationByReceiver_shouldReturnListOfAllNotificationsWithMatchingReceiversUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return empty list when no notification match the receiver's uuid.
     * @see NotificationService#getNotificationByReceiver(String)
     */
    @Test
    public void getNotificationByReceiver_shouldReturnEmptyListWhenNoNotificationMatchTheReceiversUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies delete the notification object from the local repository.
     * @see NotificationService#deleteNotification(com.muzima.api.model.Notification)
     */
    @Test
    public void deleteNotification_shouldDeleteTheNotificationObjectFromTheLocalRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }
}
