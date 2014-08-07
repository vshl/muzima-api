/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.muzima.api.dao.NotificationDao;
import com.muzima.api.model.Notification;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.CollectionUtil;
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
     * Get all notifications for a particular sender from the Lucene repository identified by the sender uuid.
     *
     * @param senderUuid the sender's uuid.
     * @return list of all notification with matching sender uuid or empty list when no notification match the
     * sender's uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all notifications  with matching sender's uuid..
     * @should return empty list when no notification match the sender's uuid..
     */
    @Override
    public List<Notification> getNotificationBySender(final String senderUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(senderUuid)) {
            Filter filter = FilterFactory.createFilter("senderUuid", senderUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<Notification> getNotificationBySender(final String senderUuid, final Integer page,
                                                      final Integer pageSize) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(senderUuid)) {
            Filter filter = FilterFactory.createFilter("senderUuid", senderUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass, page, pageSize);
    }

    @Override
    public List<Notification> getNotificationBySender(final String senderUuid, final String status) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(senderUuid)) {
            Filter filter = FilterFactory.createFilter("senderUuid", senderUuid);
            filters.add(filter);
        }

        if (!StringUtil.isEmpty(status)) {
            Filter filter = FilterFactory.createFilter("status", status);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

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
    @Override
    public List<Notification> getNotificationByReceiver(final String receiverUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(receiverUuid)) {
            Filter filter = FilterFactory.createFilter("receiverUuid", receiverUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<Notification> getNotificationByReceiver(final String receiverUuid, final String status) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(receiverUuid)) {
            Filter filter = FilterFactory.createFilter("receiverUuid", receiverUuid);
            filters.add(filter);
        }

        if (!StringUtil.isEmpty(status)) {
            Filter filter = FilterFactory.createFilter("status", status);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<Notification> getNotificationByReceiver(final String receiverUuid, final Integer page,
                                                        final Integer pageSize) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(receiverUuid)) {
            Filter filter = FilterFactory.createFilter("receiverUuid", receiverUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass, page, pageSize);
    }

    @Override
    public Notification getNotificationBySource(final String source) throws IOException {
        Notification notification = null;
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(source)) {
            Filter filter = FilterFactory.createFilter("source", source);
            filters.add(filter);
        }

        List<Notification> notifications = service.getObjects(filters, daoClass);
        if (!CollectionUtil.isEmpty(notifications)) {
            if (notifications.size() > 1)
                throw new IOException("Unable to uniquely identify a notification using the source");
            notification = notifications.get(0);
        }
        return notification;
    }
}


