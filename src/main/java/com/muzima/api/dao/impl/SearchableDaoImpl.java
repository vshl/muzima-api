/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
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
