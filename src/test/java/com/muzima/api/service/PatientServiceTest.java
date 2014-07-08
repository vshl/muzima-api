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

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Patient;
import com.muzima.api.model.PersonName;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

/**
 * TODO: Write brief description about the class here.
 */
public class PatientServiceTest {
    private static final String GIVEN_NAME = "Test";
    private static final String FAMILY_NAME = "Indakasi";
    // baseline patient
    private Patient patient;
    private List<Patient> patients;

    private Context context;
    private PatientService patientService;
    private CohortService cohortService;

    private static int nextInt(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    @Before
    public void prepare() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/muzima/" + UUID.randomUUID().toString();
        ContextFactory.setProperty(Constants.LUCENE_DIRECTORY_PATH, path);
        context = ContextFactory.createContext();
        context.openSession();
        if (!context.isAuthenticated()) {
            context.authenticate("admin", "test", "http://localhost:8081/openmrs-standalone");
        }
        patientService = context.getPatientService();
        cohortService = context.getCohortService();
        patients = patientService.downloadPatientsByName(GIVEN_NAME);
        patient = patients.get(nextInt(patients.size()));
    }

    @After
    public void cleanUp() throws Exception {
        String lucenePath = ContextFactory.getProperty(Constants.LUCENE_DIRECTORY_PATH);
        File luceneDirectory = new File(lucenePath);
        for (String filename : luceneDirectory.list()) {
            File file = new File(luceneDirectory, filename);
            Assert.assertTrue(file.delete());
        }
        context.deauthenticate();
        context.closeSession();
    }

    /**
     * @verifies download patient with matching uuid.
     * @see PatientService#downloadPatientByUuid(String)
     */
    @Test
    public void downloadPatientByUuid_shouldDownloadPatientWithMatchingUuid() throws Exception {
        Patient downloadedPatient = patientService.downloadPatientByUuid(patient.getUuid());
        assertThat(downloadedPatient, samePropertyValuesAs(patient));
    }

    /**
     * @verifies download all patient with partially matched name.
     * @see PatientService#downloadPatientsByName(String)
     */
    @Test
    public void downloadPatientsByName_shouldDownloadAllPatientWithPartiallyMatchedName() throws Exception {
        String name = FAMILY_NAME;
        String partialName = name.substring(0, name.length() - 1);
        List<Patient> downloadedPatients = patientService.downloadPatientsByName(partialName);
        for (Patient downloadedPatient : downloadedPatients) {
            assertThat(downloadedPatient.getFamilyName(), containsString(partialName));
        }
    }

    /**
     * @verifies save patient to local data repository.
     * @see PatientService#savePatient(com.muzima.api.model.Patient)
     */
    @Test
    public void savePatient_shouldSavePatientToLocalDataRepository() throws Exception {
        int cohortCounter = patientService.countAllPatients();
        patientService.savePatient(patient);
        assertThat(patientService.countAllPatients(), equalTo(cohortCounter + 1));
        patientService.savePatient(patient);
        assertThat(patientService.countAllPatients(), equalTo(cohortCounter + 1));
    }

    /**
     * @verifies save patients to local data repository.
     * @see PatientService#savePatients(java.util.List)
     */
    @Test
    public void savePatients_shouldSavePatientsToLocalDataRepository() throws Exception {
        int cohortCounter = patientService.countAllPatients();
        patientService.savePatients(patients);
        assertThat(patientService.countAllPatients(), equalTo(cohortCounter + patients.size()));
        patientService.savePatients(patients);
        assertThat(patientService.countAllPatients(), equalTo(cohortCounter + patients.size()));
    }

