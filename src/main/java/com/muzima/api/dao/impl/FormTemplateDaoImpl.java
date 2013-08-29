/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
