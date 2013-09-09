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

import com.google.inject.Inject;
import com.muzima.api.dao.OpenmrsDao;
import com.muzima.api.model.OpenmrsSearchable;
import com.muzima.search.api.context.ServiceContext;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class OpenmrsDaoImpl<T extends OpenmrsSearchable> extends SearchableDaoImpl<T> implements OpenmrsDao<T> {

    @Inject
    private ServiceContext serviceContext;

    protected OpenmrsDaoImpl(final Class<T> daoClass) {
        super(daoClass);
    }

    /**
     * Download the searchable object matching the uuid. This process involve executing the REST call, pulling the
     * resource and then saving it to local lucene repository.
     *
     * @param resourceParams the parameters to be passed to search object to filter the searchable object.
     * @param resource       resource descriptor used to convert the resource to the correct object.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> download(final Map<String, String> resourceParams, final String resource) throws IOException {
        List<T> list = new ArrayList<T>();
        for (Searchable searchable : service.loadObjects(resourceParams, serviceContext.getResource(resource))) {
            list.add((T) searchable);
        }
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#getByUuid(String)
     */
    public T getByUuid(final String uuid) throws IOException {
        return service.getObject(uuid, daoClass);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#countByName(String)
     */
    @Override
    public Integer countByName(final String name) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(name)) {
            Filter filter = FilterFactory.createFilter("name", name + "*");
            filters.add(filter);
        }
        return service.countObjects(filters, daoClass);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#getByName(String)
     */
    public List<T> getByName(final String name) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(name)) {
            Filter filter = FilterFactory.createFilter("name", name + "*");
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.OpenmrsDao#getByName(String, Integer, Integer)
     */
    @Override
    public List<T> getByName(final String name, final Integer page, final Integer pageSize) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(name)) {
            Filter filter = FilterFactory.createFilter("name", name + "*");
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass, page, pageSize);
    }
}
