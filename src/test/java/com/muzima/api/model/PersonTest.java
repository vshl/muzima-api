package com.muzima.api.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PersonTest {
    @Test
    public void shouldReturnCorrectDisplayName(){
        PersonName personName = new PersonName();
        String familyName = "Family";
        personName.setFamilyName(familyName);
        String givenName = "Given";
        personName.setGivenName(givenName);
        String middleName = "Middle";
        personName.setMiddleName(middleName);
        Person person = new Person();
        person.addName(personName);

        String displayName = familyName + ", " + givenName + " " + middleName;
        assertThat(person.getDisplayName(), is(displayName));
    }

    @Test
    public void shouldReturnEmptyDisplayNameForPersonWithNullNames() throws Exception {
        PersonName personName = new PersonName();
        Person person = new Person();
        person.addName(personName);

        assertThat(person.getDisplayName(), is(""));
    }
}
