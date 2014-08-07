/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.PatientDaoImpl;
import com.muzima.api.model.Patient;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

@ImplementedBy(PatientDaoImpl.class)
public interface PatientDao extends OpenmrsDao<Patient> {

    /**
     * Get patient by using the identifier.
     *
     * @param identifier the identifier of the patient.
     * @return the patient with matching identifier.
     * @throws IOException when search api unable to process the resource.
     */
    Patient getByIdentifier(final String identifier) throws IOException;

    /**
     * Get cohort by the name of the cohort. Passing empty string will returns all registered cohorts.
     *
     * @param name the partial name of the cohort or empty string.
     * @return the list of all matching cohort on the cohort name.
     * @throws IOException when search api unable to process the resource.
     */
    List<Patient> getPatientByName(final String name) throws IOException, ParseException;

    List<Patient> getPatientByName(final String name, final Integer page,
                                   final Integer pageSize) throws IOException, ParseException;

    /**
     * Search for patients matching the term on name and identifier.
     *
     * @param term the term that should match.
     * @return all patients with matching name or identifier.
     * @throws IOException when search api unable to process the resource.
     */
    List<Patient> search(final String term) throws ParseException, IOException;

    /**
     * Search for patients matching the term on name and identifier.
     *
     * @param term       the term that should match.
     * @param cohortUuid the cohort to search in
     * @return all patients with matching name or identifier within cohort.
     * @throws IOException when search api unable to process the resource.
     */
    List<Patient> search(final String term, final String cohortUuid) throws ParseException, IOException;


    List<Patient> search(final String term, final Integer page,
                         final Integer pageSize) throws ParseException, IOException;
}
