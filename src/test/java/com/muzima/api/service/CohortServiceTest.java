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
import com.muzima.search.api.util.StringUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class CohortServiceTest {

    private final Logger logger = LoggerFactory.getLogger(CohortServiceTest.class.getSimpleName());

    @After
    public void cleanUp() {
        String tmpDirectory = System.getProperty("java.io.tmpdir");
        String lucenePath = tmpDirectory + "/muzima";

        File luceneDirectory = new File(lucenePath);
        for (String filename : luceneDirectory.list()) {
            File file = new File(luceneDirectory, filename);
            Assert.assertTrue(file.delete());
        }
    }

    @Test
    public void downloadStaticCohort() throws Exception {
        Context context = ContextFactory.createContext();

        context.openSession();
        if (!context.isAuthenticated())
            context.authenticate("admin", "test", "http://localhost:8081/openmrs-standalone");

        int patientCounter = 0;
        int cohortMemberCounter = 0;
        int observationCounter = 0;

        long start = System.currentTimeMillis();

        CohortService cohortService = context.getCohortService();
        PatientService patientService = context.getPatientService();

        List<Cohort> cohorts = cohortService.downloadCohortsByName(StringUtil.EMPTY);
        if (!cohorts.isEmpty()) {
            Cohort cohort = cohorts.get(0);
            logger.info("Cohort: {} | {}", cohort.getName(), cohort.getUuid());
            CohortData cohortData = cohortService.downloadCohortData(cohort);
            logger.info("Cohort data: {}", cohortData);
            patientCounter = patientCounter + cohortData.getPatients().size();
            patientService.savePatients(cohortData.getPatients());
            cohortMemberCounter = cohortMemberCounter + cohortData.getCohortMembers().size();
            cohortService.saveCohortMembers(cohortData.getCohortMembers());
        }

        long end = System.currentTimeMillis();
        double elapsed = (end - start) / 1000;
        logger.info("Download Statistic:");
        logger.info("Total time: " + elapsed + "s");
        logger.info("Total patients: {}", patientCounter);
        logger.info("Total cohort members: {}", cohortMemberCounter);
        logger.info("Total observations: {}", observationCounter);

        context.deauthenticate();
        context.closeSession();
    }

    @Test
    public void downloadDynamicCohort() throws Exception {
        Context context = ContextFactory.createContext();

        context.openSession();
        if (!context.isAuthenticated())
            context.authenticate("admin", "test", "http://localhost:8081/openmrs-standalone");

        int patientCounter = 0;
        int cohortMemberCounter = 0;
        int observationCounter = 0;

        long start = System.currentTimeMillis();

        CohortService cohortService = context.getCohortService();
        PatientService patientService = context.getPatientService();

        List<Cohort> cohorts = cohortService.downloadDynamicCohortsByName(StringUtil.EMPTY);
        for (Cohort cohort : cohorts) {
            logger.info("Cohort: {} | {}", cohort.getName(), cohort.getUuid());
            CohortData cohortData = cohortService.downloadCohortData(cohort);
            logger.info("Cohort data: {}", cohortData);
            patientCounter = patientCounter + cohortData.getPatients().size();
            patientService.savePatients(cohortData.getPatients());
            cohortMemberCounter = cohortMemberCounter + cohortData.getCohortMembers().size();
            cohortService.saveCohortMembers(cohortData.getCohortMembers());
        }

        long end = System.currentTimeMillis();
        double elapsed = (end - start) / 1000;
        logger.info("Download Statistic:");
        logger.info("Total time: " + elapsed + "s");
        logger.info("Total patients: {}", patientCounter);
        logger.info("Total cohort members: {}", cohortMemberCounter);
        logger.info("Total observations: {}", observationCounter);

        context.deauthenticate();
        context.closeSession();
    }
}
