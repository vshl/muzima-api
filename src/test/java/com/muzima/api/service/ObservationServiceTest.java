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

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: Write brief description about the class here.
 */
public class ObservationServiceTest {
    /**
     * @verifies download observation with matching uuid.
     * @see ObservationService#downloadObservationByUuid(String)
     */
    @Test
    public void downloadObservationByUuid_shouldDownloadObservationWithMatchingUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies download all observation with matching patient and concept.
     * @see ObservationService#downloadObservationsByPatientAndConcept(String, String)
     */
    @Test
    public void downloadObservationsByPatientAndConcept_shouldDownloadAllObservationWithMatchingPatientAndConcept() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies download all observation when name is empty.
     * @see ObservationService#downloadObservationsByPatientAndConcept(String, String)
     */
    @Test
    public void downloadObservationsByPatientAndConcept_shouldDownloadAllObservationWhenNameIsEmpty() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies save observation to local data repository
     * @see ObservationService#saveObservation(com.muzima.api.model.Observation)
     */
    @Test
    public void saveObservation_shouldSaveObservationToLocalDataRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies save observations to local data repository
     * @see ObservationService#saveObservations(java.util.List)
     */
    @Test
    public void saveObservations_shouldSaveObservationsToLocalDataRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies replace existing observation in the local data repository.
     * @see ObservationService#updateObservation(com.muzima.api.model.Observation)
     */
    @Test
    public void updateObservation_shouldReplaceExistingObservationInTheLocalDataRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies replace existing observations in the local data repository.
     * @see ObservationService#updateObservations(java.util.List)
     */
    @Test
    public void updateObservations_shouldReplaceExistingObservationsInTheLocalDataRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return observation with matching uuid.
     * @see ObservationService#getObservationByUuid(String)
     */
    @Test
    public void getObservationByUuid_shouldReturnObservationWithMatchingUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return null when no observation match the uuid.
     * @see ObservationService#getObservationByUuid(String)
     */
    @Test
    public void getObservationByUuid_shouldReturnNullWhenNoObservationMatchTheUuid() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return list of all observations for the patient.
     * @see ObservationService#getObservationsByPatient(String)
     */
    @Test
    public void getObservationsByPatient_shouldReturnListOfAllObservationsForThePatient() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return empty list when no observation found for the patient.
     * @see ObservationService#getObservationsByPatient(String)
     */
    @Test
    public void getObservationsByPatient_shouldReturnEmptyListWhenNoObservationFoundForThePatient() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return list of all observations for the patient.
     * @see ObservationService#getObservationsByPatientAndConcept(String, String)
     */
    @Test
    public void getObservationsByPatientAndConcept_shouldReturnListOfAllObservationsForThePatient() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return empty list when no observation found for the patient.
     * @see ObservationService#getObservationsByPatientAndConcept(String, String)
     */
    @Test
    public void getObservationsByPatientAndConcept_shouldReturnEmptyListWhenNoObservationFoundForThePatient() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return list of all observations with matching search term on the searchable fields.
     * @see ObservationService#searchObservations(String, String)
     */
    @Test
    public void searchObservations_shouldReturnListOfAllObservationsWithMatchingSearchTermOnTheSearchableFields() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies return empty list when no observation match the search term.
     * @see ObservationService#searchObservations(String, String)
     */
    @Test
    public void searchObservations_shouldReturnEmptyListWhenNoObservationMatchTheSearchTerm() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies delete the observation from the local repository.
     * @see ObservationService#deleteObservation(com.muzima.api.model.Observation)
     */
    @Test
    public void deleteObservation_shouldDeleteTheObservationFromTheLocalRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies delete observations from the local repository.
     * @see ObservationService#deleteObservations(java.util.List)
     */
    @Test
    public void deleteObservations_shouldDeleteObservationsFromTheLocalRepository() throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }
}
