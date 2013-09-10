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

import com.muzima.api.dao.EncounterDao;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Encounter;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class EncounterDaoImpl extends OpenmrsDaoImpl<Encounter> implements EncounterDao {

    protected EncounterDaoImpl() {
        super(Encounter.class);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterDao#getEncountersByPatientUuid(String)
     */
    @Override
    public List<Encounter> getEncountersByPatientUuid(final String patientUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter filter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(filter);
        }
        return service.getObjects(filters, daoClass);
    }
}
