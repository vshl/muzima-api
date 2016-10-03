/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.LastSyncTimeDao;
import com.muzima.api.model.APIName;
import com.muzima.api.model.LastSyncTime;
import com.muzima.api.service.LastSyncTimeService;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;

import java.io.IOException;
import java.util.Date;

import static com.muzima.util.Constants.UUID_TYPE_SEPARATOR;

public class LastSyncTimeServiceImpl implements LastSyncTimeService {

    @Inject
    private LastSyncTimeDao lastSyncTimeDao;

    @Override
    public Date getLastSyncTimeFor(APIName apiName) throws IOException {
        return getLastSyncTimeFor(apiName, null);
    }

    @Override
    public LastSyncTime getFullLastSyncTimeInfoFor(APIName apiName) throws IOException {
        return lastSyncTimeDao.getLastSyncTime(apiName.toString());
    }

    @Override
    public Date getLastSyncTimeFor(APIName apiName, String paramSignature) throws IOException {
        if (apiName == APIName.DOWNLOAD_OBSERVATIONS && paramSignature != null) {
            String[] paramParts = paramSignature.split(UUID_TYPE_SEPARATOR, -1);
            if (paramParts.length != 2) {
                throw new IncorrectParamSignatureException("Incorrect parameter signature for Observation download");
            }
            if (StringUtil.isEmpty(paramParts[1]) || StringUtil.isEmpty(paramParts[0])) {
                return null;
            }
        }

        LastSyncTime lastSyncTime = lastSyncTimeDao.getLastSyncTime(apiName.toString(), paramSignature);
        if (lastSyncTime == null) {
            lastSyncTime = new LastSyncTime();
        }
        return lastSyncTime.getLastSyncDate();
    }

    @Override
    public void saveLastSyncTime(LastSyncTime lastSyncTime) throws IOException {
        lastSyncTimeDao.save(lastSyncTime, Constants.UUID_LAST_SYNC_TIME);
    }

    @Override
    public void deleteAll() throws IOException {
        lastSyncTimeDao.delete(lastSyncTimeDao.getAll(), Constants.UUID_LAST_SYNC_TIME);
    }
}
