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

import com.google.inject.ImplementedBy;
import com.muzima.api.model.Encounter;
import com.muzima.api.service.impl.EncounterServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(EncounterServiceImpl.class)
public interface EncounterService extends MuzimaInterface {

    /**
     * Download encounter with matching uuid.
     *
     * @param uuid the uuid of the encounter.
     * @return the encounter with matching uuid.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should return downloaded encounter with matching uuid.
     * @should return null when no encounter match the uuid.
     */
    Encounter downloadEncounterByUuid(final String uuid) throws IOException;

    /**
     * Download list of encounters with matching name.
     *
     * @param name the partial name of the patient.
     * @return list of encounters with for patient with matching name.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should return downloaded list of encounters for patient with matching name.
     * @should return empty list when the name is empty.
     */
    List<Encounter> downloadEncountersByPatientName(final String name) throws IOException;

    /**
     * Download list of encounters with matching name.
     *
     * @param patientUuid the uuid of the patient.
     * @return list of encounters with matching name.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should return downloaded list of encounters with matching name.
     * @should return empty list when the name is empty.
     */
    List<Encounter> downloadEncountersByPatientUuid(final String patientUuid) throws IOException;

    /**
     * Get a single encounter from local data repository with matching uuid.
     *
     * @param uuid the uuid of the encounter.
     * @return the encounter with matching uuid.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should return encounter with matching uuid.
     * @should return null when no encounter match the uuid.
     */
    Encounter getEncounterByUuid(final String uuid) throws IOException;

    /**
     * Get list of encounters from local data repository with matching name.
     *
     * @param name the name of the encounter.
     * @return list of encounters with matching name.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should return list of encounters with matching name.
     * @should return empty list when no encounter match the name.
     */
    List<Encounter> getEncountersByName(final String name) throws IOException;

    /**
     * Get all encounters stored in the local data repository.
     *
     * @return all encounters stored in the local data repository.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should return all encounters stored in the local data repository.
     */
    List<Encounter> getAllEncounters() throws IOException;

    /**
     * Count all encounters stored in the local data repository.
     *
     * @return number of encounters stored in the local data repository.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should return number of encounters stored in the local data repository.
     */
    Integer countAllEncounters() throws IOException;

    /**
     * Save a encounter into local data repository.
     *
     * @param encounter the encounter to be saved.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should save encounter into local data repository.
     */
    void saveEncounter(final Encounter encounter) throws IOException;

    /**
     * Save list of encounters into local data repository.
     *
     * @param encounters the encounters to be saved.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should save list of encounters into local data repository.
     */
    void saveEncounters(final List<Encounter> encounters) throws IOException;

    /**
     * Update a encounter in the local data repository.
     *
     * @param encounter the encounter to be updated.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should update encounter in local data repository.
     */
    void updateEncounter(final Encounter encounter) throws IOException;

    /**
     * Update list of encounters in the local data repository.
     *
     * @param encounters the encounters to be updated.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should update list of encounters in local data repository.
     */
    void updateEncounters(final List<Encounter> encounters) throws IOException;

    /**
     * Delete a encounter from the local data repository.
     *
     * @param encounter the encounter to be deleted.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should delete encounter from local data repository.
     */
    void deleteEncounter(final Encounter encounter) throws IOException;

    /**
     * Delete list of encounters from the local data repository.
     *
     * @param encounters the encounters to be deleted.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should delete list of encounters from local data repository.
     */
    void deleteEncounters(final List<Encounter> encounters) throws IOException;
}
