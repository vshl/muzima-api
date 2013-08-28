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
import com.muzima.api.dao.CohortDao;
import com.muzima.api.model.Cohort;
import com.muzima.search.api.context.ServiceContext;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CohortDaoImpl extends OpenmrsDaoImpl<Cohort> implements CohortDao {

    private static final String TAG = CohortDao.class.getSimpleName();

    @Inject
    private ServiceContext serviceContext;

    protected CohortDaoImpl() {
        super(Cohort.class);
    }

    /**
     * Download the searchable object matching the uuid. This process involve executing the REST call, pulling the
     * resource and then saving it to local lucene repository.
     *
     * @param resourceParams the parameters to be passed to search object to filter the searchable object.
     * @param resource       resource descriptor used to convert the resource to the correct object.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public List<Cohort> download(final Map<String, String> resourceParams, final String resource) throws IOException {
        List<Cohort> list = new ArrayList<Cohort>();
        for (Searchable searchable : service.loadObjects(resourceParams, serviceContext.getResource(resource))) {
            Cohort cohort = (Cohort) searchable;
            if (StringUtil.equals(resource, Constants.SEARCH_DYNAMIC_COHORT_RESOURCE) ||
                    StringUtil.equals(resource, Constants.UUID_DYNAMIC_COHORT_RESOURCE)) {
                cohort.setDynamic(true);
            } else if (StringUtil.equals(resource, Constants.SEARCH_STATIC_COHORT_RESOURCE) ||
                    StringUtil.equals(resource, Constants.UUID_STATIC_COHORT_RESOURCE)) {
                cohort.setDynamic(false);
            }
            list.add(cohort);
        }
        return list;
    }

    /**
     * Get cohort by the name of the cohort. Passing empty string will returns all registered cohorts.
     *
     * @param name the partial name of the cohort or empty string.
     * @return the list of all matching cohort on the cohort name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<Cohort> getByName(final String name) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(name)) {
            Filter filter = FilterFactory.createFilter("name", name + "*");
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }
}
