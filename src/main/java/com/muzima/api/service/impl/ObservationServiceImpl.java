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
import com.muzima.search.api.util.CollectionUtil;
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
     * {@inheritDoc}
     *
     * @see ObservationService#downloadObservationByUuid(String)
     */
    @Override
    public Observation downloadObservationByUuid(final String uuid) throws IOException {
        Observation observation = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<Observation> observations = observationDao.download(parameter, Constants.UUID_OBSERVATION_RESOURCE);
        if (!CollectionUtil.isEmpty(observations)) {
            if (observations.size() > 1) {
                throw new IOException("Unable to uniquely identify a form record.");
            }
            observation = observations.get(0);
        }
        return observation;
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#downloadObservationsByPatientAndConcept(String, String)
     */
    @Override
    public List<Observation> downloadObservationsByPatientAndConcept(final String patientUuid,
                                                                     final String conceptUuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("person", patientUuid);
            put("concept", conceptUuid);
        }};
        return observationDao.download(parameter, Constants.SEARCH_OBSERVATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#saveObservation(com.muzima.api.model.Observation)
     */
    @Override
    public void saveObservation(final Observation observation) throws IOException {
        observationDao.save(observation, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#saveObservations(java.util.List)
     */
    @Override
    public void saveObservations(final List<Observation> observations) throws IOException {
        observationDao.save(observations, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#updateObservation(com.muzima.api.model.Observation)
     */
    @Override
    public void updateObservation(final Observation observation) throws IOException {
        observationDao.update(observation, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#updateObservations(java.util.List)
     */
    @Override
    public void updateObservations(final List<Observation> observations) throws IOException {
        observationDao.update(observations, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#getObservationByUuid(String)
     */
    @Override
    public Observation getObservationByUuid(final String uuid) throws IOException {
        return observationDao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#getObservationsByPatient(String)
     */
    @Override
    public List<Observation> getObservationsByPatient(final String patientUuid) throws IOException {
        return observationDao.search(patientUuid, StringUtil.EMPTY);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#getObservationsByPatientAndConcept(String, String)
     */
    @Override
    public List<Observation> getObservationsByPatientAndConcept(final String patientUuid, final String conceptUuid) throws IOException {
        return observationDao.get(patientUuid, conceptUuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#searchObservations(String, String)
     */
    @Override
    public List<Observation> searchObservations(final String patientUuid, final String term) throws IOException, ParseException {
        return observationDao.search(patientUuid, term);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#deleteObservation(com.muzima.api.model.Observation)
     */
    @Override
    public void deleteObservation(final Observation observation) throws IOException {
        observationDao.delete(observation, Constants.UUID_OBSERVATION_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#deleteObservations(java.util.List)
     */
    @Override
    public void deleteObservations(final List<Observation> observations) throws IOException {
        observationDao.delete(observations, Constants.UUID_OBSERVATION_RESOURCE);
    }
}
