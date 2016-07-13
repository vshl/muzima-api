/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.ObservationDaoImpl;
import com.muzima.api.model.Concept;
import com.muzima.api.model.Observation;

import java.io.IOException;
import java.util.List;

@ImplementedBy(ObservationDaoImpl.class)
public interface ObservationDao extends OpenmrsDao<Observation> {

    /**
     * Search observations for patient with matching uuid of the question.
     *
     * @param patientUuid the uuid of the patient.
     * @param conceptUuid the uuid of the question of the observations.
     * @return all observations for the patient with question matching the search term.
     * @throws IOException when search api unable to process the resource.
     */
    List<Observation> get(final String patientUuid, final String conceptUuid) throws IOException;

    int count(final String patientUuid, final String conceptUuid) throws IOException;

    List<Observation> get(final String patientUuid, final String conceptUuid,
                          final Integer page, final Integer pageSize) throws IOException;

    List<Observation> get(final Concept concept) throws IOException;

    List<Observation> get(final String formDataUuid) throws IOException;
}
