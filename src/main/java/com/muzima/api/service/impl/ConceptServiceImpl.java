/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.ConceptDao;
import com.muzima.api.model.Concept;
import com.muzima.api.service.ConceptService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;
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
        Concept concept = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        String resourceName = Constants.UUID_CONCEPT_RESOURCE;
        if (numeric) {
            resourceName = Constants.UUID_CONCEPT_NUMERIC_RESOURCE;
        }
        List<Concept> concepts = conceptDao.download(parameter, resourceName);
        if (!CollectionUtil.isEmpty(concepts)) {
            if (concepts.size() > 1) {
                throw new IOException("Unable to uniquely identify a concept record.");
            }
            concept = concepts.get(0);
        }
        return concept;
    }

    @Override
    public Concept downloadConceptByUuid(final String uuid) throws IOException {
        Concept concept = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        String resourceName = Constants.UUID_CONCEPT_RESOURCE;
        List<Concept> concepts = conceptDao.download(parameter, resourceName);
        if (!CollectionUtil.isEmpty(concepts)) {
            if (concepts.size() > 1) {
                throw new IOException("Unable to uniquely identify a concept record.");
            }
            concept = concepts.get(0);

            if(concept.isNumeric()){
                return downloadConceptByUuid(uuid,true);
            }
        }
        return concept;
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
     * @see ConceptService#downloadConceptsByName(String)
     */
    @Override
    public List<Concept> downloadConceptsByName(final String name) throws IOException {
        List<Concept> consolidatedConcepts = new ArrayList<Concept>();
        if (!StringUtil.isEmpty(name)) {
            Map<String, String> parameter = new HashMap<String, String>() {{
                put("q", name);
            }};
            List<Concept> concepts = conceptDao.download(parameter, Constants.SEARCH_CONCEPT_RESOURCE);
            for (Concept concept : concepts) {
                if (concept.isNumeric()) {
                    consolidatedConcepts.add(downloadConcept(concept));
                } else {
                    consolidatedConcepts.add(concept);
                }
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
     * @see ConceptService#getAllConcepts()
     */
    @Override
    public List<Concept> getAllConcepts() throws IOException {
        return conceptDao.getAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#countAllConcepts()
     */
    @Override
    public Integer countAllConcepts() throws IOException {
        return conceptDao.countAll();
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
        for (Concept concept : concepts) {
            if (concept.isNumeric()) {
                numericConcepts.add(concept);
            } else {
                nonNumericConcepts.add(concept);
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
        for (Concept concept : concepts) {
            if (concept.isNumeric()) {
                numericConcepts.add(concept);
            } else {
                nonNumericConcepts.add(concept);
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
        for (Concept concept : concepts) {
            if (concept.isNumeric()) {
                numericConcepts.add(concept);
            } else {
                nonNumericConcepts.add(concept);
            }
        }
        conceptDao.delete(numericConcepts, Constants.UUID_CONCEPT_NUMERIC_RESOURCE);
        conceptDao.delete(nonNumericConcepts, Constants.UUID_CONCEPT_RESOURCE);
    }
}
