/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
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
