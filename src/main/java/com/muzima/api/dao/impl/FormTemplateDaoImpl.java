/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao.impl;

import com.muzima.api.dao.FormTemplateDao;
import com.muzima.api.model.FormTemplate;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormTemplateDaoImpl extends OpenmrsDaoImpl<FormTemplate> implements FormTemplateDao {

    private static final String TAG = FormTemplateDaoImpl.class.getSimpleName();

    protected FormTemplateDaoImpl() {
        super(FormTemplate.class);
    }

    /**
     * Check whether the form with the uuid exists in the local data repository or not.
     *
     * @param formUuid the uuid of the form object.
     * @return true when the form template associated with the form object is already downloaded.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public Boolean exists(final String formUuid) throws IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(formUuid)) {
            Filter filter = FilterFactory.createFilter("uuid", formUuid);
            filters.add(filter);
        }
        Integer count = service.countObjects(filters, daoClass);
        if (count > 1) {
            throw new IOException("Unable to uniquely identify an object using key: '" + formUuid + "' in the repository.");
        }
        return (count == 1);
    }
}
