/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.muzima.api.dao.CredentialDao;
import com.muzima.api.model.Credential;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CredentialDaoImpl extends SearchableDaoImpl<Credential> implements CredentialDao {

    private static final String TAG = CredentialDao.class.getSimpleName();

    protected CredentialDaoImpl() {
        super(Credential.class);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.CredentialDao#getCredentialByUuid(String)
     */
    @Override
    public Credential getCredentialByUuid(final String uuid) throws IOException {
        return service.getObject(uuid, daoClass);
    }

    /**
     * Get a credential record by the username of the user.
     *
     * @param username the username of the user.
     * @return credential with matching username.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public Credential getByUsername(final String username) throws IOException {
        Credential credential = null;
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(username)) {
            Filter filter = FilterFactory.createFilter("username", username);
            filters.add(filter);
        }
        List<Credential> credentials = service.getObjects(filters, daoClass);
        if (!CollectionUtil.isEmpty(credentials)) {
            if (credentials.size() > 1)
                throw new IOException("Unable to uniquely identify a Patient using the identifier");
            credential = credentials.get(0);
        }
        return credential;
    }

}
