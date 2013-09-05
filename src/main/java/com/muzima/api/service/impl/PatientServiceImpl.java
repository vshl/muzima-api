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
import com.muzima.api.dao.PatientDao;
import com.muzima.api.model.Patient;
import com.muzima.api.service.PatientService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientServiceImpl implements PatientService {

    @Inject
    private PatientDao patientDao;

    protected PatientServiceImpl() {
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#downloadPatientByUuid(String)
     */
    @Override
    public Patient downloadPatientByUuid(final String uuid) throws IOException {
        Patient patient = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<Patient> patients = patientDao.download(parameter, Constants.UUID_PATIENT_RESOURCE);
        if (!CollectionUtil.isEmpty(patients)) {
            if (patients.size() > 1) {
                throw new IOException("Unable to uniquely identify a patient record.");
            }
            patient = patients.get(0);
        }
        return patient;
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#downloadPatientsByName(String)
     */
    @Override
    public List<Patient> downloadPatientsByName(final String name) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        return patientDao.download(parameter, Constants.SEARCH_PATIENT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#savePatient(com.muzima.api.model.Patient)
     */
    @Override
    public void savePatient(final Patient patient) throws IOException {
        patientDao.save(patient, Constants.UUID_PATIENT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#savePatients(java.util.List)
     */
    @Override
    public void savePatients(final List<Patient> patients) throws IOException {
        patientDao.save(patients, Constants.UUID_PATIENT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#updatePatient(com.muzima.api.model.Patient)
     */
    @Override
    public void updatePatient(final Patient patient) throws IOException {
        patientDao.update(patient, Constants.UUID_PATIENT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#updatePatients(java.util.List)
     */
    @Override
    public void updatePatients(final List<Patient> patients) throws IOException {
        patientDao.update(patients, Constants.UUID_PATIENT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#getPatientByUuid(String)
     */
    @Override
    public Patient getPatientByUuid(final String uuid) throws IOException {
        return patientDao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#getPatientByIdentifier(String)
     */
    @Override
    public Patient getPatientByIdentifier(final String identifier) throws IOException {
        return patientDao.getByIdentifier(identifier);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.PatientService#countAllPatients()
     */
    @Override
    public Integer countAllPatients() throws IOException {
        return patientDao.countAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.PatientService#getAllPatients()
     */
    @Override
    public List<Patient> getAllPatients() throws IOException {
        return patientDao.getAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#getPatientsByName(String)
     */
    @Override
    public List<Patient> getPatientsByName(final String name) throws IOException, ParseException {
        return patientDao.getByName(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#searchPatients(String)
     */
    @Override
    public List<Patient> searchPatients(final String term) throws IOException, ParseException {
        return patientDao.search(term);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#searchPatients(String, String)
     */
    @Override
    public List<Patient> searchPatients(final String term, final String cohortUuid) throws IOException, ParseException {
        return patientDao.search(term, cohortUuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#deletePatient(com.muzima.api.model.Patient)
     */
    @Override
    public void deletePatient(final Patient patient) throws IOException {
        patientDao.delete(patient, Constants.UUID_PATIENT_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#deletePatients(java.util.List)
     */
    @Override
    public void deletePatients(final List<Patient> patients) throws IOException {
        patientDao.delete(patients, Constants.UUID_PATIENT_RESOURCE);
    }
}
