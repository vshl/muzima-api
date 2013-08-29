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
import com.muzima.api.dao.SearchableDao;
import com.muzima.search.api.context.ServiceContext;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.service.RestAssuredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public abstract class SearchableDaoImpl<T extends Searchable> implements SearchableDao<T> {

    protected Logger logger;

    protected Class<T> daoClass;

    @Inject
    protected ServiceContext context;

    @Inject
    protected RestAssuredService service;

    protected SearchableDaoImpl(final Class<T> daoClass) {
        this.daoClass = daoClass;
        this.logger = LoggerFactory.getLogger(daoClass.getSimpleName());
    }

    /**
     * {@inheritDoc}
     *
     * @see SearchableDao#save(com.muzima.search.api.model.object.Searchable, String)
     */
    @Override
    public void save(final T object, final String resource) throws IOException {
        service.createObjects(Arrays.<Searchable>asList(object), context.getResource(resource));
    }

    /**
     * {@inheritDoc}
     *
     * @see SearchableDao#save(java.util.List, String)
     */
    @Override
    public void save(final List<T> objects, final String resource) throws IOException {
        service.createObjects(
                Arrays.asList(objects.toArray(new Searchable[objects.size()])),
                context.getResource(resource));
    }

    /**
     * {@inheritDoc}
     *
     * @see SearchableDao#update(com.muzima.search.api.model.object.Searchable, String)
     */
    @Override
    public void update(final T object, final String resource) throws IOException {
        service.updateObjects(Arrays.<Searchable>asList(object), context.getResource(resource));
    }

    /**
     * {@inheritDoc}
     *
     * @see SearchableDao#update(java.util.List, String)
     */
    @Override
    public void update(final List<T> objects, final String resource) throws IOException {
        service.updateObjects(
                Arrays.asList(objects.toArray(new Searchable[objects.size()])),
                context.getResource(resource));
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#countAll()
     */
    @Override
    public Integer countAll() throws IOException {
        return service.countObjects(new ArrayList<Filter>(), daoClass);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.dao.SearchableDao#getAll()
     */
    @Override
    public List<T> getAll() throws IOException {
        return service.getObjects(new ArrayList<Filter>(), daoClass);
    }

    /**
     * {@inheritDoc}
     *
     * @see SearchableDao#getAll(Integer, Integer)
     */
    @Override
    public List<T> getAll(final Integer page, final Integer pageSize) throws IOException {
        return service.getObjects(new ArrayList<Filter>(), daoClass, page, pageSize);
    }

    /**
     * {@inheritDoc}
     *
     * @see SearchableDao#delete(com.muzima.search.api.model.object.Searchable, String)
     */
    @Override
    public void delete(final T searchable, final String resource) throws IOException {
        service.deleteObjects(Arrays.<Searchable>asList(searchable), context.getResource(resource));
    }

    /**
     * {@inheritDoc}
     *
     * @see SearchableDao#delete(java.util.List, String)
     */
    @Override
    public void delete(final List<T> objects, final String resource) throws IOException {
        service.deleteObjects(
                Arrays.asList(objects.toArray(new Searchable[objects.size()])),
                context.getResource(resource));
    }
}
