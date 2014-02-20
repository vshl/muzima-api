package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.LastSyncTimeDao;
import com.muzima.api.model.APIName;
import com.muzima.api.model.LastSyncTime;
import com.muzima.api.service.LastSyncTimeService;
import com.muzima.util.Constants;

import java.io.IOException;
import java.util.Date;

public class LastSyncTimeServiceImpl implements LastSyncTimeService {

    @Inject
    private LastSyncTimeDao lastSyncTimeDao;

    @Override
    public Date getLastSyncTimeFor(APIName apiName) throws IOException {
        return getLastSyncTimeFor(apiName, null);
    }

    @Override
    public Date getLastSyncTimeFor(APIName apiName, String paramSignature) throws IOException {
        LastSyncTime lastSyncTime = lastSyncTimeDao.getLastSyncTime(apiName.toString(), paramSignature);
        if(lastSyncTime == null){
            lastSyncTime =  new LastSyncTime();
        }
        return lastSyncTime.getLastSyncDate();
    }

    @Override
    public void saveLastSyncTime(LastSyncTime lastSyncTime) throws IOException {
        lastSyncTimeDao.save(lastSyncTime, Constants.UUID_LAST_SYNC_TIME);
    }
}
