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
     * Get all notifications for a particular sender from the Lucene repository identified by the sender uuid.
     *
     * @param senderUuid the sender's uuid.
     * @return list of all notification with matching sender uuid or empty list when no notification match the
     *         sender's uuid.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all notifications  with matching sender's uuid..
     * @should return empty list when no notification match the sender's uuid..
     */
    List<Notification> getNotificationBySender(final String senderUuid) throws IOException, ParseException;

    /**
     * Get all notifications for a particular receiver from the Lucene repository identified by the receiver uuid.
     *
     * @param receiverUuid the receiver's uuid.
     * @return list of all notification with matching receiver uuid or empty list when no notification match the
     *         receiver's uuid.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all notifications with matching receiver's uuid..
     * @should return empty list when no notification match the receiver's uuid..
     */
    List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException, ParseException;
}
