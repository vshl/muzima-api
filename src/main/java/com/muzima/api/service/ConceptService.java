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

    /**
     * Download list of concepts with matching name.
     *
     * @param name the name of the concept.
     * @return list of concepts with matching name.
     * @throws IOException when the search api unable to process the resource.
     * @should download list of concepts with matching name.
     * @should return empty list when the name is empty.
     */
    List<Concept> downloadConceptsByName(final String name) throws IOException;

    /**
     * Get a single concept from local data repository with matching uuid.
     *
     * @param uuid the uuid of the concept.
     * @return the concept with matching uuid.
     * @throws IOException when the search api unable to process the resource.
     * @should return concept with matching uuid.
     * @should return null when no concept match the uuid.
     */
    Concept getConceptByUuid(final String uuid) throws IOException;

    /**
     * Get list of concepts from local data repository with matching name.
     *
     * @param name the name of the concept.
     * @return list of concepts with matching name.
     * @throws IOException when the search api unable to process the resource.
     * @should return list of concepts with matching name.
     * @should return empty list when no concept match the name.
     */
    List<Concept> getConceptsByName(final String name) throws IOException;

    /**
     * Get all concepts stored in the local data repository.
     * @return all concepts stored in the local data repository.
     * @throws IOException when the search api unable to process the resource.
     * @should return all concepts stored in the local data repository.
     */
    List<Concept> getAllConcepts() throws IOException;

    /**
     * Count all concepts stored in the local data repository.
     * @return number of concepts stored in the local data repository.
     * @throws IOException when the search api unable to process the resource.
     * @should return number of concepts stored in the local data repository.
     */
    Integer countAllConcepts() throws IOException;

    /**
     * Save a concept into local data repository.
     *
     * @param concept the concept to be saved.
     * @throws IOException when the search api unable to process the resource.
     * @should save concept into local data repository.
     */
    void saveConcept(final Concept concept) throws IOException;

    /**
     * Save list of concepts into local data repository.
     *
     * @param concepts the concepts to be saved.
     * @throws IOException when the search api unable to process the resource.
     * @should save list of concepts into local data repository.
     */
    void saveConcepts(final List<Concept> concepts) throws IOException;

    /**
     * Update a concept in the local data repository.
     *
     * @param concept the concept to be updated.
     * @throws IOException when the search api unable to process the resource.
     * @should update concept in local data repository.
     */
    void updateConcept(final Concept concept) throws IOException;

    /**
     * Update list of concepts in the local data repository.
     *
     * @param concepts the concepts to be updated.
     * @throws IOException when the search api unable to process the resource.
     * @should update list of concepts in local data repository.
     */
    void updateConcepts(final List<Concept> concepts) throws IOException;

    /**
     * Delete a concept from the local data repository.
     *
     * @param concept the concept to be deleted.
     * @throws IOException when the search api unable to process the resource.
     * @should delete concept from local data repository.
     */
    void deleteConcept(final Concept concept) throws IOException;

    /**
     * Delete list of concepts from the local data repository.
     *
     * @param concepts the concepts to be deleted.
     * @throws IOException when the search api unable to process the resource.
     * @should delete list of concepts from local data repository.
     */
    void deleteConcepts(final List<Concept> concepts) throws IOException;
}
