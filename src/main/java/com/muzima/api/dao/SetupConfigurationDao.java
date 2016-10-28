package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.SetupConfigurationDaoImpl;
import com.muzima.api.model.SetupConfiguration;


@ImplementedBy(SetupConfigurationDaoImpl.class)
public interface SetupConfigurationDao extends OpenmrsDao<SetupConfiguration> {
}
