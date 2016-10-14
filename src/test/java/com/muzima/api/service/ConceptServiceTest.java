/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Concept;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
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
public class ConceptServiceTest {
    // baseline concept
    private Concept concept;
    private List<Concept> concepts;

    private Context context;
    private ConceptService conceptService;

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
            context.authenticate("admin", "test", "http://demo2.muzima.org", false);
        }
        conceptService = context.getService(ConceptService.class);
        concepts = conceptService.downloadConceptsByName("HEIGHT (CM)");
        concept = concepts.get(nextInt(concepts.size()));
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

    /**
     * @verifies return empty list when the name is empty.
     * @see ConceptService#downloadConceptsByName(String)
     */
    @Test
    public void downloadConceptsByName_shouldReturnEmptyListWhenTheNameIsEmpty() throws Exception {
        List<Concept> downloadedConcepts = conceptService.downloadConceptsByName(StringUtil.EMPTY);
        assertThat(downloadedConcepts, hasSize(0));
    }

    /**
     * @verifies download list of concepts with matching name.
     * @see ConceptService#downloadConceptsByName(String)
     */
    @Test
    public void downloadConceptsByName_shouldDownloadListOfConceptsWithMatchingName() throws Exception {
        String name = concept.getName();
        String partialName = name.substring(0, name.length() - 1);
        List<Concept> downloadedConcepts = conceptService.downloadConceptsByName(partialName);
        for (Concept downloadedConcept : downloadedConcepts) {
            assertThat(downloadedConcept.getName(), containsString(partialName));
        }
    }

    /**
     * @verifies return concept with matching uuid.
     * @see ConceptService#getConceptByUuid(String)
     */
    @Test
    public void getConceptByUuid_shouldReturnConceptWithMatchingUuid() throws Exception {
        Concept nullConcept = conceptService.getConceptByUuid(concept.getUuid());
        assertThat(nullConcept, nullValue());
        conceptService.saveConcept(concept);
        Concept savedConcept = conceptService.getConceptByUuid(concept.getUuid());
        assertThat(savedConcept, notNullValue());
        assertThat(savedConcept, samePropertyValuesAs(concept));
    }

    /**
     * @verifies return null when no concept match the uuid.
     * @see ConceptService#getConceptByUuid(String)
     */
    @Test
    public void getConceptByUuid_shouldReturnNullWhenNoConceptMatchTheUuid() throws Exception {
        Concept nullConcept = conceptService.getConceptByUuid(concept.getUuid());
        assertThat(nullConcept, nullValue());
        conceptService.saveConcept(concept);
        String randomUuid = UUID.randomUUID().toString();
        Concept savedConcept = conceptService.getConceptByUuid(randomUuid);
        assertThat(savedConcept, nullValue());
    }

    /**
     * @verifies return list of concepts with matching name.
     * @see ConceptService#getConceptsByName(String)
     */
    @Test
    public void getConceptsByName_shouldReturnListOfConceptsWithMatchingName() throws Exception {
        assertThat(0, equalTo(conceptService.countAllConcepts()));
        conceptService.saveConcept(concept);
        List<Concept> savedStaticConcepts = conceptService.getConceptsByName(concept.getName());
        assertThat(1, equalTo(savedStaticConcepts.size()));
    }

    /**
     * @verifies return empty list when no concept match the name.
     * @see ConceptService#getConceptsByName(String)
     */
    @Test
    public void getConceptsByName_shouldReturnEmptyListWhenNoConceptMatchTheName() throws Exception {
        String randomName = UUID.randomUUID().toString();
        conceptService.saveConcept(concept);
        List<Concept> savedStaticConcepts = conceptService.getConceptsByName(randomName);
        assertThat(0, equalTo(savedStaticConcepts.size()));
        assertThat(concept, not(isIn(savedStaticConcepts)));
    }

    /**
     * @verifies save concept into local data repository.
     * @see ConceptService#saveConcept(com.muzima.api.model.Concept)
     */
    @Test
    public void saveConcept_shouldSaveConceptIntoLocalDataRepository() throws Exception {
        int conceptCounter = conceptService.countAllConcepts();
        conceptService.saveConcept(concept);
        assertThat(conceptCounter + 1, equalTo(conceptService.countAllConcepts()));
        conceptService.saveConcept(concept);
        assertThat(conceptCounter + 1, equalTo(conceptService.countAllConcepts()));
    }

    /**
     * @verifies save list of concepts into local data repository.
     * @see ConceptService#saveConcepts(java.util.List)
     */
    @Test
    public void saveConcepts_shouldSaveListOfConceptsIntoLocalDataRepository() throws Exception {
        int conceptCounter = conceptService.countAllConcepts();
        conceptService.saveConcepts(concepts);
        assertThat(conceptCounter + concepts.size(), equalTo(conceptService.countAllConcepts()));
        conceptService.saveConcepts(concepts);
        assertThat(conceptCounter + concepts.size(), equalTo(conceptService.countAllConcepts()));
    }

    /**
     * @verifies update concept in local data repository.
     * @see ConceptService#updateConcept(com.muzima.api.model.Concept)
     */
    @Test
    public void updateConcept_shouldUpdateConceptInLocalDataRepository() throws Exception {
        Concept nullConcept = conceptService.getConceptByUuid(concept.getUuid());
        assertThat(nullConcept, nullValue());
        conceptService.saveConcept(concept);
        Concept savedConcept = conceptService.getConceptByUuid(concept.getUuid());
        assertThat(savedConcept, notNullValue());
        assertThat(savedConcept, samePropertyValuesAs(concept));

        String unitName = "mL/cm";
        savedConcept.setUnit(unitName);
        conceptService.updateConcept(savedConcept);
        Concept updatedConcept = conceptService.getConceptByUuid(savedConcept.getUuid());
        assertThat(updatedConcept, not(samePropertyValuesAs(concept)));
        assertThat(updatedConcept.getUnit(), equalTo(unitName));
    }

    /**
     * @verifies update list of concepts in local data repository.
     * @see ConceptService#updateConcepts(java.util.List)
     */
    @Test
    public void updateConcepts_shouldUpdateListOfConceptsInLocalDataRepository() throws Exception {
        int counter = 0;
        conceptService.saveConcepts(concepts);
        List<Concept> savedConcepts = conceptService.getAllConcepts();
        for (Concept concept : savedConcepts) {
            concept.setUnit("New Unit For Concept: " + counter++);
        }
        conceptService.updateConcepts(savedConcepts);
        List<Concept> updatedConcepts = conceptService.getAllConcepts();
        for (Concept updatedConcept : updatedConcepts) {
            for (Concept concept : concepts) {
                assertThat(updatedConcept, not(samePropertyValuesAs(concept)));
            }
            assertThat(updatedConcept.getUnit(), containsString("New Unit For Concept"));
        }
    }

    /**
     * @verifies delete concept from local data repository.
     * @see ConceptService#deleteConcept(com.muzima.api.model.Concept)
     */
    @Test
    public void deleteConcept_shouldDeleteConceptFromLocalDataRepository() throws Exception {
        assertThat(conceptService.getAllConcepts(), hasSize(0));
        conceptService.saveConcepts(concepts);
        int conceptCount = concepts.size();
        assertThat(conceptService.getAllConcepts(), hasSize(conceptCount));
        for (Concept concept : concepts) {
            conceptService.deleteConcept(concept);
            assertThat(conceptService.getAllConcepts(), hasSize(--conceptCount));
        }
    }

    /**
     * @verifies delete list of concepts from local data repository.
     * @see ConceptService#deleteConcepts(java.util.List)
     */
    @Test
    public void deleteConcepts_shouldDeleteListOfConceptsFromLocalDataRepository() throws Exception {
        assertThat(conceptService.getAllConcepts(), hasSize(0));
        conceptService.saveConcepts(concepts);
        List<Concept> savedConcepts = conceptService.getAllConcepts();
        assertThat(savedConcepts, hasSize(concepts.size()));
        conceptService.deleteConcepts(savedConcepts);
        assertThat(conceptService.getAllConcepts(), hasSize(0));
    }

    /**
     * @verifies return all concepts stored in the local data repository.
     * @see ConceptService#getAllConcepts()
     */
    @Test
    public void getAllConcepts_shouldReturnAllConceptsStoredInTheLocalDataRepository() throws Exception {
        assertThat(conceptService.countAllConcepts(), equalTo(0));
        assertThat(conceptService.getAllConcepts(), hasSize(0));
        conceptService.saveConcepts(concepts);
        List<Concept> savedConcepts = conceptService.getAllConcepts();
        assertThat(savedConcepts.size(), equalTo(concepts.size()));
        assertThat(savedConcepts, hasSize(concepts.size()));
    }

    /**
     * @verifies return number of concepts stored in the local data repository.
     * @see ConceptService#countAllConcepts()
     */
    @Test
    public void countAllConcepts_shouldReturnNumberOfConceptsStoredInTheLocalDataRepository() throws Exception {
        assertThat(conceptService.countAllConcepts(), equalTo(0));
        assertThat(conceptService.getAllConcepts(), hasSize(0));
        conceptService.saveConcepts(concepts);
        List<Concept> savedConcepts = conceptService.getAllConcepts();
        assertThat(savedConcepts.size(), equalTo(concepts.size()));
        assertThat(savedConcepts, hasSize(concepts.size()));
    }
}
