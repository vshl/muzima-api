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
import com.muzima.api.dao.ObservationDao;
import com.muzima.api.model.Concept;
import com.muzima.api.model.Observation;
import com.muzima.api.model.Patient;
import com.muzima.api.service.ObservationService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservationServiceImpl implements ObservationService {

    @Inject
    private ConceptDao conceptDao;

    @Inject
    private ObservationDao observationDao;

    protected ObservationServiceImpl() {
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#downloadObservationsByPatientAndConcept(com.muzima.api.model.Patient, com.muzima.api.model.Concept)
     */
    @Override
    public List<Observation> downloadObservationsByPatientAndConcept(final Patient patient,
                                                                     final Concept concept) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("person", patient.getUuid());
            put("concept", concept.getUuid());
        }};
        String resourceName = Constants.SEARCH_OBSERVATION_NON_CODED_RESOURCE;
        if (concept.isCoded()) {
            resourceName = Constants.SEARCH_OBSERVATION_CODED_RESOURCE;
        }
        return observationDao.download(parameter, resourceName);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#downloadObservationsByPatientAndConcept(com.muzima.api.model.Patient, com.muzima.api.model.Concept)
     */
    @Override
    public List<Observation> downloadObservationsByPatientAndConcept(final String patientUuid,
                                                                     final String conceptUuid) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("person", patientUuid);
            put("concept", conceptUuid);
        }};
        Concept concept = conceptDao.getByUuid(conceptUuid);
        String resourceName = Constants.SEARCH_OBSERVATION_NON_CODED_RESOURCE;
        if (concept != null && concept.isCoded()) {
            resourceName = Constants.SEARCH_OBSERVATION_CODED_RESOURCE;
        }
        return observationDao.download(parameter, resourceName);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#downloadObservationsByPatientsAndConcepts(java.util.List, java.util.List)
     */
    public List<Observation> downloadObservationsByPatientsAndConcepts(final List<Patient> patients,
                                                                       final List<Concept> concepts) throws IOException {
        final StringBuilder personBuilder = new StringBuilder();
        for (Patient patient : patients) {
            if (personBuilder.length() > 0) {
                personBuilder.append(",");
            }
            personBuilder.append(patient.getUuid());
        }
        final StringBuilder codedBuilder = new StringBuilder();
        final StringBuilder nonCodedBuilder = new StringBuilder();
        for (Concept concept : concepts) {
            if (concept.isCoded()) {
                if (codedBuilder.length() > 0) {
                    codedBuilder.append(",");
                }
                codedBuilder.append(concept.getUuid());
            } else {
                if (nonCodedBuilder.length() > 0) {
                    nonCodedBuilder.append(",");
                }
                nonCodedBuilder.append(concept.getUuid());
            }
        }
        Map<String, String> codedParameter = new HashMap<String, String>() {{
            put("person", personBuilder.toString());
            put("concept", codedBuilder.toString());
        }};
        Map<String, String> nonCodedParameter = new HashMap<String, String>() {{
            put("person", personBuilder.toString());
            put("concept", nonCodedBuilder.toString());
        }};
        List<Observation> codedObservations = observationDao.download(codedParameter,
                Constants.SEARCH_OBSERVATION_CODED_RESOURCE);
        List<Observation> nonCodedObservations = observationDao.download(nonCodedParameter,
                Constants.SEARCH_OBSERVATION_NON_CODED_RESOURCE);
        List<Observation> observations = new ArrayList<Observation>();
        observations.addAll(codedObservations);
        observations.addAll(nonCodedObservations);
        return observations;
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#downloadObservationsByPatientUuidsAndConceptUuids(java.util.List, java.util.List)
     */
    public List<Observation> downloadObservationsByPatientUuidsAndConceptUuids(final List<String> patientUuids,
                                                                               final List<String> conceptUuids) throws IOException {
        final StringBuilder personBuilder = new StringBuilder();
        for (String patientUuid : patientUuids) {
            if (personBuilder.length() > 0) {
                personBuilder.append(",");
            }
            personBuilder.append(patientUuid);
        }
        final StringBuilder codedBuilder = new StringBuilder();
        final StringBuilder nonCodedBuilder = new StringBuilder();
        for (String conceptUuid : conceptUuids) {
            Concept concept = conceptDao.getByUuid(conceptUuid);
            if (concept.isCoded()) {
                if (codedBuilder.length() > 0) {
                    codedBuilder.append(",");
                }
                codedBuilder.append(concept.getUuid());
            } else {
                if (nonCodedBuilder.length() > 0) {
                    nonCodedBuilder.append(",");
                }
                nonCodedBuilder.append(concept.getUuid());
            }
        }
        Map<String, String> codedParameter = new HashMap<String, String>() {{
            put("person", personBuilder.toString());
            put("concept", codedBuilder.toString());
        }};
        Map<String, String> nonCodedParameter = new HashMap<String, String>() {{
            put("person", personBuilder.toString());
            put("concept", nonCodedBuilder.toString());
        }};
        List<Observation> codedObservations = observationDao.download(codedParameter,
                Constants.SEARCH_OBSERVATION_CODED_RESOURCE);
        List<Observation> nonCodedObservations = observationDao.download(nonCodedParameter,
                Constants.SEARCH_OBSERVATION_NON_CODED_RESOURCE);
        List<Observation> observations = new ArrayList<Observation>();
        observations.addAll(codedObservations);
        observations.addAll(nonCodedObservations);
        return observations;
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
        return observationDao.get(patientUuid, StringUtil.EMPTY);
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#getObservationsByPatient(String)
     */
    @Override
    public List<Observation> getObservationsByPatient(final Patient patient) throws IOException {
        return getObservationsByPatient(patient.getUuid());
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
     * @see ObservationService#getObservationsByPatientAndConcept(String, String)
     */
    @Override
    public List<Observation> getObservationsByPatientAndConcept(final Patient patient, final Concept concept) throws IOException {
        return getObservationsByPatientAndConcept(patient.getUuid(), concept.getUuid());
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#searchObservations(String, String)
     */
    @Override
    public List<Observation> searchObservations(final String patientUuid, final String term) throws IOException {
        List<Concept> concepts = conceptDao.getByName(term);
        List<Observation> observations = new ArrayList<Observation>();
        for (Concept concept : concepts) {
            observations.addAll(getObservationsByPatientAndConcept(patientUuid, concept.getUuid()));
        }
        return observations;
    }

    /**
     * {@inheritDoc}
     *
     * @see ObservationService#searchObservations(String, String)
     */
    @Override
    public List<Observation> searchObservations(final Patient patient, final String term) throws IOException {
        return searchObservations(patient.getUuid(), term);
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
