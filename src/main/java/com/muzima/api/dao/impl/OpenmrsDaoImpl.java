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
import com.muzima.search.api.model.object.Searchable;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * @param term     the term to be passed to search object to filter the searchable object.
     * @param resource resource descriptor used to convert the resource to the correct object.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<T> download(final String term, final String resource) throws IOException {
        List<T> list = new ArrayList<T>();
        for (Searchable searchable : service.loadObjects(term, serviceContext.getResource(resource))) {
            list.add((T) searchable);
        }
        return list;
    }
}
