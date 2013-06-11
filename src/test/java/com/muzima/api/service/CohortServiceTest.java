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
import com.muzima.api.model.CohortData;
import com.muzima.api.model.CohortDefinition;
import com.muzima.api.model.Observation;
import com.muzima.api.model.Patient;
import com.muzima.search.api.util.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class CohortServiceTest {

    private final Logger logger = LoggerFactory.getLogger(CohortServiceTest.class.getSimpleName());

    @Test
    public void donwloadStaticCohort() throws Exception {
        Context context = ContextFactory.createContext();

        context.openSession();
        if (!context.isAuthenticated())
            context.authenticate("admin", "test", "http://localhost:8081/openmrs-standalone");

        CohortService cohortService = context.getCohortService();
        PatientService patientService = context.getPatientService();
        ObservationService observationService = context.getObservationService();

        List<Cohort> cohorts = cohortService.downloadCohortsByName(StringUtil.EMPTY);
        for (Cohort cohort : cohorts) {
            logger.info("Cohort: {} | {}", cohort.getName(), cohort.getUuid());
            CohortData cohortData = cohortService.downloadCohortData(cohort.getUuid(), false);
            logger.info("Cohort data: {}", cohortData);
            for (Patient patient : cohortData.getPatients()) {
                List<Observation> observations = observationService.downloadObservationsByPatient(patient.getUuid());
                observationService.saveObservations(observations);
            }
            patientService.savePatients(cohortData.getPatients());
            cohortService.saveCohortMembers(cohortData.getCohortMembers());
        }

        context.deauthenticate();
        context.closeSession();
    }

    @Test
    public void donwloadDynamicCohort() throws Exception {
        Context context = ContextFactory.createContext();

        context.openSession();
        if (!context.isAuthenticated())
            context.authenticate("admin", "test", "http://localhost:8081/openmrs-standalone");

        CohortService cohortService = context.getCohortService();
        PatientService patientService = context.getPatientService();
        ObservationService observationService = context.getObservationService();

        List<CohortDefinition> cohortDefinitions = cohortService.downloadCohortDefinitionsByName(StringUtil.EMPTY);
        for (CohortDefinition cohortDefinition : cohortDefinitions) {
            logger.info("Cohort: {} | {}", cohortDefinition.getName(), cohortDefinition.getUuid());
            CohortData cohortData = cohortService.downloadCohortData(cohortDefinition.getUuid(), true);
            logger.info("Cohort data: {}", cohortData);
            for (Patient patient : cohortData.getPatients()) {
                List<Observation> observations = observationService.downloadObservationsByPatient(patient.getUuid());
                observationService.saveObservations(observations);
            }
            patientService.savePatients(cohortData.getPatients());
            cohortService.saveCohortMembers(cohortData.getCohortMembers());
        }

        context.deauthenticate();
        context.closeSession();
    }
}
