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
package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.ConceptDao;
import com.muzima.api.model.Concept;
import com.muzima.api.service.ConceptService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public class ConceptServiceImpl implements ConceptService {

    @Inject
    private ConceptDao conceptDao;

    /**
     * Internal implementation of downloading concept by the uuid of the concept. This will be used
     * primarily to download numeric concept because only numeric concepts will have the units field
     * in the REST resource.
     *
     * @param uuid    the uuid of the concept.
     * @param numeric flag whether the concept is numeric or not.
     * @return the full implementation of the concept object.
     * @throws IOException when the search api unable to process the resource.
     */
    private Concept downloadConceptByUuid(final String uuid, final boolean numeric) throws IOException {
        Concept cohort = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        String resourceName = Constants.UUID_CONCEPT_RESOURCE;
        if (numeric) {
            resourceName = Constants.UUID_CONCEPT_NUMERIC_RESOURCE;
        }
        List<Concept> cohorts = conceptDao.download(parameter, resourceName);
        if (!CollectionUtil.isEmpty(cohorts)) {
            if (cohorts.size() > 1) {
                throw new IOException("Unable to uniquely identify a cohort record.");
            }
            cohort = cohorts.get(0);
        }
        return cohort;
    }

    /**
     * Internal implementation of downloading concept by the uuid of the concept. This will be used
     * primarily to download numeric concept because only numeric concepts will have the units field
     * in the REST resource.
     *
     * @param concept the concept for which the numeric elements will be downloaded.
     * @return the concept with the numeric field's information.
     * @throws IOException when the search api unable to process the resource.
     */
    private Concept downloadConcept(final Concept concept) throws IOException {
        return downloadConceptByUuid(concept.getUuid(), concept.isNumeric());
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#downloadConceptByName(String)
     */
    @Override
    public List<Concept> downloadConceptByName(final String name) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        List<Concept> consolidatedConcepts = new ArrayList<Concept>();
        List<Concept> cohorts = conceptDao.download(parameter, Constants.UUID_CONCEPT_RESOURCE);
        for (Concept concept : cohorts) {
            if (concept.isNumeric()) {
                consolidatedConcepts.add(downloadConcept(concept));
            } else {
                consolidatedConcepts.add(concept);
            }
        }
        return consolidatedConcepts;
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#getConceptByUuid(String)
     */
    @Override
    public Concept getConceptByUuid(final String uuid) throws IOException {
        return conceptDao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#getConceptsByName(String)
     */
    @Override
    public List<Concept> getConceptsByName(final String name) throws IOException {
        return conceptDao.getByName(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#saveConcept(com.muzima.api.model.Concept)
     */
    @Override
    public void saveConcept(final Concept concept) throws IOException {
        if (concept.isNumeric()) {
            conceptDao.save(concept, Constants.UUID_CONCEPT_NUMERIC_RESOURCE);
        } else {
            conceptDao.save(concept, Constants.UUID_CONCEPT_RESOURCE);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#saveConcepts(java.util.List)
     */
    @Override
    public void saveConcepts(final List<Concept> concepts) throws IOException {
        List<Concept> numericConcepts = new ArrayList<Concept>();
        List<Concept> nonNumericConcepts = new ArrayList<Concept>();
        for (Concept cohort : concepts) {
            if (cohort.isNumeric()) {
                numericConcepts.add(cohort);
            } else {
                nonNumericConcepts.add(cohort);
            }
        }
        conceptDao.save(numericConcepts, Constants.UUID_CONCEPT_NUMERIC_RESOURCE);
        conceptDao.save(nonNumericConcepts, Constants.UUID_CONCEPT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#updateConcept(com.muzima.api.model.Concept)
     */
    @Override
    public void updateConcept(final Concept concept) throws IOException {
        if (concept.isNumeric()) {
            conceptDao.update(concept, Constants.UUID_CONCEPT_NUMERIC_RESOURCE);
        } else {
            conceptDao.update(concept, Constants.UUID_CONCEPT_RESOURCE);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#updateConcepts(java.util.List)
     */
    @Override
    public void updateConcepts(final List<Concept> concepts) throws IOException {
        List<Concept> numericConcepts = new ArrayList<Concept>();
        List<Concept> nonNumericConcepts = new ArrayList<Concept>();
        for (Concept cohort : concepts) {
            if (cohort.isNumeric()) {
                numericConcepts.add(cohort);
            } else {
                nonNumericConcepts.add(cohort);
            }
        }
        conceptDao.update(numericConcepts, Constants.UUID_CONCEPT_NUMERIC_RESOURCE);
        conceptDao.update(nonNumericConcepts, Constants.UUID_CONCEPT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#deleteConcept(com.muzima.api.model.Concept)
     */
    @Override
    public void deleteConcept(final Concept concept) throws IOException {
        if (concept.isNumeric()) {
            conceptDao.delete(concept, Constants.UUID_CONCEPT_NUMERIC_RESOURCE);
        } else {
            conceptDao.delete(concept, Constants.UUID_CONCEPT_RESOURCE);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#deleteConcepts(java.util.List)
     */
    @Override
    public void deleteConcepts(final List<Concept> concepts) throws IOException {
        List<Concept> numericConcepts = new ArrayList<Concept>();
        List<Concept> nonNumericConcepts = new ArrayList<Concept>();
        for (Concept cohort : concepts) {
            if (cohort.isNumeric()) {
                numericConcepts.add(cohort);
            } else {
                nonNumericConcepts.add(cohort);
            }
        }
        conceptDao.delete(numericConcepts, Constants.UUID_CONCEPT_NUMERIC_RESOURCE);
        conceptDao.delete(nonNumericConcepts, Constants.UUID_CONCEPT_RESOURCE);
    }
}
