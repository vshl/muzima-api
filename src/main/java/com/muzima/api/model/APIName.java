/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.model;

public enum APIName {
    DOWNLOAD_OBSERVATIONS, DOWNLOAD_FORMS,
    DOWNLOAD_COHORTS, DOWNLOAD_COHORTS_DATA, DOWNLOAD_ENCOUNTERS, DOWNLOAD_SETUP_CONFIGURATIONS;

    public static APIName getAPIName(String apiName) {
        for (APIName name : values()) {
            if (apiName.equals(name.toString())) {
                return name;
            }
        }
        return null;
    }
}
