/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */


package com.muzima.api.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PersonTest {
    @Test
    public void shouldReturnCorrectDisplayName() {
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
