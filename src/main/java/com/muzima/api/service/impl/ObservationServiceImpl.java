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
import com.muzima.api.dao.ObservationDao;
import com.muzima.api.model.Observation;
import com.muzima.api.service.ObservationService;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservationServiceImpl implements ObservationService {

    @Inject
    private ObservationDao observationDao;

    protected ObservationServiceImpl() {
    }

    /**
     * Download a single observation record from the observation rest resource into the local lucene repository.
     *
     * @param uuid the uuid of the observation.
     * @throws IOException when search api unable to process the resource.
     * @should download observation with matching uuid.
     */
    @Override
    public Observation downloadObservationByUuid(final String uuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("uuid", uuid);
        }};
        List<Observation> observations = observationDao.download(parameter, Constants.UUID_OBSERVATION_RESOURCE);
        if (observations.size() > 1) {
            throw new IOException("Unable to uniquely identify a form record.");
        } else if (observations.size() == 0) {
            return null;
        }
        return observations.get(0);
    }

    /**
     * Download all observations with name similar to the partial name passed in the parameter.
     *
     * @param patientUuid the partial name of the observation to be downloaded. When empty, will return all observations available.
     * @throws IOException when search api unable to process the resource.
     * @should download all observation with partially matched name.
     * @should download all observation when name is empty.
     */
    @Override
    public List<Observation> downloadObservationsByPatient(final String patientUuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>(){{
            put("patient", patientUuid);
        }};
        return observationDao.download(parameter, Constants.SEARCH_OBSERVATION_RESOURCE);
    }

    /**
     * Save the observation into the local lucene repository.
     *
     * @param observation the observation to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void saveObservation(final Observation observation) throws IOException {
        observationDao.save(observation, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * Save the observations into the local lucene repository.
     *
     * @param observations the observations to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void saveObservations(final List<Observation> observations) throws IOException {
        observationDao.save(observations, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * Update the observation into the local lucene repository.
     *
     * @param observation the observation to be updated.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void updateObservation(final Observation observation) throws IOException {
        observationDao.update(observation, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * Update the observations into the local lucene repository.
     *
     * @param observations the observations to be updated.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void updateObservations(final List<Observation> observations) throws IOException {
        observationDao.update(observations, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * Get a single observation record from the repository using the uuid of the observation.
     *
     * @param uuid the observation uuid.
     * @return the observation with matching uuid or null when no observation match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return observation with matching uuid.
     * @should return null when no observation match the uuid.
     */
    @Override
    public Observation getObservationByUuid(final String uuid) throws IOException {
        return observationDao.getByUuid(uuid);
    }

    /**
     * Get all observations for the particular patient.
     *
     * @param patientUuid the uuid of the patient.
     * @return list of all observations for the patient or empty list when no observation found for the patient.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all observations for the patient.
     * @should return empty list when no observation found for the patient.
     */
    @Override
    public List<Observation> getObservationsByPatient(final String patientUuid) throws IOException {
        return observationDao.search(patientUuid, StringUtil.EMPTY);
    }

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
    @Override
    public List<Observation> getObservationsByPatientAndConcept(final String patientUuid, final String conceptUuid) throws IOException {
        return observationDao.get(patientUuid, conceptUuid);
    }

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
    @Override
    public List<Observation> searchObservations(final String patientUuid, final String term) throws IOException, ParseException {
        return observationDao.search(patientUuid, term);
    }

    /**
     * Delete a single observation from the local repository.
     *
     * @param observation the observation.
     * @throws IOException when search api unable to process the resource.
     * @should delete the observation from the local repository.
     */
    @Override
    public void deleteObservation(final Observation observation) throws IOException {
        observationDao.delete(observation, Constants.UUID_OBSERVATION_RESOURCE);
    }
}
