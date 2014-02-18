package com.muzima.api.dao.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.LastSyncTimeDao;
import com.muzima.api.model.LastSyncTime;
import com.muzima.search.api.context.ServiceContext;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LastSyncTimeDaoImpl extends OpenmrsDaoImpl<LastSyncTime> implements LastSyncTimeDao {

    @Inject
    private ServiceContext serviceContext;

    protected LastSyncTimeDaoImpl() {
        super(LastSyncTime.class);
    }

    @Override
    public LastSyncTime getLastSyncTime(String apiName) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(apiName)) {
            Filter filter = FilterFactory.createFilter("apiName", apiName);
            filters.add(filter);
        }
        return getRecentEntry(filters);
    }

    @Override
    public LastSyncTime getLastSyncTime(String apiName, String paramSignature) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(apiName)) {
            Filter apiNameFilter = FilterFactory.createFilter("apiName", apiName);
            Filter paramSignFilter = FilterFactory.createFilter("paramSignature", paramSignature);
            filters.add(apiNameFilter);
            filters.add(paramSignFilter);
        }
        return getRecentEntry(filters);
    }

    private LastSyncTime getRecentEntry(List<Filter> filters) throws IOException {
        LastSyncTime lastSyncTime = null;
        List<LastSyncTime> lastSyncTimes = service.getObjects(filters, daoClass);
        if (!CollectionUtil.isEmpty(lastSyncTimes)) {
            if (lastSyncTimes.size() > 1) {
                Collections.sort(lastSyncTimes, Collections.reverseOrder());
            }
            lastSyncTime = lastSyncTimes.get(0);
        }
        return lastSyncTime;
    }
}
