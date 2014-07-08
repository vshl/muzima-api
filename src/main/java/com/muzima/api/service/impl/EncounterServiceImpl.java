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
import com.muzima.api.dao.EncounterDao;
import com.muzima.api.dao.PatientDao;
import com.muzima.api.model.Encounter;
import com.muzima.api.model.Patient;
import com.muzima.api.service.EncounterService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;
import com.muzima.util.DateUtils;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public class EncounterServiceImpl implements EncounterService {

    @Inject
    private PatientDao patientDao;

    @Inject
    private EncounterDao encounterDao;

    protected EncounterServiceImpl() {
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#downloadEncounterByUuid(String)
     */
    @Override
    public Encounter downloadEncounterByUuid(final String uuid) throws IOException {
        Encounter encounter = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        String resourceName = Constants.UUID_ENCOUNTER_RESOURCE;
        List<Encounter> encounters = encounterDao.download(parameter, resourceName);
        if (!CollectionUtil.isEmpty(encounters)) {
            if (encounters.size() > 1) {
                throw new IOException("Unable to uniquely identify an encounter record.");
            }
            encounter = encounters.get(0);
        }
        return encounter;
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#downloadEncountersByPatientName(String)
     */
    @Override
    public List<Encounter> downloadEncountersByPatientName(final String name) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        return encounterDao.download(parameter, Constants.SEARCH_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#downloadEncountersByPatientUuid(String)
     */
    @Override
    public List<Encounter> downloadEncountersByPatientUuid(final String patientUuid) throws IOException {
        return downloadEncountersByPatientUuidAndSyncDate(patientUuid, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#downloadEncountersByPatientUuid(String)
     */
    @Override
    public List<Encounter> downloadEncountersByPatientUuidAndSyncDate(final String patientUuid, final Date syncDate) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("patient", patientUuid);
        }};
        if (syncDate != null) {
            parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
        }
        return encounterDao.download(parameter, Constants.SEARCH_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterService#downloadEncountersByPatient(com.muzima.api.model.Patient)
     */
    @Override
    public List<Encounter> downloadEncountersByPatient(final Patient patient) throws IOException {
        return downloadEncountersByPatientUuid(patient.getUuid());
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterService#downloadEncountersByPatient(com.muzima.api.model.Patient)
     */
    @Override
    public List<Encounter> downloadEncountersByPatient(final Patient patient, final Date syncDate) throws IOException {
        return downloadEncountersByPatientUuidAndSyncDate(patient.getUuid(), syncDate);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterService#downloadEncountersByPatients(java.util.List)
     */
    @Override
    public List<Encounter> downloadEncountersByPatients(final List<Patient> patients) throws IOException {
        return downloadEncountersByPatientsAndSyncDate(patients, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterService#downloadEncountersByPatients(java.util.List)
     */
    @Override
    public List<Encounter> downloadEncountersByPatientsAndSyncDate(final List<Patient> patients, final Date syncDate) throws IOException {
        final StringBuilder patientBuilder = new StringBuilder();
        for (Patient patient : patients) {
            if (patientBuilder.length() > 0) {
                patientBuilder.append(",");
            }
            patientBuilder.append(patient.getUuid());
        }
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("patient", patientBuilder.toString());
        }};
        if (syncDate != null) {
            parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
        }
        return encounterDao.download(parameter, Constants.SEARCH_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterService#downloadEncountersByPatientUuids(java.util.List)
     */
    @Override
    public List<Encounter> downloadEncountersByPatientUuids(final List<String> patientUuids) throws IOException {
        return downloadEncountersByPatientUuidsAndSyncDate(patientUuids, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterService#downloadEncountersByPatientUuids(java.util.List)
     */
    @Override
    public List<Encounter> downloadEncountersByPatientUuidsAndSyncDate(final List<String> patientUuids, final Date syncDate) throws IOException {
        final StringBuilder patientBuilder = new StringBuilder();
        for (String patientUuid : patientUuids) {
            if (patientBuilder.length() > 0) {
                patientBuilder.append(",");
            }
            patientBuilder.append(patientUuid);
        }
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("patient", patientBuilder.toString());
        }};
        if (syncDate != null) {
            parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
        }
        return encounterDao.download(parameter, Constants.SEARCH_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#getEncounterByUuid(String)
     */
    @Override
    public Encounter getEncounterByUuid(final String uuid) throws IOException {
        return encounterDao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#getEncountersByPatientName(String)
     */
    @Override
    public List<Encounter> getEncountersByPatientName(final String name) throws IOException, ParseException {
        List<Encounter> encounters = new ArrayList<Encounter>();
        List<Patient> patients = patientDao.getPatientByName(name);
        for (Patient patient : patients) {
            encounters.addAll(getEncountersByPatientUuid(patient.getUuid()));
        }
        return encounterDao.getByName(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#getEncountersByPatientUuid(String)
     */
    @Override
    public List<Encounter> getEncountersByPatientUuid(final String patientUuid) throws IOException {
        return encounterDao.getEncountersByPatientUuid(patientUuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see EncounterService#getEncountersByPatient(com.muzima.api.model.Patient)
     */
    @Override
    public List<Encounter> getEncountersByPatient(final Patient patient) throws IOException {
        return encounterDao.getEncountersByPatientUuid(patient.getUuid());
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#getAllEncounters()
     */
    @Override
    public List<Encounter> getAllEncounters() throws IOException {
        return encounterDao.getAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#countAllEncounters()
     */
    @Override
    public Integer countAllEncounters() throws IOException {
        return encounterDao.countAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#saveEncounter(com.muzima.api.model.Encounter)
     */
    @Override
    public void saveEncounter(final Encounter encounter) throws IOException {
        encounterDao.save(encounter, Constants.UUID_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#saveEncounters(java.util.List)
     */
    @Override
    public void saveEncounters(final List<Encounter> encounters) throws IOException {
        encounterDao.save(encounters, Constants.UUID_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#updateEncounter(com.muzima.api.model.Encounter)
     */
    @Override
    public void updateEncounter(final Encounter encounter) throws IOException {
        encounterDao.update(encounter, Constants.UUID_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#updateEncounters(java.util.List)
     */
    @Override
    public void updateEncounters(final List<Encounter> encounters) throws IOException {
        encounterDao.update(encounters, Constants.UUID_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#deleteEncounter(com.muzima.api.model.Encounter)
     */
    @Override
    public void deleteEncounter(final Encounter encounter) throws IOException {
        encounterDao.delete(encounter, Constants.UUID_ENCOUNTER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.EncounterService#deleteEncounters(java.util.List)
     */
    @Override
    public void deleteEncounters(final List<Encounter> encounters) throws IOException {
        encounterDao.delete(encounters, Constants.UUID_ENCOUNTER_RESOURCE);
    }
}
