package com.muzima.api.dao.impl;

import com.muzima.api.dao.SetupConfigurationTemplateDao;
import com.muzima.api.model.SetupConfigurationTemplate;

public class SetupConfigurationTemplateDaoImpl  extends OpenmrsDaoImpl<SetupConfigurationTemplate> implements SetupConfigurationTemplateDao{
    protected SetupConfigurationTemplateDaoImpl() {
        super(SetupConfigurationTemplate.class);
    }
}