    /**
     * @verifies replace existing patient in local data repository.
     * @see PatientService#updatePatient(com.muzima.api.model.Patient)
     */
    @Test
    public void updatePatient_shouldReplaceExistingPatientInLocalDataRepository() throws Exception {
        Patient nullPatient = patientService.getPatientByUuid(patient.getUuid());
        assertThat(nullPatient, nullValue());
        patientService.savePatient(patient);
        Patient savedPatient = patientService.getPatientByUuid(patient.getUuid());
        assertThat(savedPatient, notNullValue());
        assertThat(savedPatient, samePropertyValuesAs(patient));
        String cohortName = "New Patient Gender";
        savedPatient.setGender(cohortName);
        patientService.updatePatient(savedPatient);
        Patient updatedPatient = patientService.getPatientByUuid(savedPatient.getUuid());
        assertThat(updatedPatient, not(samePropertyValuesAs(patient)));
        assertThat(updatedPatient.getGender(), equalTo(cohortName));
    }

    /**
     * @verifies replace existing patients in local data repository.
     * @see PatientService#updatePatients(java.util.List)
     */
    @Test
    public void updatePatients_shouldReplaceExistingPatientsInLocalDataRepository() throws Exception {
        int counter = 0;
        patientService.savePatients(patients);
        List<Patient> savedPatients = patientService.getAllPatients();
        for (Patient cohort : savedPatients) {
            cohort.setGender("New Gender For Patient: " + counter++);
        }
        patientService.updatePatients(savedPatients);
        List<Patient> updatedPatients = patientService.getAllPatients();
        for (Patient updatedPatient : updatedPatients) {
            for (Patient cohort : patients) {
                assertThat(updatedPatient, not(samePropertyValuesAs(cohort)));
            }
            assertThat(updatedPatient.getGender(), containsString("New Gender For Patient"));
        }
    }

    /**
     * @verifies return patient with matching uuid
     * @see PatientService#getPatientByUuid(String)
     */
    @Test
    public void getPatientByUuid_shouldReturnPatientWithMatchingUuid() throws Exception {
        Patient nullPatient = patientService.getPatientByUuid(patient.getUuid());
        assertThat(nullPatient, nullValue());
        patientService.savePatient(patient);
        Patient savedPatient = patientService.getPatientByUuid(patient.getUuid());
        assertThat(savedPatient, notNullValue());
        assertThat(savedPatient, samePropertyValuesAs(patient));
    }

    /**
     * @verifies return null when no patient match the uuid
     * @see PatientService#getPatientByUuid(String)
     */
    @Test
    public void getPatientByUuid_shouldReturnNullWhenNoPatientMatchTheUuid() throws Exception {
        Patient nullPatient = patientService.getPatientByUuid(patient.getUuid());
        assertThat(nullPatient, nullValue());
        patientService.savePatient(patient);
        String randomUuid = UUID.randomUUID().toString();
        Patient savedPatient = patientService.getPatientByUuid(randomUuid);
        assertThat(savedPatient, nullValue());
    }

    /**
     * @verifies return patient with matching identifier.
     * @see PatientService#getPatientByIdentifier(String)
     */
    @Test
    public void getPatientByIdentifier_shouldReturnPatientWithMatchingIdentifier() throws Exception {
        assertThat(patientService.countAllPatients(), equalTo(0));
        patientService.savePatient(patient);
        Patient savedPatient = patientService.getPatientByIdentifier(patient.getIdentifier());
        assertThat(savedPatient, samePropertyValuesAs(patient));
    }

    /**
     * @verifies return null when no patient match the identifier.
     * @see PatientService#getPatientByIdentifier(String)
     */
    @Test
    public void getPatientByIdentifier_shouldReturnNullWhenNoPatientMatchTheIdentifier() throws Exception {
        assertThat(patientService.countAllPatients(), equalTo(0));
        patientService.savePatient(patient);
        String randomIdentifier = UUID.randomUUID().toString();
        Patient savedPatient = patientService.getPatientByIdentifier(randomIdentifier);
        assertThat(savedPatient, nullValue());
    }

