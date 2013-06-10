package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.CohortDataDaoImpl;
import com.muzima.api.model.CohortData;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(CohortDataDaoImpl.class)
public interface CohortDataDao extends OpenmrsDao<CohortData> {
}
