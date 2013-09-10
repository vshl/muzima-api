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
