/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Encounter;
import com.muzima.api.model.Patient;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

/**
 * TODO: Write brief description about the class here.
 */
public class EncounterServiceTest {
    private static final String GIVEN_NAME = "Test";
    // baseline encounter
    private Encounter encounter;
    private List<Encounter> encounters;

    private Context context;
    private PatientService patientService;
    private EncounterService encounterService;

    private static int nextInt(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    @Before
    public void prepare() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/muzima/" + UUID.randomUUID().toString();
        ContextFactory.setProperty(Constants.LUCENE_DIRECTORY_PATH, path);
        context = ContextFactory.createContext();
        context.setPreferredLocale("en");
        context.openSession();
        if (!context.isAuthenticated()) {
            context.authenticate("admin", "test", "http://demo2.muzima.org", false);
        }
        patientService = context.getPatientService();
        encounterService = context.getService(EncounterService.class);
        encounters = encounterService.downloadEncountersByPatientName(GIVEN_NAME);
        encounter = encounters.get(nextInt(encounters.size()));
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
     * @verifies return downloaded encounter with matching uuid.
     * @see EncounterService#downloadEncounterByUuid(String)
     */
    @Test
    public void downloadEncounterByUuid_shouldReturnDownloadedEncounterWithMatchingUuid() throws Exception {
        Encounter downloadedEncounter = encounterService.downloadEncounterByUuid(encounter.getUuid());
        assertThat(downloadedEncounter, samePropertyValuesAs(encounter));
    }

    /**
     * @verifies return null when no encounter match the uuid.
     * @see EncounterService#downloadEncounterByUuid(String)
     */
    @Test
    public void downloadEncounterByUuid_shouldReturnNullWhenNoEncounterMatchTheUuid() throws Exception {
        String randomUuid = UUID.randomUUID().toString();
        Encounter downloadedEncounter = encounterService.downloadEncounterByUuid(randomUuid);
        assertThat(downloadedEncounter, not(samePropertyValuesAs(encounter)));
    }

    /**
     * @verifies return encounters for patient with matching name.
     * @see EncounterService#downloadEncountersByPatientName(String)
     */
    @Test
    public void downloadEncountersByPatientName_shouldReturnEncountersForPatientWithMatchingName() throws Exception {
        List<Encounter> downloadedEncounters = encounterService.downloadEncountersByPatientName(GIVEN_NAME);
        List<Patient> downloadedPatients = patientService.downloadPatientsByName(GIVEN_NAME);
        for (Encounter downloadedEncounter : downloadedEncounters) {
            assertThat(downloadedEncounter.getPatient(), isIn(downloadedPatients));
        }
    }

    /**
     * @verifies return empty list when the name is empty.
     * @see EncounterService#downloadEncountersByPatientName(String)
     */
    @Test
    public void downloadEncountersByPatientName_shouldReturnEmptyListWhenTheNameIsEmpty() throws Exception {
        String randomUuid = UUID.randomUUID().toString();
        List<Encounter> downloadedEncounters = encounterService.downloadEncountersByPatientName(randomUuid);
        assertThat(downloadedEncounters, hasSize(0));
    }

    /**
     * @verifies return downloaded list of encounters with matching uuid.
     * @see EncounterService#downloadEncountersByPatientUuid(String)
     */
    @Test
    public void downloadEncountersByPatientUuid_shouldReturnDownloadedListOfEncountersWithMatchingUuid() throws Exception {
        List<Patient> downloadedPatients = patientService.downloadPatientsByName(GIVEN_NAME);
        for (Patient downloadedPatient : downloadedPatients) {
            List<Encounter> downloadedEncounters = encounterService.downloadEncountersByPatientUuid(downloadedPatient.getUuid());
            for (Encounter downloadedEncounter : downloadedEncounters) {
                assertThat(downloadedEncounter.getPatient(), equalTo(downloadedPatient));
            }
        }
    }

    /**
     * @verifies return empty list when the uuid is empty.
     * @see EncounterService#downloadEncountersByPatientUuid(String)
     */
    @Test
    public void downloadEncountersByPatientUuid_shouldReturnEmptyListWhenTheUuidIsEmpty() throws Exception {
        List<Encounter> downloadedEncounters = encounterService.downloadEncountersByPatientUuid(StringUtil.EMPTY);
        assertThat(downloadedEncounters, hasSize(0));
    }

    /**
     * @verifies return encounter with matching uuid.
     * @see EncounterService#getEncounterByUuid(String)
     */
    @Test
    public void getEncounterByUuid_shouldReturnEncounterWithMatchingUuid() throws Exception {
        assertThat(encounterService.countAllEncounters(), equalTo(0));
        encounterService.saveEncounter(encounter);
        Encounter savedEncounter = encounterService.getEncounterByUuid(encounter.getUuid());
        assertThat(savedEncounter, samePropertyValuesAs(encounter));
    }

    /**
     * @verifies return null when no encounter match the uuid.
     * @see EncounterService#getEncounterByUuid(String)
     */
    @Test
    public void getEncounterByUuid_shouldReturnNullWhenNoEncounterMatchTheUuid() throws Exception {
        assertThat(encounterService.countAllEncounters(), equalTo(0));
        encounterService.saveEncounters(encounters);
        assertThat(encounterService.countAllEncounters(), greaterThan(0));
        String randomUuid = UUID.randomUUID().toString();
        Encounter savedEncounter = encounterService.getEncounterByUuid(randomUuid);
        assertThat(savedEncounter, nullValue());
    }

    /**
     * @verifies return list of encounters with matching patient name.
     * @see EncounterService#getEncountersByPatientName(String)
     */
    @Test
    public void getEncountersByPatientName_shouldReturnListOfEncountersWithMatchingPatientName() throws Exception {
        List<Patient> downloadedPatients = patientService.downloadPatientsByName(GIVEN_NAME);
        encounterService.saveEncounters(encounters);
        List<Encounter> savedEncounters = encounterService.getEncountersByPatientName(GIVEN_NAME);
        for (Encounter savedEncounter : savedEncounters) {
            assertThat(savedEncounter.getPatient(), isIn(downloadedPatients));
        }
    }

    /**
     * @verifies return empty list when no encounter match the patient name.
     * @see EncounterService#getEncountersByPatientName(String)
     */
    @Test
    public void getEncountersByPatientName_shouldReturnEmptyListWhenNoEncounterMatchThePatientName() throws Exception {
        String randomPatientName = UUID.randomUUID().toString();
        encounterService.saveEncounters(encounters);
        List<Encounter> savedEncounters = encounterService.getEncountersByPatientName(randomPatientName);
        assertThat(savedEncounters, hasSize(0));
    }

    /**
     * @verifies return all encounters stored in the local data repository.
     * @see EncounterService#getAllEncounters()
     */
    @Test
    public void getAllEncounters_shouldReturnAllEncountersStoredInTheLocalDataRepository() throws Exception {
        assertThat(encounterService.countAllEncounters(), equalTo(0));
        encounterService.saveEncounters(encounters);
        List<Encounter> savedEncounters = encounterService.getAllEncounters();
        assertThat(savedEncounters.size(), equalTo(encounters.size()));
        for (Encounter savedEncounter : savedEncounters) {
            assertThat(savedEncounter, isIn(encounters));
        }
    }

    /**
     * @verifies return number of encounters stored in the local data repository.
     * @see EncounterService#countAllEncounters()
     */
    @Test
    public void countAllEncounters_shouldReturnNumberOfEncountersStoredInTheLocalDataRepository() throws Exception {
        assertThat(encounterService.countAllEncounters(), equalTo(0));
        encounterService.saveEncounters(encounters);
        assertThat(encounterService.countAllEncounters(), equalTo(encounters.size()));
        encounterService.saveEncounters(encounters);
        assertThat(encounterService.countAllEncounters(), equalTo(encounters.size()));
    }

    /**
     * @verifies save encounter into local data repository.
     * @see EncounterService#saveEncounter(com.muzima.api.model.Encounter)
     */
    @Test
    public void saveEncounter_shouldSaveEncounterIntoLocalDataRepository() throws Exception {
        assertThat(encounterService.countAllEncounters(), equalTo(0));
        encounterService.saveEncounter(encounter);
        assertThat(encounterService.countAllEncounters(), equalTo(1));
        Encounter savedEncounter = encounterService.getEncounterByUuid(encounter.getUuid());
        assertThat(savedEncounter, samePropertyValuesAs(encounter));
        encounterService.saveEncounter(encounter);
        assertThat(encounterService.countAllEncounters(), equalTo(1));
    }

    /**
     * @verifies save list of encounters into local data repository.
     * @see EncounterService#saveEncounters(java.util.List)
     */
    @Test
    public void saveEncounters_shouldSaveListOfEncountersIntoLocalDataRepository() throws Exception {
        assertThat(encounterService.countAllEncounters(), equalTo(0));
        encounterService.saveEncounters(encounters);
        List<Encounter> savedEncounters = encounterService.getAllEncounters();
        assertThat(savedEncounters.size(), equalTo(encounters.size()));
        for (Encounter savedEncounter : savedEncounters) {
            assertThat(savedEncounter, isIn(encounters));
        }
        encounterService.saveEncounters(encounters);
        assertThat(encounterService.countAllEncounters(), equalTo(encounters.size()));
    }

    /**
     * @verifies update encounter in local data repository.
     * @see EncounterService#updateEncounter(com.muzima.api.model.Encounter)
     */
    @Test
    public void updateEncounter_shouldUpdateEncounterInLocalDataRepository() throws Exception {
        Encounter nullEncounter = encounterService.getEncounterByUuid(encounter.getUuid());
        assertThat(nullEncounter, nullValue());
        encounterService.saveEncounter(encounter);
        Encounter savedEncounter = encounterService.getEncounterByUuid(encounter.getUuid());
        assertThat(savedEncounter, notNullValue());
        assertThat(savedEncounter, samePropertyValuesAs(encounter));

        Date encounterDatetime = new Date();
        savedEncounter.setEncounterDatetime(encounterDatetime);
        encounterService.updateEncounter(savedEncounter);
        Encounter updatedEncounter = encounterService.getEncounterByUuid(savedEncounter.getUuid());
        assertThat(updatedEncounter, not(samePropertyValuesAs(encounter)));
        assertThat(updatedEncounter.getEncounterDatetime(), equalTo(encounterDatetime));
    }

    /**
     * @verifies update list of encounters in local data repository.
     * @see EncounterService#updateEncounters(java.util.List)
     */
    @Test
    public void updateEncounters_shouldUpdateListOfEncountersInLocalDataRepository() throws Exception {
        Date currentDate = new Date();
        encounterService.saveEncounters(encounters);
        List<Encounter> savedEncounters = encounterService.getAllEncounters();
        for (Encounter encounter : savedEncounters) {
            encounter.setEncounterDatetime(currentDate);
        }
        encounterService.updateEncounters(savedEncounters);
        List<Encounter> updatedEncounters = encounterService.getAllEncounters();
        for (Encounter updatedEncounter : updatedEncounters) {
            for (Encounter encounter : encounters) {
                assertThat(updatedEncounter, not(samePropertyValuesAs(encounter)));
            }
            assertThat(updatedEncounter.getEncounterDatetime(), equalTo(currentDate));
        }
    }

    /**
     * @verifies delete encounter from local data repository.
     * @see EncounterService#deleteEncounter(com.muzima.api.model.Encounter)
     */
    @Test
    public void deleteEncounter_shouldDeleteEncounterFromLocalDataRepository() throws Exception {
        assertThat(encounterService.getAllEncounters(), hasSize(0));
        encounterService.saveEncounters(encounters);
        int encounterCount = encounters.size();
        assertThat(encounterService.getAllEncounters(), hasSize(encounterCount));
        for (Encounter encounter : encounters) {
            encounterService.deleteEncounter(encounter);
            assertThat(encounterService.getAllEncounters(), hasSize(--encounterCount));
        }
    }

    /**
     * @verifies delete list of encounters from local data repository.
     * @see EncounterService#deleteEncounters(java.util.List)
     */
    @Test
    public void deleteEncounters_shouldDeleteListOfEncountersFromLocalDataRepository() throws Exception {
        assertThat(encounterService.getAllEncounters(), hasSize(0));
        encounterService.saveEncounters(encounters);
        List<Encounter> savedEncounters = encounterService.getAllEncounters();
        assertThat(savedEncounters, hasSize(encounters.size()));
        encounterService.deleteEncounters(savedEncounters);
        assertThat(encounterService.getAllEncounters(), hasSize(0));
    }

    /**
     * @verifies return list of encounters with matching patient uuid.
     * @see EncounterService#getEncountersByPatientUuid(String)
     */
    @Test
    public void getEncountersByPatientUuid_shouldReturnListOfEncountersWithMatchingPatientUuid() throws Exception {
        Patient patient = encounter.getPatient();
        assertThat(encounterService.getEncountersByPatientUuid(patient.getUuid()), empty());
        encounterService.saveEncounter(encounter);
        assertThat(encounterService.getEncountersByPatientUuid(patient.getUuid()), not(empty()));
    }

    /**
     * @verifies return empty list when no encounter match the patient uuid.
     * @see EncounterService#getEncountersByPatientUuid(String)
     */
    @Test
    public void getEncountersByPatientUuid_shouldReturnEmptyListWhenNoEncounterMatchThePatientUuid() throws Exception {
        Patient patient = encounter.getPatient();
        assertThat(encounterService.getEncountersByPatientUuid(patient.getUuid()), empty());
        encounterService.saveEncounter(encounter);
        assertThat(encounterService.getEncountersByPatientUuid(patient.getUuid()), not(empty()));
        String randomPatientUuid = UUID.randomUUID().toString();
        assertThat(encounterService.getEncountersByPatientUuid(randomPatientUuid), empty());
    }

    /**
     * @verifies return list of encounters with matching patient.
     * @see EncounterService#getEncountersByPatient(com.muzima.api.model.Patient)
     */
    @Test
    public void getEncountersByPatient_shouldReturnListOfEncountersWithMatchingPatient() throws Exception {
        Patient patient = encounter.getPatient();
        assertThat(encounterService.getEncountersByPatient(patient), empty());
        encounterService.saveEncounter(encounter);
        assertThat(encounterService.getEncountersByPatient(patient), not(empty()));
    }

    /**
     * @verifies return empty list when no encounter match the patient.
     * @see EncounterService#getEncountersByPatient(com.muzima.api.model.Patient)
     */
    @Test
    public void getEncountersByPatient_shouldReturnEmptyListWhenNoEncounterMatchThePatient() throws Exception {
        Patient patient = encounter.getPatient();
        assertThat(encounterService.getEncountersByPatient(patient), empty());
        encounterService.saveEncounter(encounter);
        assertThat(encounterService.getEncountersByPatient(patient), not(empty()));
        Patient randomPatient = new Patient();
        randomPatient.setUuid(UUID.randomUUID().toString());
        assertThat(encounterService.getEncountersByPatient(randomPatient), empty());
    }

    /**
     * @verifies return encounter that matches form data Uuid.
     * @see EncounterService#getEncounterByFormDataUuid(String)
     */
    @Test
    public void getEncounterByFormDataUuid_shouldReturnEncounterThatMatchesFormDataUuid() throws Exception {
        String randomFormDataUuid = UUID.randomUUID().toString();
        assertThat(encounterService.getEncounterByFormDataUuid(randomFormDataUuid),equalTo(null));

        encounter.setFormDataUuid(randomFormDataUuid);
        assertThat(encounter.getFormDataUuid(),equalTo(randomFormDataUuid));
        encounterService.saveEncounter(encounter);
        assertThat(encounterService.getEncounterByFormDataUuid(randomFormDataUuid).getFormDataUuid(),equalTo(randomFormDataUuid));
    }

    /**
     * @verifies return downloaded list of encounters with matching uuid.
     * @see EncounterService#downloadEncountersByPatient(com.muzima.api.model.Patient)
     */
    @Test
    public void downloadEncountersByPatient_shouldReturnDownloadedListOfEncountersWithMatchingUuid() throws Exception {
        Patient patient = encounter.getPatient();
        List<Encounter> downloadedEncounters = encounterService.downloadEncountersByPatient(patient);
        for (Encounter downloadedEncounter : downloadedEncounters) {
            assertThat(downloadedEncounter.getPatient(), equalTo(patient));
        }
    }

    /**
     * @verifies return empty list when the uuid is empty.
     * @see EncounterService#downloadEncountersByPatient(com.muzima.api.model.Patient)
     */
    @Test
    public void downloadEncountersByPatient_shouldReturnEmptyListWhenTheUuidIsEmpty() throws Exception {
        Patient randomPatient = new Patient();
        randomPatient.setUuid(UUID.randomUUID().toString());
        List<Encounter> downloadedEncounters = encounterService.downloadEncountersByPatient(randomPatient);
        assertThat(downloadedEncounters, empty());
    }
}
