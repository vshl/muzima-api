package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.LastSyncTimeDaoImpl;
import com.muzima.api.model.LastSyncTime;

import java.io.IOException;

@ImplementedBy(LastSyncTimeDaoImpl.class)
public interface LastSyncTimeDao extends OpenmrsDao<LastSyncTime> {
    LastSyncTime getLastSyncTime(String apiName) throws IOException;

    LastSyncTime getLastSyncTime(String apiName, String paramSignature) throws IOException;
}
