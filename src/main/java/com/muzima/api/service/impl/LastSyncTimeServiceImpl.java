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
        if(apiName == APIName.DOWNLOAD_OBSERVATIONS && paramSignature != null){
            String[] paramParts = paramSignature.split("\\|", -1);
            if(paramParts.length != 2){
                throw new IncorrectParamSignatureException("Incorrect parameter signature for Observation download");
            }
            if(StringUtil.isEmpty(paramParts[1]) || StringUtil.isEmpty(paramParts[0])){
                return null;
            }
        }

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
