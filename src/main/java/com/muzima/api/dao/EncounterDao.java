/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.EncounterDaoImpl;
import com.muzima.api.model.Encounter;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(EncounterDaoImpl.class)
public interface EncounterDao extends OpenmrsDao<Encounter> {

    /**
     * Get list of encounters for particular patient.
     *
     * @param patientUuid the patient uuid.
     * @return list of encounters for the patient.
     * @throws java.io.IOException when the search api unable to process the resource.
     */
    List<Encounter> getEncountersByPatientUuid(final String patientUuid) throws IOException;
}
