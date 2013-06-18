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

import com.muzima.api.dao.CohortDao;
import com.muzima.api.dao.CohortDefinitionDao;
import com.muzima.api.model.CohortDefinition;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CohortDefinitionDaoImpl extends OpenmrsDaoImpl<CohortDefinition> implements CohortDefinitionDao {

    private static final String TAG = CohortDao.class.getSimpleName();

    protected CohortDefinitionDaoImpl() {
        super(CohortDefinition.class);
    }

    /**
     * Get cohort definition by the name of it. Passing empty string will returns all registered cohort definitions.
     *
     * @param name the partial name of the cohort definition or empty string.
     * @return the list of all matching cohort definition on the name.
     * @throws org.apache.lucene.queryParser.ParseException
     *                             when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public List<CohortDefinition> getByName(final String name) throws ParseException, IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(name)) {
            Filter filter = FilterFactory.createFilter("name", name + "*");
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }
}
