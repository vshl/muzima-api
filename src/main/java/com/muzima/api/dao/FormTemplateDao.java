package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.FormTemplateDaoImpl;
import com.muzima.api.model.FormTemplate;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(FormTemplateDaoImpl.class)
public interface FormTemplateDao extends OpenmrsDao<FormTemplate> {
}
