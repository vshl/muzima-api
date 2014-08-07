/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.FormTemplateDaoImpl;
import com.muzima.api.model.FormTemplate;

import java.io.IOException;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(FormTemplateDaoImpl.class)
public interface FormTemplateDao extends OpenmrsDao<FormTemplate> {

    /**
     * Check whether the form with the uuid exists in the local data repository or not.
     *
     * @param formUuid the uuid of the form object.
     * @return true when the form template associated with the form object is already downloaded.
     * @throws IOException when search api unable to process the resource.
     */
    Boolean exists(final String formUuid) throws IOException;
}