    /**
     * @verifies return all registered patients.
     * @see PatientService#getAllPatients()
     */
    @Test
    public void getAllPatients_shouldReturnAllRegisteredPatients() throws Exception {
        assertThat(patientService.countAllPatients(), equalTo(0));
        patientService.savePatients(patients);
        List<Patient> savedPatients = patientService.getPatientsByName(StringUtil.EMPTY);
        assertThat(savedPatients, hasSize(patients.size()));
    }

    /**
     * @verifies return empty list when no patient is registered.
     * @see PatientService#getAllPatients()
     */
    @Test
    public void getAllPatients_shouldReturnEmptyListWhenNoPatientIsRegistered() throws Exception {
        assertThat(patientService.countAllPatients(), equalTo(0));
    }

    /**
     * @verifies return list of all patients with matching name partially.
     * @see PatientService#getPatientsByName(String)
     */
    @Test
    public void getPatientsByName_shouldReturnListOfAllPatientsWithMatchingNamePartially() throws Exception {
        assertThat(patientService.countAllPatients(), equalTo(0));
        patientService.savePatient(patient);
        List<Patient> savedPatients = patientService.getPatientsByName(patient.getFamilyName());
        assertThat(savedPatients.size(), equalTo(1));
        assertThat(patient, isIn(savedPatients));
    }

    /**
     * @verifies return empty list when no patient match the name.
     * @see PatientService#getPatientsByName(String)
     */
    @Test
    public void getPatientsByName_shouldReturnEmptyListWhenNoPatientMatchTheName() throws Exception {
        String randomName = UUID.randomUUID().toString();
        patientService.savePatient(patient);
        List<Patient> savedPatients = patientService.getPatientsByName(randomName);
        assertThat(savedPatients.size(), equalTo(0));
        assertThat(patient, not(isIn(savedPatients)));
    }

    /**
     * @verifies return list of all patients with matching search term.
     * @see PatientService#searchPatients(String)
     */
    @Test
    public void searchPatients_shouldReturnListOfAllPatientsWithMatchingSearchTerm() throws Exception {
        patientService.savePatients(patients);
        String name = FAMILY_NAME;
        String partialName = name.substring(0, name.length() - 1);
        List<Patient> patientsByName = patientService.searchPatients(partialName);
        assertThat(patientsByName.size(), equalTo(1));
        String identifier = patient.getIdentifier();
        String partialIdentifier = identifier.substring(0, identifier.length() - 1);
        List<Patient> patientsByIdentifier = patientService.searchPatients(partialIdentifier);
        assertThat(patientsByIdentifier.size(), equalTo(1));
        assertThat(patient, isIn(patientsByIdentifier));
    }

    /**
     * @verifies return empty list when no patient match the search term.
     * @see PatientService#searchPatients(String)
     */
    @Test
    public void searchPatients_shouldReturnEmptyListWhenNoPatientMatchTheSearchTerm() throws Exception {
        String randomName = UUID.randomUUID().toString();
        patientService.savePatient(patient);
        List<Patient> savedPatients = patientService.searchPatients(randomName);
        assertThat(savedPatients.size(), equalTo(0));
        assertThat(patient, not(isIn(savedPatients)));
    }

    /**
     * @verifies return list of all patients in cohort with matching search term.
     * @see PatientService#searchPatients(String, String)
     */
    @Test
    public void searchPatients_shouldReturnListOfAllPatientsInCohortWithMatchingSearchTerm() throws Exception {
        String randomName = UUID.randomUUID().toString();
        patientService.savePatient(patient);
        List<Patient> savedPatients = patientService.getPatientsByName(randomName);
        assertThat(savedPatients.size(), equalTo(0));
        assertThat(patient, not(isIn(savedPatients)));
    }

    /**
     * @verifies delete the patient object from the local repository.
     * @see PatientService#deletePatient(com.muzima.api.model.Patient)
     */
    @Test
    public void deletePatient_shouldDeleteThePatientObjectFromTheLocalRepository() throws Exception {
        assertThat(patientService.getAllPatients(), hasSize(0));
        patientService.savePatients(patients);
        int cohortCount = patients.size();
        assertThat(patientService.getAllPatients(), hasSize(cohortCount));
        for (Patient cohort : patients) {
            patientService.deletePatient(cohort);
            assertThat(patientService.getAllPatients(), hasSize(--cohortCount));
        }
    }

