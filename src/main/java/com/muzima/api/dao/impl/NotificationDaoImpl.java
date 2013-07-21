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
package com.muzima.api.dao.impl;

import com.muzima.api.dao.NotificationDao;
import com.muzima.api.model.Notification;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDaoImpl extends OpenmrsDaoImpl<Notification> implements NotificationDao {

    private static final String TAG = NotificationDao.class.getSimpleName();

    protected NotificationDaoImpl() {
        super(Notification.class);
    }

    /**
     * Search notifications for errors with matching partial search term.
     *
     * @param senderUuid the uuid of the sender.
     * @param receiverUuid the uuid of the receiver.
     * @return all notification matching the sender and receiver
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public List<Notification> search(final String senderUuid, final receiverUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(senderUuid)) {
            Filter patientFilter = FilterFactory.createFilter("senderUuid", senderUuid);
            filters.add(patientFilter);
        }
        if (!StringUtil.isEmpty(receiverUuid)) {
            Filter conceptFilter = FilterFactory.createFilter("receiverUuid", receiverUuid);
            filters.add(conceptFilter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<Notification> search(String notificationUuid, String term) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Notification> get(String receiverUuid, String senderUuid) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}


