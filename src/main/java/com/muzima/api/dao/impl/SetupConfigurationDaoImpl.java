package com.muzima.api.dao.impl;

import com.muzima.api.dao.SetupConfigurationDao;
import com.muzima.api.model.SetupConfiguration;

public class SetupConfigurationDaoImpl extends OpenmrsDaoImpl<SetupConfiguration> implements SetupConfigurationDao {
    protected SetupConfigurationDaoImpl(){
        super(SetupConfiguration.class);
    }
}
