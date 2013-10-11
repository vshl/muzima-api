package com.muzima.api.model;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PatientTest {

    private Patient patient;

    @Before
    public void setUp() throws Exception {
        patient = new Patient();
        PatientIdentifier patientId = new PatientIdentifier();
        patientId.setPreferred(true);
        patientId.setIdentifier("Identifier");
        patient.setIdentifiers(asList(patientId));
    }

    @Test
    public void shouldGetSummaryForPatientsWithDefaultGender() throws Exception {
        patient.setNames(asList(personName()));
        assertThat(patient.getSummary(), is("♂ familyName, g m, Identifier"));
    }

    @Test
    public void shouldGetSummaryForMalePatients() throws Exception {
        patient.setNames(asList(personName()));
        patient.setGender("m");
        assertThat(patient.getSummary(), is("♂ familyName, g m, Identifier"));
    }

    @Test
    public void shouldGetSummaryForFemalePatients() throws Exception {
        patient.setNames(asList(personName()));
        patient.setGender("f");
        assertThat(patient.getSummary(), is("♀ familyName, g m, Identifier"));
    }

    @Test
    public void shouldCheckWhetherPatientObjIsSerializable() throws Exception {
        assertThat(new Patient() instanceof Serializable, is(true));
    }

    private PersonName personName() {
        PersonName personName = new PersonName();
        personName.setFamilyName("familyName");
        personName.setGivenName("givenName");
        personName.setMiddleName("middleName");
        personName.setPreferred(true);
        return personName;
    }
}
