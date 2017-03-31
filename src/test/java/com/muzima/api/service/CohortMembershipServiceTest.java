/*
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.service;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortMembership;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotEquals;

public class CohortMembershipServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CohortMembershipServiceTest.class.getSimpleName());
    private static final String GIVEN_NAME = "Test";
    private static final String FAMILY_NAME = "Patient";

    private Cohort cohort;

    private Context context;

    private CohortService cohortService;
    private CohortMembershipService cohortMembershipService;
    private List<CohortMembership> memberships;

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
            context.authenticate("admin", "Admin123", "http://localhost:8080/openmrs", false);
        }
        cohortService = context.getCohortService();
        cohortMembershipService = context.getCohortMembershipService();
        List<Cohort> cohorts = cohortService.downloadCohortsByName(StringUtil.EMPTY);
        logger.info("Number of downloaded static cohorts: {}", cohorts.size());
        cohort = cohorts.get(nextInt(cohorts.size()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2017-05-01 00:00:00");
        memberships = cohortMembershipService.downloadCohortMemberships(cohort.getUuid(), date);
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

    @Test
    public void getCohortMemberships_shouldGetAllCohortMemberships() throws Exception {
        assertNotEquals(memberships.size(), 0);
    }

    @Test
    public void saveCohortMembership_shouldSaveTheCohortMembershipObjectToLuceneRepository() throws Exception {
        int counter = 0;
        while (counter < memberships.size()) {
            CohortMembership membership = memberships.get(counter);
            cohortMembershipService.saveCohortMembership(membership);
            counter = counter + 1;
            assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(counter));
            assertThat(cohortMembershipService.countCohortMemberships(cohort), equalTo(counter));
        }
    }

    @Test
    public void saveCohortMemberships_shouldSaveListOfCohortMembershipsToLocalLuceneRepository() throws Exception {
        assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(0));
        assertThat(cohortMembershipService.countCohortMemberships(cohort), equalTo(0));
        cohortMembershipService.saveCohortMemberships(memberships);
        assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(memberships.size()));
        assertThat(cohortMembershipService.countCohortMemberships(cohort),equalTo(memberships.size()));
    }

    @Test
    public void searchCohortMemberships_shouldReturnListOfAllCohortMembershipsWithMatchingSearchTerm() throws Exception {
        cohortMembershipService.saveCohortMemberships(memberships);
        String name = FAMILY_NAME;
        String partialName = name.substring(0, name.length() - 1);
        List<CohortMembership> membershipsByName = cohortMembershipService.searchCohortMemberships(partialName, cohort.getUuid());
        assertThat(membershipsByName.size(), equalTo(1));
    }

    @Test
    public void updateCohortMembership_shouldReplaceTheCohortMembershipInLocalLuceneRepository() throws IOException {
        CohortMembership membership = memberships.get(nextInt(memberships.size()));
        assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(0));
        cohortMembershipService.saveCohortMembership(membership);
        List<CohortMembership> localMemberships = cohortMembershipService.getCohortMemberships(cohort);
        assertThat(localMemberships, hasSize(1));
        for (CohortMembership localMembership : localMemberships) {
            Cohort localCohort = localMembership.getCohort();
            assertThat(localCohort, samePropertyValuesAs(cohort));
            localCohort.setName("New Cohort Name");
            cohortMembershipService.updateCohortMembership(localMembership);
        }
        List<CohortMembership> updatedMemberships = cohortMembershipService.getCohortMemberships(cohort);
        assertThat(updatedMemberships, hasSize(1));
        for (CohortMembership updatedMembership : updatedMemberships) {
            Cohort updatedCohort = updatedMembership.getCohort();
            assertThat(updatedCohort, not(samePropertyValuesAs(cohort)));
        }
    }

    @Test
    public void updateCohortMemberships_shouldReplaceTheCohortMembershipsInLocalLuceneRepository() throws IOException {
        CohortMembership membership = memberships.get(nextInt(memberships.size()));
        assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(0));
        cohortMembershipService.saveCohortMembership(membership);
        List<CohortMembership> localMemberships = cohortMembershipService.getCohortMemberships(cohort);
        assertThat(localMemberships, hasSize(1));
        for (CohortMembership localMembership : localMemberships) {
            Cohort localCohort = localMembership.getCohort();
            assertThat(localCohort, samePropertyValuesAs(cohort));
            localCohort.setName("New Cohort Name");
        }
        cohortMembershipService.updateCohortMemberships(localMemberships);
        List<CohortMembership> updatedMemberships = cohortMembershipService.getCohortMemberships(cohort);
        assertThat(updatedMemberships, hasSize(1));
        for (CohortMembership updatedMembership : updatedMemberships) {
            Cohort updatedCohort = updatedMembership.getCohort();
            assertThat(updatedCohort, not(samePropertyValuesAs(cohort)));
        }
    }

    @Test
    public void getCohortMemberships_shouldReturnListOfAllMembershipsForTheCohort() throws Exception {
        assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(0));
        cohortMembershipService.saveCohortMemberships(memberships);
        List<CohortMembership> localMemberships = cohortMembershipService.getCohortMemberships(cohort);
        assertThat(localMemberships, hasSize(memberships.size()));
    }

    @Test
    public void getCohortMemberships_shouldReturnEmptyListWhenNoMembershipsAreInTheCohort() throws Exception {
        assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(0));
        cohortMembershipService.saveCohortMemberships(memberships);
    }

    @Test
    public void deleteCohortMemberships_shouldDeleteAllMembershipsForTheCohortFromTheLocalRepository() throws Exception {
        assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(0));
        cohortMembershipService.saveCohortMemberships(memberships);
        List<CohortMembership> localMemberships = cohortMembershipService.getCohortMemberships(cohort);
        assertThat(localMemberships, hasSize(memberships.size()));
        cohortMembershipService.deleteCohortMemberships(cohort);
        assertThat(cohortMembershipService.getCohortMemberships(cohort), hasSize(0));
    }
}