    /**
     * @verifies delete the patient object from the local repository.
     * @see PatientService#deletePatients(java.util.List)
     */
    @Test
    public void deletePatients_shouldDeleteThePatientObjectFromTheLocalRepository() throws Exception {
        assertThat(patientService.getAllPatients(), hasSize(0));
        patientService.savePatients(patients);
        List<Patient> savedPatients = patientService.getAllPatients();
        assertThat(savedPatients, hasSize(patients.size()));
        patientService.deletePatients(savedPatients);
        assertThat(patientService.getAllPatients(), hasSize(0));
    }

    @Test
    public void savePatient_shouldReturnNullWhenPatientAlreadyExists() throws Exception {
        Patient patient = new Patient();
        patient.setUuid("uuid1");
        assertThat(patientService.savePatient(patient), notNullValue());
        assertThat(patientService.savePatient(patient), nullValue());
    }

    @Test
    public void shouldReturnPatientsThatAreNotInCohort() throws IOException {
        Cohort cohort = new Cohort();
        cohort.setUuid("cohortUUID");
        cohortService.saveCohort(cohort);

        Patient patient1 = patient("uuid1");
        Patient patient2 = patient("uuid2");

        patientService.savePatient(patient1);
        patientService.savePatient(patient2);
        cohortService.saveCohortMember(new CohortMember(cohort, patient1));

        List<Patient> patientsNotInCohorts = patientService.getPatientsNotInCohorts();

        assertThat(patientsNotInCohorts.size(), is(1));
        assertThat(patientsNotInCohorts.get(0).getUuid(), is("uuid2"));
    }

    @Test
    public void shouldReturnEmptyPatientListIfAllPatientBelongsToSomeCohort() throws Exception {
        Cohort cohort = new Cohort();
        cohort.setUuid("cohortUUID");
        cohortService.saveCohort(cohort);

        patientService.savePatient(patient("uuid1"));
        cohortService.saveCohortMember(new CohortMember(cohort, patient("uuid1")));

        List<Patient> patientsNotInCohorts = patientService.getPatientsNotInCohorts();
        assertThat(patientsNotInCohorts.size(), is(0));
    }

    private Patient patient(String uuid) {
        Patient patient = new Patient();
        patient.setUuid(uuid);
        return patient;
    }

    @Test
    public void shouldSortPatientsByDisplayName() throws Exception {
        Patient patient1 = getPatientWith("Alpha", "greek", "middle");
        Patient patient2 = getPatientWith("Beta", "greek", "middle");
        Patient patient3 = getPatientWith(null, "greek", "middle");
        Patient patient4 = getPatientWith("Alpha", "latin", "middle");
        patientService.savePatient(patient1);
        patientService.savePatient(patient2);
        patientService.savePatient(patient3);
        patientService.savePatient(patient4);

        List<Patient> allPatients = patientService.getAllPatients();
        assertThat(allPatients.size(), is(4));
        assertThat(allPatients.get(0).getFamilyName(), is(patient1.getFamilyName()));
        assertThat(allPatients.get(1).getFamilyName(), is(patient4.getFamilyName()));
        assertThat(allPatients.get(2).getFamilyName(), is(patient2.getFamilyName()));
        assertThat(allPatients.get(3).getFamilyName(), is(nullValue()));

    }

    private Patient getPatientWith(String familyName, String givenName, String middle) {
        Patient patient1 = new Patient();
        patient1.setUuid(UUID.randomUUID().toString());
        PersonName personName = new PersonName();
        personName.setFamilyName(familyName);
        personName.setGivenName(givenName);
        personName.setMiddleName(middle);
        patient1.setNames(asList(personName));
        return patient1;
    }
}
