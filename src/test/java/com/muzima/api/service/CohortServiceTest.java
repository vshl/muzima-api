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
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Patient;
import com.muzima.search.api.util.StringUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

/**
 * TODO: Write brief description about the class here.
 */
public class CohortServiceTest {
    // baseline static cohort
    private Cohort staticCohort;
    private List<Cohort> staticCohorts;
    // baseline dynamic cohort
    private Cohort dynamicCohort;
    private List<Cohort> dynamicCohorts;

    private Context context;
    private CohortService cohortService;

    private static int nextInt(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    @Before
    public void prepare() throws Exception {
        context = ContextFactory.createContext();
        context.openSession();
        if (!context.isAuthenticated()) {
            context.authenticate("admin", "test", "http://localhost:8081/openmrs-standalone");
        }
        cohortService = context.getCohortService();
        staticCohorts = cohortService.downloadCohortsByName(StringUtil.EMPTY);
        staticCohort = staticCohorts.get(nextInt(staticCohorts.size()));
        dynamicCohorts = cohortService.downloadDynamicCohortsByName(StringUtil.EMPTY);
        dynamicCohort = dynamicCohorts.get(nextInt(dynamicCohorts.size()));
    }

    @After
    public void cleanUp() throws Exception {
        String tmpDirectory = System.getProperty("java.io.tmpdir");
        String lucenePath = tmpDirectory + "/muzima";
        File luceneDirectory = new File(lucenePath);
        for (String filename : luceneDirectory.list()) {
            File file = new File(luceneDirectory, filename);
            Assert.assertTrue(file.delete());
        }
        context.deauthenticate();
        context.closeSession();
    }

    /**
     * @verifies download cohort with matching uuid.
     * @see CohortService#downloadCohortByUuid(String)
     */
    @Test
    public void downloadCohortByUuid_shouldDownloadCohortWithMatchingUuid() throws Exception {
        Cohort downloadedStaticCohort = cohortService.downloadCohortByUuid(staticCohort.getUuid());
        assertThat(downloadedStaticCohort, samePropertyValuesAs(staticCohort));
    }

    /**
     * @verifies download all cohorts with partially matched name.
     * @see CohortService#downloadCohortsByName(String)
     */
    @Test
    public void downloadCohortsByName_shouldDownloadAllCohortsWithPartiallyMatchedName() throws Exception {
        String name = staticCohort.getName();
        String partialName = name.substring(0, name.length() - 1);
        List<Cohort> downloadedStaticCohorts = cohortService.downloadCohortsByName(partialName);
        for (Cohort downloadedStaticCohort : downloadedStaticCohorts) {
            assertThat(downloadedStaticCohort.getName(), containsString(partialName));
        }
    }

    /**
     * @verifies download all cohorts when name is empty.
     * @see CohortService#downloadCohortsByName(String)
     */
    @Test
    public void downloadCohortsByName_shouldDownloadAllCohortsWhenNameIsEmpty() throws Exception {
        List<Cohort> downloadedStaticCohorts = cohortService.downloadCohortsByName(StringUtil.EMPTY);
        assertThat(downloadedStaticCohorts, hasSize(staticCohorts.size()));
    }

    /**
     * @verifies download cohort definition with matching uuid.
     * @see CohortService#downloadDynamicCohortByUuid(String)
     */
    @Test
    public void downloadDynamicCohortByUuid_shouldDownloadCohortDefinitionWithMatchingUuid() throws Exception {
        Cohort downloadedDynamicCohort = cohortService.downloadDynamicCohortByUuid(dynamicCohort.getUuid());
        assertThat(downloadedDynamicCohort, samePropertyValuesAs(dynamicCohort));
    }

    /**
     * @verifies download all cohort definitions with partially matched name.
     * @see CohortService#downloadDynamicCohortsByName(String)
     */
    @Test
    public void downloadDynamicCohortsByName_shouldDownloadAllCohortDefinitionsWithPartiallyMatchedName() throws Exception {
        String name = dynamicCohort.getName();
        String partialName = name.substring(0, name.length() - 1);
        List<Cohort> downloadedDynamicCohorts = cohortService.downloadDynamicCohortsByName(partialName);
        for (Cohort downloadedDynamicCohort : downloadedDynamicCohorts) {
            assertThat(downloadedDynamicCohort.getName(), containsString(partialName));
        }
    }

    /**
     * @verifies download all cohort definitions when name is empty.
     * @see CohortService#downloadDynamicCohortsByName(String)
     */
    @Test
    public void downloadDynamicCohortsByName_shouldDownloadAllCohortDefinitionsWhenNameIsEmpty() throws Exception {
        List<Cohort> downloadedStaticCohorts = cohortService.downloadCohortsByName(StringUtil.EMPTY);
        assertThat(downloadedStaticCohorts, hasSize(staticCohorts.size()));
    }

    /**
     * @verifies save cohort object into local lucene data repository.
     * @see CohortService#saveCohort(com.muzima.api.model.Cohort)
     */
    @Test
    public void saveCohort_shouldSaveCohortObjectIntoLocalLuceneDataRepository() throws Exception {
        int cohortCounter = cohortService.countAllCohorts();
        cohortService.saveCohort(staticCohort);
        assertThat(cohortCounter + 1, equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohort(staticCohort);
        assertThat(cohortCounter + 1, equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohort(dynamicCohort);
        assertThat(cohortCounter + 2, equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohort(dynamicCohort);
        assertThat(cohortCounter + 2, equalTo(cohortService.countAllCohorts()));
    }

    /**
     * @verifies save list of all cohort objects into local lucene data repository.
     * @see CohortService#saveCohorts(java.util.List)
     */
    @Test
    public void saveCohorts_shouldSaveListOfAllCohortObjectsIntoLocalLuceneDataRepository() throws Exception {
        int cohortCounter = cohortService.countAllCohorts();
        cohortService.saveCohorts(staticCohorts);
        assertThat(cohortCounter + staticCohorts.size(), equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohorts(staticCohorts);
        assertThat(cohortCounter + staticCohorts.size(), equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohorts(dynamicCohorts);
        assertThat(cohortCounter + dynamicCohorts.size() + staticCohorts.size(), equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohorts(dynamicCohorts);
        assertThat(cohortCounter + dynamicCohorts.size() + staticCohorts.size(), equalTo(cohortService.countAllCohorts()));
    }

    /**
     * @verifies replace old cohort with the matching uuid with the new cohort object.
     * @see CohortService#updateCohort(com.muzima.api.model.Cohort)
     */
    @Test
    public void updateCohort_shouldReplaceOldCohortWithTheMatchingUuidWithTheNewCohortObject() throws Exception {
        Cohort nullCohort = cohortService.getCohortByUuid(staticCohort.getUuid());
        assertThat(nullCohort, nullValue());
        cohortService.saveCohort(staticCohort);
        Cohort savedCohort = cohortService.getCohortByUuid(staticCohort.getUuid());
        assertThat(savedCohort, notNullValue());
        assertThat(savedCohort, samePropertyValuesAs(staticCohort));

        String cohortName = "New Cohort Name";
        savedCohort.setName(cohortName);
        cohortService.updateCohort(savedCohort);
        Cohort updatedCohort = cohortService.getCohortByUuid(savedCohort.getUuid());
        assertThat(updatedCohort, not(samePropertyValuesAs(staticCohort)));
        assertThat(updatedCohort.getName(), equalTo(cohortName));
    }

    /**
     * @verifies replace all old cohorts with  matching uuid with the new cohort object.
     * @see CohortService#updateCohorts(java.util.List)
     */
    @Test
    public void updateCohorts_shouldReplaceAllOldCohortsWithMatchingUuidWithTheNewCohortObject() throws Exception {
        int counter = 0;
        cohortService.saveCohorts(staticCohorts);
        List<Cohort> savedCohorts = cohortService.getAllCohorts();
        for (Cohort cohort : savedCohorts) {
            cohort.setName("New Name For Cohort: " + counter++);
        }
        cohortService.updateCohorts(savedCohorts);
        List<Cohort> updatedCohorts = cohortService.getAllCohorts();
        for (Cohort updatedCohort : updatedCohorts) {
            for (Cohort cohort : staticCohorts) {
                assertThat(updatedCohort, not(samePropertyValuesAs(cohort)));
            }
            assertThat(updatedCohort.getName(), containsString("New Name For Cohort"));
        }
    }

    /**
     * @verifies return cohort with matching uuid.
     * @see CohortService#getCohortByUuid(String)
     */
    @Test
    public void getCohortByUuid_shouldReturnCohortWithMatchingUuid() throws Exception {
        Cohort nullCohort = cohortService.getCohortByUuid(staticCohort.getUuid());
        assertThat(nullCohort, nullValue());
        cohortService.saveCohort(staticCohort);
        Cohort savedCohort = cohortService.getCohortByUuid(staticCohort.getUuid());
        assertThat(savedCohort, notNullValue());
        assertThat(savedCohort, samePropertyValuesAs(staticCohort));
    }

    /**
     * @verifies return null when no cohort match the uuid.
     * @see CohortService#getCohortByUuid(String)
     */
    @Test
    public void getCohortByUuid_shouldReturnNullWhenNoCohortMatchTheUuid() throws Exception {
        Cohort nullCohort = cohortService.getCohortByUuid(staticCohort.getUuid());
        assertThat(nullCohort, nullValue());
        cohortService.saveCohort(staticCohort);
        String randomUuid = UUID.randomUUID().toString();
        Cohort savedCohort = cohortService.getCohortByUuid(randomUuid);
        assertThat(savedCohort, nullValue());
    }

    /**
     * @verifies return number of cohort in the local repository with matching name.
     * @see CohortService#countCohorts(String)
     */
    @Test
    public void countCohorts_shouldReturnNumberOfCohortInTheLocalRepositoryWithMatchingName() throws Exception {
        assertThat(0, equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohort(staticCohort);
        assertThat(1, equalTo(cohortService.countCohorts(staticCohort.getName())));
        cohortService.saveCohort(dynamicCohort);
        assertThat(1, equalTo(cohortService.countCohorts(dynamicCohort.getName())));
    }

    /**
     * @verifies return list of all cohorts with matching name.
     * @see CohortService#getCohortsByName(String)
     */
    @Test
    public void getCohortsByName_shouldReturnListOfAllCohortsWithMatchingName() throws Exception {
        assertThat(0, equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohort(staticCohort);
        List<Cohort> savedStaticCohorts = cohortService.getCohortsByName(staticCohort.getName());
        assertThat(1, equalTo(savedStaticCohorts.size()));
        cohortService.saveCohort(dynamicCohort);
        List<Cohort> savedDynamicCohorts = cohortService.getCohortsByName(dynamicCohort.getName());
        assertThat(1, equalTo(savedDynamicCohorts.size()));
    }

    /**
     * @verifies return empty list when no cohort match the name.
     * @see CohortService#getCohortsByName(String)
     */
    @Test
    public void getCohortsByName_shouldReturnEmptyListWhenNoCohortMatchTheName() throws Exception {
        String randomName = UUID.randomUUID().toString();
        cohortService.saveCohort(staticCohort);
        List<Cohort> savedStaticCohorts = cohortService.getCohortsByName(randomName);
        assertThat(0, equalTo(savedStaticCohorts.size()));
        assertThat(staticCohort, not(isIn(savedStaticCohorts)));
        cohortService.saveCohort(dynamicCohort);
        List<Cohort> savedDynamicCohorts = cohortService.getCohortsByName(randomName);
        assertThat(0, equalTo(savedDynamicCohorts.size()));
        assertThat(dynamicCohort, not(isIn(savedDynamicCohorts)));
    }

    /**
     * @verifies return number of cohort in the local repository.
     * @see CohortService#countAllCohorts()
     */
    @Test
    public void countAllCohorts_shouldReturnNumberOfCohortInTheLocalRepository() throws Exception {
        assertThat(0, equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohorts(staticCohorts);
        assertThat(staticCohorts.size(), equalTo(cohortService.countAllCohorts()));
        cohortService.saveCohorts(dynamicCohorts);
        assertThat(staticCohorts.size() + dynamicCohorts.size(), equalTo(cohortService.countAllCohorts()));
    }

    /**
     * @verifies return all registered cohorts.
     * @see CohortService#getAllCohorts()
     */
    @Test
    public void getAllCohorts_shouldReturnAllRegisteredCohorts() throws Exception {
        cohortService.saveCohorts(staticCohorts);
        List<Cohort> savedCohorts = cohortService.getAllCohorts();
        assertThat(savedCohorts, hasSize(staticCohorts.size()));
    }

    /**
     * @verifies return empty list when no cohort is registered.
     * @see CohortService#getAllCohorts()
     */
    @Test
    public void getAllCohorts_shouldReturnEmptyListWhenNoCohortIsRegistered() throws Exception {
        assertThat(cohortService.getAllCohorts(), hasSize(0));
    }

    /**
     * @verifies delete the cohort from lucene repository.
     * @see CohortService#deleteCohort(com.muzima.api.model.Cohort)
     */
    @Test
    public void deleteCohort_shouldDeleteTheCohortFromLuceneRepository() throws Exception {
        assertThat(cohortService.getAllCohorts(), hasSize(0));
        cohortService.saveCohorts(staticCohorts);
        int cohortCount = staticCohorts.size();
        assertThat(cohortService.getAllCohorts(), hasSize(cohortCount));
        for (Cohort cohort : staticCohorts) {
            cohortService.deleteCohort(cohort);
            assertThat(cohortService.getAllCohorts(), hasSize(--cohortCount));
        }
    }

    /**
     * @verifies delete all cohorts from lucene repository.
     * @see CohortService#deleteCohorts(java.util.List)
     */
    @Test
    public void deleteCohorts_shouldDeleteAllCohortsFromLuceneRepository() throws Exception {
        assertThat(cohortService.getAllCohorts(), hasSize(0));
        cohortService.saveCohorts(staticCohorts);
        List<Cohort> savedCohorts = cohortService.getAllCohorts();
        assertThat(savedCohorts, hasSize(staticCohorts.size()));
        cohortService.deleteCohorts(savedCohorts);
        assertThat(cohortService.getAllCohorts(), hasSize(0));
    }

    /**
     * @verifies download cohort data identified by the uuid and dynamic field.
     * @see CohortService#downloadCohortData(String, boolean)
     */
    @Test
    public void downloadCohortData_shouldDownloadCohortDataIdentifiedByTheUuidAndDynamicField() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort.getUuid(), staticCohort.isDynamic());
        assertThat(staticCohortData.getCohort(), samePropertyValuesAs(staticCohort));
        assertThat(staticCohortData.getPatients(), not(empty()));
        assertThat(staticCohortData.getCohortMembers(), not(empty()));
        CohortData dynamicCohortData = cohortService.downloadCohortData(dynamicCohort.getUuid(), dynamicCohort.isDynamic());
        assertThat(dynamicCohortData.getCohort(), samePropertyValuesAs(dynamicCohort));
        assertThat(dynamicCohortData.getPatients(), not(empty()));
        assertThat(dynamicCohortData.getCohortMembers(), not(empty()));
    }

    /**
     * @verifies download cohort data for the cohort object
     * @see CohortService#downloadCohortData(com.muzima.api.model.Cohort)
     */
    @Test
    public void downloadCohortData_shouldDownloadCohortDataForTheCohortObject() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort);
        assertThat(staticCohortData.getCohort(), samePropertyValuesAs(staticCohort));
        assertThat(staticCohortData.getPatients(), not(empty()));
        assertThat(staticCohortData.getCohortMembers(), not(empty()));
        CohortData dynamicCohortData = cohortService.downloadCohortData(dynamicCohort);
        assertThat(dynamicCohortData.getCohort(), samePropertyValuesAs(dynamicCohort));
        assertThat(dynamicCohortData.getPatients(), not(empty()));
        assertThat(dynamicCohortData.getCohortMembers(), not(empty()));
    }

    /**
     * @verifies save the cohort member object to local lucene repository.
     * @see CohortService#saveCohortMember(com.muzima.api.model.CohortMember)
     */
    @Test
    public void saveCohortMember_shouldSaveTheCohortMemberObjectToLocalLuceneRepository() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort);
        int counter = 0;
        for (CohortMember cohortMember : staticCohortData.getCohortMembers()) {
            counter = counter + 1;
            cohortService.saveCohortMember(cohortMember);
            assertThat(cohortService.getCohortMembers(staticCohort), hasSize(counter));
            assertThat(cohortService.countCohortMembers(staticCohort), equalTo(counter));
        }
    }

    /**
     * @verifies save list of cohort members to local lucene repository.
     * @see CohortService#saveCohortMembers(java.util.List)
     */
    @Test
    public void saveCohortMembers_shouldSaveListOfCohortMembersToLocalLuceneRepository() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort);
        assertThat(cohortService.getCohortMembers(staticCohort), hasSize(0));
        assertThat(cohortService.countCohortMembers(staticCohort), equalTo(0));
        List<CohortMember> cohortMembers = staticCohortData.getCohortMembers();
        cohortService.saveCohortMembers(cohortMembers);
        assertThat(cohortService.getCohortMembers(staticCohort), hasSize(cohortMembers.size()));
        assertThat(cohortService.countCohortMembers(staticCohort), equalTo(cohortMembers.size()));
    }

    /**
     * @verifies replace the cohort member in local lucene repository.
     * @see CohortService#updateCohortMember(com.muzima.api.model.CohortMember)
     */
    @Test
    public void updateCohortMember_shouldReplaceTheCohortMemberInLocalLuceneRepository() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort);
        List<CohortMember> cohortMembers = staticCohortData.getCohortMembers();
        CohortMember cohortMember = cohortMembers.get(nextInt(cohortMembers.size()));
        assertThat(cohortService.getCohortMembers(staticCohort), hasSize(0));
        cohortService.saveCohortMember(cohortMember);
        List<CohortMember> savedCohortMembers = cohortService.getCohortMembers(staticCohort);
        assertThat(savedCohortMembers, hasSize(1));
        for (CohortMember savedCohortMember : savedCohortMembers) {
            Cohort savedCohort = savedCohortMember.getCohort();
            assertThat(savedCohort, samePropertyValuesAs(staticCohort));
            savedCohort.setName("New Cohort Name");
            cohortService.updateCohortMember(savedCohortMember);
        }
        List<CohortMember> updatedCohortMembers = cohortService.getCohortMembers(staticCohort);
        assertThat(updatedCohortMembers, hasSize(1));
        for (CohortMember updatedCohortMember : updatedCohortMembers) {
            Cohort savedCohort = updatedCohortMember.getCohort();
            assertThat(savedCohort, not(samePropertyValuesAs(staticCohort)));
        }
    }

