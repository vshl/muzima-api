/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.muzima.api.dao.FormDataDao;
import com.muzima.api.model.FormData;
import com.muzima.api.model.resolver.SyncFormDataResolver;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;
import org.json.JSONException;
import org.json.JSONWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class FormDataDaoImpl extends SearchableDaoImpl<FormData> implements FormDataDao {

    private static final String TAG = FormDataDaoImpl.class.getSimpleName();

    @Inject(optional = true)
    @Named("connection.proxy")
    private Proxy proxy;

    @Inject
    @Named("connection.timeout")
    private int timeout;

    @Inject
    private SyncFormDataResolver resolver;

    protected FormDataDaoImpl() {
        super(FormData.class);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.FormDataDao#getFormDataByUuid(String)
     */
    @Override
    public FormData getFormDataByUuid(final String uuid) throws IOException {
        return service.getObject(uuid, daoClass);
    }

    /**
     * {@inheritDoc}
     *
     * @see FormDataDao#getAllFormData(String, String, String)
     */
    @Override
    public List<FormData> getAllFormData(final String patientUuid, final String userUuid,
                                         final String status) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter patientFilter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(patientFilter);
        }
        if (!StringUtil.isEmpty(userUuid)) {
            Filter userFilter = FilterFactory.createFilter("userUuid", userUuid);
            filters.add(userFilter);
        }
        if (!StringUtil.isEmpty(status)) {
            Filter statusFilter = FilterFactory.createFilter("status", status);
            filters.add(statusFilter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<FormData> getAllFormData(final String patientUuid, final String userUuid, final String status,
                                         final Integer page, final Integer pageSize) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter patientFilter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(patientFilter);
        }
        if (!StringUtil.isEmpty(userUuid)) {
            Filter userFilter = FilterFactory.createFilter("userUuid", userUuid);
            filters.add(userFilter);
        }
        if (!StringUtil.isEmpty(status)) {
            Filter statusFilter = FilterFactory.createFilter("status", status);
            filters.add(statusFilter);
        }
        return service.getObjects(filters, daoClass, page, pageSize);
    }

    @Override
    public boolean syncFormData(final FormData formData) throws IOException, JSONException {
        boolean synced = false;

        String resourcePath = resolver.resolve(Collections.<String, String>emptyMap());
        URL url = new URL(resourcePath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (proxy != null) {
            connection = (HttpURLConnection) url.openConnection(proxy);
        }
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(timeout);
        connection = resolver.authenticate(connection);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter
                .object()
                .key("dataSource").value("Mobile Device")
                .key("payload").value(getPayloadBasedOnDiscriminator(formData))
                .key("discriminator").value(formData.getDiscriminator())
                .endObject();
        writer.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK
                || responseCode == HttpURLConnection.HTTP_CREATED) {
            synced = true;
        }
        return synced;
    }

    private String getPayloadBasedOnDiscriminator(FormData formData) {
        if ("xml-registration".equals(formData.getDiscriminator())
                || "xml-encounter".equals(formData.getDiscriminator())) {
            return formData.getXmlPayload();
        }
        return formData.getJsonPayload();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.FormDataDao#getFormDataByTemplateUUID(String)
     */
    @Override
    public List<FormData> getFormDataByTemplateUUID(String templateUUID) throws IOException {
        Filter templateUUIDFilter = FilterFactory.createFilter("templateUuid", templateUUID);
        return service.getObjects(asList(templateUUIDFilter), daoClass);
    }
}
