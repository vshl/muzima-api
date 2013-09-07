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

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class ConceptServiceImpl implements ConceptService {

    @Inject
    private ConceptDao conceptDao;

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#downloadConceptByUuid(String)
     */
    @Override
    public Concept downloadConceptByUuid(final String uuid) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#downloadConceptByName(String)
     */
    @Override
    public List<Concept> downloadConceptByName(final String name) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#getConceptByUuid(String)
     */
    @Override
    public Concept getConceptByUuid(final String uuid) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#getConceptsByName(String)
     */
    @Override
    public List<Concept> getConceptsByName(final String name) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#saveConcept(com.muzima.api.model.Concept)
     */
    @Override
    public void saveConcept(final Concept concept) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#saveConcepts(java.util.List)
     */
    @Override
    public void saveConcepts(final List<Concept> concepts) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#updateConcept(com.muzima.api.model.Concept)
     */
    @Override
    public void updateConcept(final Concept concept) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#updateConcepts(java.util.List)
     */
    @Override
    public void updateConcepts(final List<Concept> concepts) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#deleteConcept(com.muzima.api.model.Concept)
     */
    @Override
    public void deleteConcept(final Concept concept) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @see ConceptService#deleteConcepts(java.util.List)
     */
    @Override
    public void deleteConcepts(final List<Concept> concepts) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
