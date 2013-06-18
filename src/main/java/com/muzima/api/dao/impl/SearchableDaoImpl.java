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
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.service.RestAssuredService;
import com.muzima.search.api.util.StringUtil;
import org.apache.lucene.queryParser.ParseException;
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
     * Save the object to the local repository.
     *
     * @param object   the object to be saved.
     * @param resource the resource descriptor used for saving.
     * @return saved object.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void save(final T object, final String resource) throws IOException {
        service.createObjects(Arrays.<Searchable>asList(object), context.getResource(resource));
    }

    /**
     * Save list of objects to the local repository. Use this save method when you want to save multiple objects
     * at the same time.
     *
     * @param objects  the objects to be saved.
     * @param resource the resource descriptor used for saving.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void save(final List<T> objects, final String resource) throws IOException {
        service.createObjects((List<Searchable>) objects, context.getResource(resource));
    }

    /**
     * Update the saved object in the local repository.
     *
     * @param object   the object to be updated.
     * @param resource the resource descriptor used for saving.
     * @return updated object.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void update(final T object, final String resource) throws IOException {
        service.updateObjects(Arrays.<Searchable>asList(object), context.getResource(resource));
    }

    /**
     * Update list of objects in the local repository. Use this save method when you want to update multiple objects
     * at the same time.
     *
     * @param objects  the objects to be updated.
     * @param resource the resource descriptor used for updating.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void update(final List<T> objects, final String resource) throws IOException {
        service.updateObjects((List<Searchable>) objects, context.getResource(resource));
    }

    /**
     * Get the OpenMRS searchable object using the uuid.
     *
     * @param uuid the uuid of the searchable object.
     * @return the searchable object.
     * @throws IOException when search api unable to process the resource.
     */
    public T getByUuid(final String uuid) throws IOException {
        return service.getObject(uuid, daoClass);
    }

    /**
     * Get cohort by the name of the cohort. Passing empty string will returns all registered cohorts.
     *
     * @param name the partial name of the cohort or empty string.
     * @return the list of all matching cohort on the cohort name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    public List<T> getByName(final String name) throws ParseException, IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(name)) {
            Filter filter = FilterFactory.createFilter("name", name + "*");
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }

    /**
     * Get all searchable object for the particular type.
     *
     * @return list of all searchable object or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<T> getAll() throws ParseException, IOException {
        return service.getObjects(StringUtil.EMPTY, daoClass);
    }

    /**
     * Delete the searchable object from the lucene repository.
     *
     * @param searchable the object to be deleted.
     * @param resource   the resource descriptor used to retrieve the object from the repository.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public void delete(final T searchable, final String resource) throws IOException {
        service.deleteObjects(Arrays.<Searchable>asList(searchable), context.getResource(resource));
    }

    /**
     * Delete list of objects from the local repository. Use this save method when you want to delete multiple
     * objects at the same time.
     *
     * @param objects  the objects to be deleted.
     * @param resource the resource descriptor used for deleting.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void delete(final List<T> objects, final String resource) throws IOException {
        service.deleteObjects((List<Searchable>) objects, context.getResource(resource));
    }
}
