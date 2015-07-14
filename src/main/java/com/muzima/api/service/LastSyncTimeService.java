/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.APIName;
import com.muzima.api.model.LastSyncTime;
import com.muzima.api.service.impl.LastSyncTimeServiceImpl;

import java.io.IOException;
import java.util.Date;

@ImplementedBy(LastSyncTimeServiceImpl.class)
public interface LastSyncTimeService extends MuzimaInterface {

    Date getLastSyncTimeFor(APIName apiName) throws IOException, IncorrectParamSignatureException;

    LastSyncTime getFullLastSyncTimeInfoFor(APIName apiName) throws IOException;

    Date getLastSyncTimeFor(APIName apiName, String paramSignature) throws IOException, IncorrectParamSignatureException;

    void saveLastSyncTime(LastSyncTime lastSyncTime) throws IOException;

    void deleteAll() throws IOException;

    public static class IncorrectParamSignatureException extends IOException {
        public IncorrectParamSignatureException(String message) {
            super(message);
        }
    }
}
