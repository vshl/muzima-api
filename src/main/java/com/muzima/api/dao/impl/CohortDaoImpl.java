/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.CohortDao;
import com.muzima.api.model.Cohort;
import com.muzima.search.api.context.ServiceContext;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CohortDaoImpl extends OpenmrsDaoImpl<Cohort> implements CohortDao {

    private static final String TAG = CohortDao.class.getSimpleName();

    @Inject
    private ServiceContext serviceContext;

    protected CohortDaoImpl() {
        super(Cohort.class);
    }

    /**
     * Download the searchable object matching the uuid. This process involve executing the REST call, pulling the
     * resource and then saving it to local lucene repository.
     *
     * @param resourceParams the parameters to be passed to search object to filter the searchable object.
     * @param resource       resource descriptor used to convert the resource to the correct object.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public List<Cohort> download(final Map<String, String> resourceParams, final String resource) throws IOException {
        List<Cohort> list = new ArrayList<Cohort>();
        for (Searchable searchable : service.loadObjects(resourceParams, serviceContext.getResource(resource))) {
            Cohort cohort = (Cohort) searchable;
            if (StringUtil.equals(resource, Constants.SEARCH_DYNAMIC_COHORT_RESOURCE) ||
                    StringUtil.equals(resource, Constants.UUID_DYNAMIC_COHORT_RESOURCE)) {
                cohort.setDynamic(true);
            } else if (StringUtil.equals(resource, Constants.SEARCH_STATIC_COHORT_RESOURCE) ||
                    StringUtil.equals(resource, Constants.UUID_STATIC_COHORT_RESOURCE)) {
                cohort.setDynamic(false);
            }
            list.add(cohort);
        }
        return list;
    }
}
