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
package com.mclinic.api.service;

import java.io.File;
import java.net.URL;
import java.util.List;

import com.mclinic.api.model.Patient;
import com.mclinic.api.module.MuzimaModule;
import com.mclinic.search.api.Context;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PatientServiceTest {

    private PatientService patientService;

    private AdministrativeService service;

    @Before
    public void prepare() throws Exception {

        URL lucenePath = AdministrativeServiceTest.class.getResource("../lucene");
        MuzimaModule module = new MuzimaModule(lucenePath.getPath(), "uuid");
        // we set this value for testing only because our mock data come from this server
        // see: the json folder to check for the correct server value
        module.setServer("http://localhost:8081/openmrs-standalone");
        Context.initialize(module);

        URL repositoryPath = AdministrativeServiceTest.class.getResource("../j2l");

        service = Context.getInstance(AdministrativeService.class);
        Assert.assertNotNull(service);

        service.initializeRepository(repositoryPath.getPath());

        URL patientJsonPath = AdministrativeServiceTest.class.getResource("../json/patient");
        service.loadPatients(new File(patientJsonPath.getPath()));

        URL memberJsonPath = AdministrativeServiceTest.class.getResource("../json/cohort_member");
        service.loadCohortPatients(new File(memberJsonPath.getPath()));

        patientService = Context.getInstance(PatientService.class);
        Assert.assertNotNull(patientService);
    }

    @After
    public void cleanUp() {
        URL lucenePath = AdministrativeServiceTest.class.getResource("../lucene");

        File luceneDirectory = new File(lucenePath.getPath());
        for (String filename : luceneDirectory.list()) {
            File file = new File(luceneDirectory, filename);
            Assert.assertTrue(file.delete());
        }
    }

    /**
     * @verifies return patient with matching uuid
     * @see PatientService#getPatientByUuid(String)
     */
    @Test
    public void getPatientByUuid_shouldReturnPatientWithMatchingUuid() throws Exception {
        String uuid = "dd55e586-1691-11df-97a5-7038c432aabf";
        Patient patient = patientService.getPatientByUuid(uuid);
        Assert.assertNotNull(patient);
    }

    /**
     * @verifies return null when no patient match the uuid
     * @see PatientService#getPatientByUuid(String)
     */
    @Test
    public void getPatientByUuid_shouldReturnNullWhenNoPatientMatchTheUuid() throws Exception {
        String randomUuid = "1234";
        Patient patient = patientService.getPatientByUuid(randomUuid);
        Assert.assertNull(patient);
    }

    /**
     * @verifies return patient with matching identifier
     * @see PatientService#getPatientByIdentifier(String)
     */
    @Test
    public void getPatientByIdentifier_shouldReturnPatientWithMatchingIdentifier() throws Exception {
        String identifier = "363MO-5";
        Patient patient = patientService.getPatientByIdentifier(identifier);
        Assert.assertNotNull(patient);
    }

    /**
     * @verifies return null when no patient match the identifier
     * @see PatientService#getPatientByIdentifier(String)
     */
    @Test
    public void getPatientByIdentifier_shouldReturnNullWhenNoPatientMatchTheIdentifier() throws Exception {
        String randomIdentifier = "999KT-3";
        Patient patient = patientService.getPatientByIdentifier(randomIdentifier);
        Assert.assertNull(patient);
    }

    /**
     * @verifies return all registered patients
     * @see PatientService#getAllPatients()
     */
    @Test
    public void getAllPatients_shouldReturnAllRegisteredPatients() throws Exception {
        List<Patient> patients = patientService.getAllPatients();
        Assert.assertNotNull(patients);
        Assert.assertFalse(patients.isEmpty());
    }

    /**
     * @verifies return empty list when no patient is registered
     * @see PatientService#getAllPatients()
     */
    @Test
    public void getAllPatients_shouldReturnEmptyListWhenNoPatientIsRegistered() throws Exception {
        List<Patient> patients = patientService.getAllPatients();
        Assert.assertNotNull(patients);
        Assert.assertFalse(patients.isEmpty());

        while (patients.size() > 0) {
            for (Patient patient : patients)
                patientService.deletePatient(patient);
            patients = patientService.getAllPatients();
        }

        patients = patientService.getAllPatients();
        Assert.assertNotNull(patients);
        Assert.assertTrue(patients.isEmpty());
    }

    /**
     * @verifies return list of all patients with matching name
     * @see PatientService#getPatientsByName(String)
     */
    @Test
    public void getPatientsByName_shouldReturnListOfAllPatientsWithMatchingName() throws Exception {
        String name = "Test";
        List<Patient> patients = patientService.getPatientsByName(name);
        Assert.assertNotNull(patients);
        Assert.assertFalse(patients.isEmpty());
    }

    /**
     * @verifies return empty list when no patient match the name
     * @see PatientService#getPatientsByName(String)
     */
    @Test
    public void getPatientsByName_shouldReturnEmptyListWhenNoPatientMatchTheName() throws Exception {
        String name = "RandomName";
        List<Patient> patients = patientService.getPatientsByName(name);
        Assert.assertNotNull(patients);
        Assert.assertTrue(patients.isEmpty());
    }

    /**
     * @verifies return empty list when no patient match the search term
     * @see PatientService#searchPatients(String)
     */
    @Test
    public void searchPatients_shouldReturnEmptyListWhenNoPatientMatchTheSearchTerm() throws Exception {
        String term = "MAMA";
        List<Patient> patients = patientService.searchPatients(term);
        Assert.assertNotNull(patients);
        Assert.assertEquals(0, patients.size());

    }

    /**
     * @verifies return list of all patients from the cohort
     * @see PatientService#getPatientsByCohort(String)
     */
    @Test
    public void getPatientsByCohort_shouldReturnListOfAllPatientsFromTheCohort() throws Exception {
        String uuid = "0ca78602-737f-408d-8ced-386ad12367db";
        List<Patient> patients = patientService.getPatientsByCohort(uuid);
        Assert.assertNotNull(patients);
        Assert.assertEquals(20, patients.size());
    }

    /**
     * @verifies return empty list when no patient match the cohort uuid
     * @see PatientService#getPatientsByCohort(String)
     */
    @Test
    public void getPatientsByCohort_shouldReturnEmptyListWhenNoPatientMatchTheCohortUuid() throws Exception {
        String uuid = "Some Random Uuid";
        List<Patient> patients = patientService.getPatientsByCohort(uuid);
        Assert.assertNotNull(patients);
        Assert.assertEquals(0, patients.size());
    }

    /**
     * @verifies return list of all patients with matching search term on the searchable fields
     * @see PatientService#searchPatients(String)
     */
    @Test
    public void searchPatients_shouldReturnListOfAllPatientsWithMatchingSearchTermOnTheSearchableFields() throws Exception {
        String term = "513MO";
        List<Patient> patients = patientService.searchPatients(term);
        Assert.assertNotNull(patients);
        Assert.assertEquals(1, patients.size());

        term = "51*";
        patients = patientService.searchPatients(term);
        Assert.assertNotNull(patients);
        Assert.assertEquals(3, patients.size());

        term = "Tes*";
        patients = patientService.searchPatients(term);
        Assert.assertNotNull(patients);
        Assert.assertEquals(3, patients.size());
    }
}