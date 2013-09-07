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
import com.muzima.api.model.Observation;
import com.muzima.api.service.impl.ObservationServiceImpl;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Service handling all operation to the @Observation actor/model
 */
@ImplementedBy(ObservationServiceImpl.class)
public interface ObservationService extends MuzimaInterface {

    /**
     * Download a single observation record from the observation rest resource.
     *
     * @param uuid the uuid of the observation.
     * @throws IOException when search api unable to process the resource.
     * @should download observation with matching uuid.
     */
    Observation downloadObservationByUuid(final String uuid) throws IOException;

    /**
     * Download all observations for patient with the concept as the question.
     *
     * @param patientUuid the patient uuid.
     * @param conceptUuid the concept uuid.
     * @throws IOException when search api unable to process the resource.
     * @should download all observation with matching patient and concept.
     * @should download all observation when name is empty.
     */
    List<Observation> downloadObservationsByPatientAndConcept(final String patientUuid,
                                                              final String conceptUuid) throws IOException;

    /**
     * Save the observation into the local lucene repository.
     *
     * @param observation the observation to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save observation to local data repository
     */
    void saveObservation(final Observation observation) throws IOException;

    /**
     * Save the observations into the local lucene repository.
     *
     * @param observations the observations to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save observations to local data repository
     */
    void saveObservations(final List<Observation> observations) throws IOException;

    /**
     * Update the observation into the local lucene repository.
     *
     * @param observation the observation to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace existing observation in the local data repository.
     */
    void updateObservation(final Observation observation) throws IOException;

    /**
     * Update the observations into the local lucene repository.
     *
     * @param observations the observations to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace existing observations in the local data repository.
     */
    void updateObservations(final List<Observation> observations) throws IOException;

    /**
     * Get a single observation record from the repository using the uuid of the observation.
     *
     * @param uuid the observation uuid.
     * @return the observation with matching uuid or null when no observation match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return observation with matching uuid.
     * @should return null when no observation match the uuid.
     */
    Observation getObservationByUuid(final String uuid) throws IOException;

    /**
     * Get all observations for the particular patient.
     *
     * @param patientUuid the uuid of the patient.
     * @return list of all observations for the patient or empty list when no observation found for the patient.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all observations for the patient.
     * @should return empty list when no observation found for the patient.
     */
    List<Observation> getObservationsByPatient(final String patientUuid) throws IOException;

    /**
     * Get all observations for the particular patient.
     *
     * @param patientUuid the uuid of the patient.
     * @param conceptUuid the uuid of the concept.
     * @return list of all observations for the patient or empty list when no observation found for the patient.
     * @throws IOException when search api unable to process the resource.
     * @should return list of all observations for the patient.
     * @should return empty list when no observation found for the patient.
     */
    List<Observation> getObservationsByPatientAndConcept(final String patientUuid,
                                                         final String conceptUuid) throws IOException;

    /**
     * Search for all observations for the particular patient with matching search term.
     *
     * @param patientUuid the patient.
     * @param term        the search term.
     * @return list of all observations with matching search term on the searchable fields or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all observations with matching search term on the searchable fields.
     * @should return empty list when no observation match the search term.
     */
    List<Observation> searchObservations(final String patientUuid,
                                         final String term) throws IOException, ParseException;

    /**
     * Delete a single observation from the local repository.
     *
     * @param observation the observation.
     * @throws IOException when search api unable to process the resource.
     * @should delete the observation from the local repository.
     */
    void deleteObservation(final Observation observation) throws IOException;

    /**
     * Delete observations from the local repository.
     *
     * @param observations the observations.
     * @throws IOException when search api unable to process the resource.
     * @should delete observations from the local repository.
     */
    void deleteObservations(final List<Observation> observations) throws IOException;
}
