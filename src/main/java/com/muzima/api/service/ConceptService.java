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
package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.Concept;
import com.muzima.api.service.impl.ConceptServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(ConceptServiceImpl.class)
public interface ConceptService extends MuzimaInterface {

    Concept downloadConceptByUuid(final String uuid) throws IOException;

    List<Concept> downloadConceptByName(final String name) throws IOException;

    Concept getConceptByUuid(final String uuid) throws IOException;

    List<Concept> getConceptsByName(final String name) throws IOException;

    void saveConcept(final Concept concept) throws IOException;

    void saveConcepts(final List<Concept> concepts) throws IOException;

    void updateConcept(final Concept concept) throws IOException;

    void updateConcepts(final List<Concept> concepts) throws IOException;

    void deleteConcept(final Concept concept) throws IOException;

    void deleteConcepts(final List<Concept> concepts) throws IOException;
}
