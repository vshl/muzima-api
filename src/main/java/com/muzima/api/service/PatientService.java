/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.CohortMembership;
import com.muzima.api.model.Patient;
import com.muzima.api.service.impl.PatientServiceImpl;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Service handling all operation to the @{Patient} actor/model
 * <p/>
 * TODO: add ability to search based on lucene like query syntax (merging name and identifier).
 */
@ImplementedBy(PatientServiceImpl.class)
public interface PatientService extends MuzimaInterface {

    /**
     * Download a single patient record from the patient rest resource into the local lucene repository.
     *
     * @param uuid the uuid of the patient.
     * @throws IOException when search api unable to process the resource.
     * @should download patient with matching uuid.
     */
    Patient downloadPatientByUuid(final String uuid) throws IOException;

    /**
     * Download all patients with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the patient to be downloaded. When empty, will return all patients available.
     * @throws IOException when search api unable to process the resource.
     * @should download all patient with partially matched name.
     */
    List<Patient> downloadPatientsByName(final String name) throws IOException;

    Patient consolidateTemporaryPatient(final String temporaryUuid) throws IOException;

    Patient consolidateTemporaryPatient(final Patient temporaryPatient) throws IOException;

    /**
     * Save patient to the local lucene repository.
     *
     * @param patient the patient to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save patient to local data repository.
     */
    Patient savePatient(final Patient patient) throws IOException;

    /**
     * Save patients to the local lucene repository.
     *
     * @param patients the patients to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save patients to local data repository.
     */
    void savePatients(final List<Patient> patients) throws IOException;

    /**
     * Update patient in the local lucene repository.
     *
     * @param patient the patient to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace existing patient in local data repository.
     */
    void updatePatient(final Patient patient) throws IOException;

    /**
     * Update patients in the local lucene repository.
     *
     * @param patients the patients to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should replace existing patients in local data repository.
     */
    void updatePatients(final List<Patient> patients) throws IOException;

    /**
     * Get a single patient record from the local repository with matching uuid.
     *
     * @param uuid the patient uuid
     * @return patient with matching uuid or null when no patient match the uuid
     * @throws IOException when search api unable to process the resource.
     * @should return patient with matching uuid
     * @should return null when no patient match the uuid
     */

    Patient getPatientByUuid(final String uuid) throws IOException;

    /**
     * Get patient by the identifier of the patient.
     *
     * @param identifier the patient identifier.
     * @return patient with matching identifier or null when no patient match the identifier.
     * @throws IOException when search api unable to process the resource.
     * @should return patient with matching identifier.
     * @should return null when no patient match the identifier.
     */
    Patient getPatientByIdentifier(final String identifier) throws IOException;

    /**
     * Count all patient objects.
     *
     * @return the total number of patient objects.
     * @throws IOException when search api unable to process the resource.
     */
    Integer countAllPatients() throws IOException;

    /**
     * Count all patient objects in cohort.
     *
     * @return the total number of patient objects.
     * @throws IOException when search api unable to process the resource.
     */
    Integer countPatients(final String cohortUuid) throws IOException;

    /**
     * Get all saved patients in the local repository.
     *
     * @return all registered patients or empty list when no patient is registered.
     * @throws IOException when search api unable to process the resource.
     * @should return all registered patients.
     * @should return empty list when no patient is registered.
     */
    List<Patient> getAllPatients() throws IOException;

    /**
     * Get all saved patients in the local repository, of the specified page and page size
     *
     * @param page the page number
     * @param pageSize the number of patients per page.
     *
     * @return all registered patients or empty list when no patient is registered.
     * @throws IOException when search api unable to process the resource.
     * @should return all registered patients.
     * @should return empty list when no patient is registered.
     */
    List<Patient> getPatients(final Integer page,
                                 final Integer pageSize) throws IOException;

    /**
     * Get all saved patients in cohort in the local repository, of the specified page and page size
     *
     * @param cohortUuid the cohort ID
     * @param page the page number
     * @param pageSize the number of patients per page.
     *
     * @return all registered patients or empty list when no patient is registered.
     * @throws IOException when search api unable to process the resource.
     * @should return all registered patients.
     * @should return empty list when no patient is registered.
     */
    List<Patient> getPatients(final String cohortUuid,
                                 final Integer page,
                                 final Integer pageSize) throws IOException;
    /**
     * Get list of patients with name similar to the search term.
     *
     * @param name the patient name.
     * @return list of all patients with matching name or empty list when no patient match the name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all patients with matching name partially.
     * @should return empty list when no patient match the name.
     */
    List<Patient> getPatientsByName(final String name) throws IOException, ParseException;

    /**
     * Search for patients with matching characteristic on the name or identifier with the search term.
     *
     * @param term the search term.
     * @return list of all patients with matching search term on the searchable fields or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all patients with matching search term.
     * @should return empty list when no patient match the search term.
     */
    List<Patient> searchPatients(final String term) throws IOException, ParseException;
    List<Patient> searchPatients(final String term, final Integer page,
                                 final Integer pageSize) throws IOException, ParseException;

    /**
     * Search for patients with matching characteristic on the name or identifier with the search term, within the give cohort.
     *
     * @param term       the search term
     * @param cohortUuid the Uuid of the cohort, only patients within the cohort will be searched
     * @return list of all patients in the cohort with matching search term on the searchable fields or empty list.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return list of all patients in cohort with matching search term.
     * @should return empty list when no patient match the search term.
     */
    List<Patient> searchPatients(final String term, final String cohortUuid) throws IOException, ParseException;

    /**
     * Delete a single patient object from the local repository.
     *
     * @param patient the patient object.
     * @throws IOException when search api unable to process the resource.
     * @should delete the patient object from the local repository.
     */
    void deletePatient(final Patient patient) throws IOException;

    /**
     * Delete patient objects from the local repository.
     *
     * @param patients the patient objects.
     * @throws IOException when search api unable to process the resource.
     * @should delete the patient object from the local repository.
     */
    void deletePatients(final List<Patient> patients) throws IOException;

    /**
     * @return List of patients that are not a part of any cohort.
     */
    List<Patient> getPatientsNotInCohorts() throws IOException;

    List<Patient> getPatientsFromCohortMembers(List<CohortMember> cohortMembers);

    List<Patient> getPatientsFromCohortMemberships(List<CohortMembership> memberships);
}
