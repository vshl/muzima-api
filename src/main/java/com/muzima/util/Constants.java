/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.util;

public class Constants {

    public static final String DATE_FORMAT = "dd/MMM/yyyy";

    public static final int TYPE_NUMERIC = 1;

    public static final int TYPE_DATE = 2;

    public static final int TYPE_STRING = 3;
    /*
     * Context configuration.
     */
    public static final String LUCENE_DIRECTORY_PATH = "configuration.lucene.directory";

    public static final String LUCENE_DEFAULT_FIELD = "configuration.lucene.field.key";

    public static final String LUCENE_USE_ENCRYPTION = "configuration.lucene.encryption";

    public static final String LUCENE_USE_COMPRESSION = "configuration.lucene.compression";

    public static final String LUCENE_ENCRYPTION_KEY = "configuration.lucene.encryption.key";

    public static final String RESOURCE_CONFIGURATION_PATH = "configuration.resource.path";

    public static final String RESOURCE_CONFIGURATION_STRING = "configuration.resource.string";
    /*
     * OpenMRS server configuration.
     */
    public static final String CONNECTION_SERVER = "connection.openmrs.server";

    public static final String CONNECTION_USERNAME = "connection.openmrs.username";

    public static final String CONNECTION_PASSWORD = "connection.openmrs.password";
    /*
     * Resource configuration.
     */
    public static final String LOCAL_COHORT_MEMBER_RESOURCE = "Local Cohort Member Resource";

    public static final String LOCAL_CREDENTIAL_RESOURCE = "Local Credential Resource";

    public static final String LOCAL_FORM_DATA_RESOURCE = "Local Form Data Resource";

    public static final String SEARCH_STATIC_COHORT_RESOURCE = "Search Static Cohort Resource";

    public static final String SEARCH_DYNAMIC_COHORT_RESOURCE = "Search Dynamic Cohort Resource";

    public static final String SEARCH_FORM_RESOURCE = "Search Form Resource";

    public static final String SEARCH_FORM_TEMPLATE_RESOURCE = "Search Form Template Resource";

    public static final String SEARCH_PATIENT_RESOURCE = "Search Patient Resource";

    public static final String SEARCH_PRIVILEGE_RESOURCE = "Search Privilege Resource";

    public static final String SEARCH_ROLE_RESOURCE = "Search Role Resource";

    public static final String SEARCH_USER_RESOURCE = "Search User Resource";

    public static final String SEARCH_LOCATION_RESOURCE = "Search Location Resource";

    public static final String UUID_DYNAMIC_COHORT_RESOURCE = "Uuid Dynamic Cohort Resource";

    public static final String UUID_STATIC_COHORT_RESOURCE = "Uuid Static Cohort Resource";

    public static final String UUID_FORM_RESOURCE = "Uuid Form Resource";

    public static final String UUID_FORM_TEMPLATE_RESOURCE = "Uuid Form Template Resource";

    public static final String UUID_NOTIFICATION_RESOURCE = "Uuid Notification Resource";

    public static final String SENDER_NOTIFICATION_RESOURCE = "Sender Notification Resource";

    public static final String RECEIVER_NOTIFICATION_RESOURCE = "Receiver Notification Resource";

    public static final String UUID_PATIENT_RESOURCE = "Uuid Patient Resource";

    public static final String UUID_PRIVILEGE_RESOURCE = "Uuid Privilege Resource";

    public static final String UUID_ROLE_RESOURCE = "Uuid Role Resource";

    public static final String UUID_USER_RESOURCE = "Uuid User Resource";

    public static final String STATIC_COHORT_DATA_RESOURCE = "Static Cohort Data Resource";

    public static final String DYNAMIC_COHORT_DATA_RESOURCE = "Dynamic Cohort Data Resource";

    public static final String SEARCH_CONCEPT_RESOURCE = "Search Concept Resource";

    public static final String UUID_CONCEPT_RESOURCE = "Uuid Concept Resource";

    public static final String SEARCH_CONCEPT_NUMERIC_RESOURCE = "Search Concept Numeric Resource";

    public static final String UUID_CONCEPT_NUMERIC_RESOURCE = "Uuid Concept Numeric Resource";

    public static final String SEARCH_ENCOUNTER_RESOURCE = "Search Encounter Resource";

    public static final String UUID_ENCOUNTER_RESOURCE = "Uuid Encounter Resource";

    public static final String SEARCH_OBSERVATION_CODED_RESOURCE = "Search Observation Coded Resource";

    public static final String SEARCH_OBSERVATION_NON_CODED_RESOURCE = "Search Observation Non Coded Resource";

    public static final String UUID_OBSERVATION_RESOURCE = "Uuid Observation Resource";

    public static final String UUID_REGISTRATION_RESOURCE = "Uuid Registration Resource";

    public static final String CONCEPT_CREATED_ON_PHONE = "ConceptCreatedOnPhone";

    public static final String UUID_LAST_SYNC_TIME = "Uuid Last Sync Time Resource";

    public static final String UUID_LOCATION_RESOURCE = "Uuid Location Resource";

    public static final String UUID_SEPARATOR = ",";

    public static final String UUID_TYPE_SEPARATOR = ";";

    public static final String OBSERVATION_CREATED_ON_PHONE = "observationFromPhoneUuid";
}
