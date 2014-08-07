/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConceptTest {
    @Test
    public void shouldSetSynonymsBasedOnConcepts() {
        Concept concept = new Concept();
        List<ConceptName> conceptNames = asList(conceptName("concept1"),
                conceptName("concept2"));

        concept.setConceptNames(conceptNames);
        String synonyms = concept.getSynonyms();

        assertThat(synonyms, is("concept1"));
    }

    @Test
    public void shouldReturnOneSynonymAndAddSuffixForRemainingConceptNames() {
        Concept concept = new Concept();
        List<ConceptName> conceptNames = asList(conceptName("concept1"),
                conceptName("concept2"), conceptName("concept3"));

        concept.setConceptNames(conceptNames);
        String synonyms = concept.getSynonyms();

        assertThat(synonyms, is("concept1 (1 more.)"));
    }

    @Test
    public void shouldKnowWhenAGivenNameIsThePreferred() throws Exception {
        final String name = "PreferredName";
        Concept aConcept = new Concept();
        aConcept.setConceptNames(Arrays.asList(conceptName(name)));
        assertThat(aConcept.containsNameIgnoreLowerCase(name), is(true));
    }

    @Test
    public void shouldIgnoreSpaceInEitherName() throws Exception {
        final String name = " PreferredName ";
        Concept aConcept = new Concept();
        aConcept.setConceptNames(Arrays.asList(conceptName(name)));
        assertThat(aConcept.containsNameIgnoreLowerCase(name + "    "), is(true));
    }

    @Test
    public void shouldKnowWhenAGivenNameIsNotThePreferred() throws Exception {
        final String preferredName = "PreferredName";
        final String nonPreferredName = "NonPreferredName";
        Concept aConcept = new Concept();
        aConcept.setConceptNames(Arrays.asList(conceptName(preferredName), conceptName(nonPreferredName)));
        assertThat(aConcept.containsNameIgnoreLowerCase(nonPreferredName), is(true));
    }

    private ConceptName conceptName(String name) {
        ConceptName conceptName = new ConceptName();
        conceptName.setName(name);
        return conceptName;
    }

}