    /**
     * @verifies replace the cohort members in local lucene repository.
     * @see CohortService#updateCohortMembers(java.util.List)
     */
    @Test
    public void updateCohortMembers_shouldReplaceTheCohortMembersInLocalLuceneRepository() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort);
        List<CohortMember> cohortMembers = staticCohortData.getCohortMembers();
        assertThat(cohortService.getCohortMembers(staticCohort), hasSize(0));
        cohortService.saveCohortMembers(cohortMembers);
        List<CohortMember> savedCohortMembers = cohortService.getCohortMembers(staticCohort);
        assertThat(savedCohortMembers, hasSize(cohortMembers.size()));
        for (CohortMember savedCohortMember : savedCohortMembers) {
            Cohort savedCohort = savedCohortMember.getCohort();
            assertThat(savedCohort, samePropertyValuesAs(staticCohort));
            savedCohort.setName("New Cohort Name");
        }
        cohortService.updateCohortMembers(savedCohortMembers);
        List<CohortMember> updatedCohortMembers = cohortService.getCohortMembers(staticCohort);
        assertThat(updatedCohortMembers, hasSize(cohortMembers.size()));
        for (CohortMember updatedCohortMember : updatedCohortMembers) {
            Cohort savedCohort = updatedCohortMember.getCohort();
            assertThat(savedCohort, not(samePropertyValuesAs(staticCohort)));
        }
    }

    /**
     * @verifies return list of all members for the cohort.
     * @see CohortService#getCohortMembers(String)
     */
    @Test
    public void getCohortMembers_shouldReturnListOfAllMembersForTheCohort() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort);
        List<CohortMember> cohortMembers = staticCohortData.getCohortMembers();
        assertThat(cohortService.getCohortMembers(staticCohort), hasSize(0));
        cohortService.saveCohortMembers(cohortMembers);
        List<CohortMember> savedCohortMembers = cohortService.getCohortMembers(staticCohort);
        assertThat(savedCohortMembers, hasSize(cohortMembers.size()));
    }

    /**
     * @verifies return empty list when no member are in the cohort.
     * @see CohortService#getCohortMembers(String)
     */
    @Test
    public void getCohortMembers_shouldReturnEmptyListWhenNoMemberAreInTheCohort() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort);
        List<CohortMember> cohortMembers = staticCohortData.getCohortMembers();
        assertThat(cohortService.getCohortMembers(staticCohort), hasSize(0));
        cohortService.saveCohortMembers(cohortMembers);
        List<CohortMember> savedCohortMembers = cohortService.getCohortMembers(dynamicCohort);
        assertThat(savedCohortMembers, not(hasSize(cohortMembers.size())));
        assertThat(savedCohortMembers, hasSize(0));
    }

    /**
     * @verifies delete all members for the cohort from the local repository.
     * @see CohortService#deleteCohortMembers(String)
     */
    @Test
    public void deleteCohortMembers_shouldDeleteAllMembersForTheCohortFromTheLocalRepository() throws Exception {
        CohortData staticCohortData = cohortService.downloadCohortData(staticCohort);
        List<CohortMember> cohortMembers = staticCohortData.getCohortMembers();
        assertThat(cohortService.getCohortMembers(staticCohort), hasSize(0));
        cohortService.saveCohortMembers(cohortMembers);
        List<CohortMember> savedCohortMembers = cohortService.getCohortMembers(staticCohort);
        assertThat(savedCohortMembers, hasSize(cohortMembers.size()));
        cohortService.deleteCohortMembers(staticCohort);
        assertThat(cohortService.getCohortMembers(staticCohort), hasSize(0));
    }
}
