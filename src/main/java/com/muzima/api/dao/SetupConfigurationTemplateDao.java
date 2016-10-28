package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.SetupConfigurationTemplateDaoImpl;
import com.muzima.api.model.SetupConfigurationTemplate;

@ImplementedBy(SetupConfigurationTemplateDaoImpl.class)
public interface SetupConfigurationTemplateDao  extends OpenmrsDao<SetupConfigurationTemplate>{
}
