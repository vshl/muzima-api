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

import com.muzima.api.dao.ObservationDao;
import com.muzima.api.model.Concept;
import com.muzima.api.model.Observation;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObservationDaoImpl extends OpenmrsDaoImpl<Observation> implements ObservationDao {

    private static final String TAG = ObservationDao.class.getSimpleName();

    protected ObservationDaoImpl() {
        super(Observation.class);
    }

    /**
     * Search observations for patient with matching uuid of the question.
     *
     * @param patientUuid the uuid of the patient.
     * @param conceptUuid the uuid of the question of the observations.
     * @return all observations for the patient with question matching the search term.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public List<Observation> get(final String patientUuid, final String conceptUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter patientFilter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(patientFilter);
        }
        if (!StringUtil.isEmpty(conceptUuid)) {
            Filter conceptFilter = FilterFactory.createFilter("conceptUuid", conceptUuid);
            filters.add(conceptFilter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<Observation> get(final String patientUuid, final String conceptUuid,
                                 final Integer page, final Integer pageSize) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(patientUuid)) {
            Filter patientFilter = FilterFactory.createFilter("patientUuid", patientUuid);
            filters.add(patientFilter);
        }
        if (!StringUtil.isEmpty(conceptUuid)) {
            Filter conceptFilter = FilterFactory.createFilter("conceptUuid", conceptUuid);
            filters.add(conceptFilter);
        }
        return service.getObjects(filters, daoClass, page, pageSize);
    }

    @Override
    public List<Observation> get(Concept concept) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if(concept != null){
            Filter conceptFilter = FilterFactory.createFilter("conceptUuid", concept.getUuid());
            filters.add(conceptFilter);
        }
        return service.getObjects(filters, daoClass);
    }

    @Override
    public List<Observation> get(String formDataUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if(formDataUuid != null){
            Filter conceptFilter = FilterFactory.createFilter("formDataUuid", formDataUuid);
            filters.add(conceptFilter);
        }
        return service.getObjects(filters, daoClass);
    }
}
