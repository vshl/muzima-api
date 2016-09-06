/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.MemberDao;
import com.muzima.api.dao.PatientDao;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Patient;
import com.muzima.api.service.CohortService;
import com.muzima.api.service.PatientService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientServiceImpl implements PatientService {

    @Inject
    private PatientDao patientDao;

    @Inject
    private MemberDao memberDao;

    @Inject
    private CohortService cohortService;

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
        return sortDisplayNameAscending(patientDao.download(parameter, Constants.SEARCH_PATIENT_RESOURCE));
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#consolidateTemporaryPatient(String)
     */
    @Override
    public Patient consolidateTemporaryPatient(final String temporaryUuid) throws IOException {
        Patient patient = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", temporaryUuid);
        }};
        List<Patient> patients = patientDao.download(parameter, Constants.UUID_REGISTRATION_RESOURCE);
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
     * @see PatientService#consolidateTemporaryPatient(com.muzima.api.model.Patient)
     */
    @Override
    public Patient consolidateTemporaryPatient(final Patient temporaryPatient) throws IOException {
        Patient patient = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", temporaryPatient.getUuid());
        }};
        List<Patient> patients = patientDao.download(parameter, Constants.UUID_REGISTRATION_RESOURCE);
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
     * @see PatientService#savePatient(com.muzima.api.model.Patient)
     */
    @Override
    public Patient savePatient(final Patient patient) throws IOException {
        if (!patientExists(patient)) {
            patientDao.save(patient, Constants.UUID_PATIENT_RESOURCE);
            return patient;
        }
        return null;
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
     * @see com.muzima.api.service.PatientService#countAllPatients(String)
     */
    @Override
    public Integer countPatients(final String cohortUuid) throws IOException {
        return cohortService.countCohortMembers(cohortUuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.PatientService#getAllPatients()
     */
    @Override
    public List<Patient> getAllPatients() throws IOException {
        return sortDisplayNameAscending(patientDao.getAll());
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.PatientService#getAllPatients(Integer, Integer)
     */
    @Override
    public List<Patient> getPatients(final Integer page,
                                        final Integer pageSize) throws IOException {
        return sortDisplayNameAscending(patientDao.getAll(page, pageSize));
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.PatientService#getAllPatients(String, Integer, Integer)
     */
    @Override
    public List<Patient> getPatients(final String cohortUuid,
                                        final Integer page,
                                        final Integer pageSize) throws IOException {
        List<CohortMember> cohortMembers = cohortService.getCohortMembers(cohortUuid, page, pageSize);
        return getPatientsFromCohortMembers(cohortMembers);
    }


    /**
     * {@inheritDoc}
     *
     * @see PatientService#getPatientsByName(String)
     */
    @Override
    public List<Patient> getPatientsByName(final String name) throws IOException, ParseException {
        return sortDisplayNameAscending(patientDao.getPatientByName(name));
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#searchPatients(String)
     */
    @Override
    public List<Patient> searchPatients(final String term) throws IOException, ParseException {
        return sortDisplayNameAscending(patientDao.search(term));
    }

    @Override
    public List<Patient> searchPatients(final String term, final Integer page,
                                        final Integer pageSize) throws IOException, ParseException {
        return sortDisplayNameAscending(patientDao.search(term, page, pageSize));
    }

    /**
     * {@inheritDoc}
     *
     * @see PatientService#searchPatients(String, String)
     */
    @Override
    public List<Patient> searchPatients(final String term, final String cohortUuid) throws IOException, ParseException {
        return sortDisplayNameAscending(patientDao.search(term, cohortUuid));
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

    @Override
    public List<Patient> getPatientsNotInCohorts() throws IOException {
        List<Patient> patientsNotInCohorts = new ArrayList<Patient>();
        for (Patient patient : getAllPatients()) {
            if (isNotAPartOfAnyCohort(patient)) {
                patientsNotInCohorts.add(patient);
            }
        }
        return sortDisplayNameAscending(patientsNotInCohorts);
    }

    @Override
    public List<Patient> getPatientsFromCohortMembers(List<CohortMember> cohortMembers) {
        List<Patient> patients = new ArrayList<Patient>();
        for (CohortMember member : cohortMembers) {
            patients.add(member.getPatient());
        }
        return sortDisplayNameAscending(patients);
    }

    private boolean isNotAPartOfAnyCohort(Patient patient) throws IOException {
        return memberDao.countByPatientUUID(patient.getUuid()) == 0;
    }

    private boolean patientExists(Patient patient) throws IOException {
        return patientDao.getByUuid(patient.getUuid()) != null;
    }

    private List<Patient> sortDisplayNameAscending(List<Patient> patientList) {
        Collections.sort(patientList);
        return patientList;
    }
}
