/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.muzima.api.model.APIName;
import com.muzima.api.service.impl.LastSyncTimeServiceImpl;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 */
public class LastSyncTimeServiceTest {
    @Test
    public void shouldAskToDownloadAllObservationHistoryWhenNoConceptIsProvided() throws Exception {
        LastSyncTimeService lastSyncTimeService = new LastSyncTimeServiceImpl();
        Date lastSyncTime = lastSyncTimeService.getLastSyncTimeFor(APIName.DOWNLOAD_OBSERVATIONS, "patientUuid;");
        assertThat(lastSyncTime, nullValue());
    }

    @Test
    public void shouldAskToDownloadAllObservationHistoryWhenNoPatientIsProvided() throws Exception {
        LastSyncTimeService lastSyncTimeService = new LastSyncTimeServiceImpl();
        Date lastSyncTime = lastSyncTimeService.getLastSyncTimeFor(APIName.DOWNLOAD_OBSERVATIONS, ";conceptUuid");
        assertThat(lastSyncTime, nullValue());
    }

    @Test(expected = LastSyncTimeService.IncorrectParamSignatureException.class)
    public void throwExceptionIfTooManySubPartsofParamSignatureForObservationIsPassed() throws Exception {
        LastSyncTimeService lastSyncTimeService = new LastSyncTimeServiceImpl();
        lastSyncTimeService.getLastSyncTimeFor(APIName.DOWNLOAD_OBSERVATIONS, "uuid;uuid;conceptUuid");
    }

    @Test(expected = LastSyncTimeService.IncorrectParamSignatureException.class)
    public void throwExceptionIfNotEnoughSubPartsofParamSignatureForObservationIsPassed() throws Exception {
        LastSyncTimeService lastSyncTimeService = new LastSyncTimeServiceImpl();
        lastSyncTimeService.getLastSyncTimeFor(APIName.DOWNLOAD_OBSERVATIONS, "uuid");
    }
}
