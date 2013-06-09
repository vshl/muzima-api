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

import com.muzima.api.dao.CredentialDao;
import com.muzima.api.dao.EvaluatedCohortDao;
import com.muzima.api.model.EvaluatedCohort;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvaluatedCohortDaoImpl extends OpenmrsDaoImpl<EvaluatedCohort> implements EvaluatedCohortDao {

    private static final String TAG = CredentialDao.class.getSimpleName();

    protected EvaluatedCohortDaoImpl() {
        super(EvaluatedCohort.class);
    }

    /**
     * Get cohort by the name of the cohort. Passing empty string will returns all registered cohorts.
     *
     * @param cohortDefinitionUuid the partial name of the cohort or empty string.
     * @return the list of all matching cohort on the cohort name.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    public EvaluatedCohort getByCohortDefinitionUuid(final String cohortDefinitionUuid) throws IOException {
        EvaluatedCohort evaluatedCohort = null;
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(cohortDefinitionUuid)) {
            Filter filter = FilterFactory.createFilter("uuid", cohortDefinitionUuid);
            filters.add(filter);
        }
        List<EvaluatedCohort> evaluatedCohorts = service.getObjects(filters, daoClass);
        if (!CollectionUtil.isEmpty(evaluatedCohorts)) {
            if (evaluatedCohorts.size() > 1)
                throw new IOException("Unable to uniquely identify a Patient using the identifier");
            evaluatedCohort = evaluatedCohorts.get(0);
        }
        return evaluatedCohort;
    }

}
