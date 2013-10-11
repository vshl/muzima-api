package com.muzima.api.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ObservationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGetNumericValueAsStringForPreciseConcept() throws Exception {
        Observation observation = createObservation(Concept.NUMERIC_TYPE);
        observation.getConcept().setPrecise(true);
        observation.setValueNumeric(12.432423);
        assertThat(observation.getValueAsString(), is("12.432423"));
    }

    @Test
    public void shouldGetNumericValueAsStringForNonPreciseConcept() throws Exception {
        Observation observation = createObservation(Concept.NUMERIC_TYPE);
        observation.getConcept().setPrecise(false);
        observation.setValueNumeric(12.432423);
        assertThat(observation.getValueAsString(), is("12"));
    }

    @Test
    public void shouldReturnEmptyStringForNullValues() throws Exception {
        assertThat(createObservation(Concept.NUMERIC_TYPE).getValueAsString(), is(""));
    }

    @Test
    public void shouldGetDateValueAsString() throws Exception {
        Observation observation = createObservation(Concept.DATE_TYPE);
        Date valueDatetime = new Date();
        observation.setValueDatetime(valueDatetime);
        assertThat(observation.getValueAsString(), is(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(valueDatetime)));
    }

    @Test
    public void shouldGetCodedValueAsString() throws Exception {
        Observation observation = createObservation(Concept.CODED_TYPE);
        Concept valueCoded = getValueConcept();

        observation.setValueCoded(valueCoded);
        assertThat(observation.getValueAsString(), is(valueCoded.getName()));
    }

    @Test
    public void shouldGetValueTextAsString() throws Exception {
        Observation observation = createObservation(null);
        observation.setValueText("text");
        assertThat(observation.getValueAsString(), is("text"));

    }

    @Test
    public void shouldThrowUnsupportedExceptionIfConceptIs() throws Exception {
        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("The concept has not been loaded fully");

        Observation observation = createObservation(Concept.CODED_TYPE);
        Concept conceptNotLoaded = new Concept();
        conceptNotLoaded.setUuid("uuid");
        observation.setConcept(conceptNotLoaded);
        observation.getValueAsString();
    }

    @Test
    public void shouldOnlyShowTheIntegerPartWhenThePreciseIsFalse() throws Exception {
        Observation observation = createObservation(Concept.NUMERIC_TYPE);
        observation.setValueNumeric(10.0);
        observation.getConcept().setPrecise(false);
        assertThat(observation.getValueAsString(), is("10"));
    }

    private Concept getValueConcept() {
        Concept valueCoded = new Concept();
        ConceptName conceptName = new ConceptName();
        conceptName.setName("AB");
        conceptName.setPreferred(true);
        valueCoded.addName(conceptName);
        return valueCoded;
    }

    private Observation createObservation(final String type) {
        Observation observation = new Observation();
        Concept concept = new Concept();
        ConceptName conceptName = new ConceptName();
        conceptName.setName("name");
        concept.addName(conceptName);
        concept.setConceptType(new ConceptType() {{
            setName(type);
        }});
        observation.setConcept(concept);
        return observation;
    }


}

