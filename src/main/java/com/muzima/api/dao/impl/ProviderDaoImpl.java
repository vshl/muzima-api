package com.muzima.api.dao.impl;

import com.muzima.api.dao.ProviderDao;
import com.muzima.api.model.Provider;
import com.muzima.search.api.util.StringUtil;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

public class ProviderDaoImpl extends OpenmrsDaoImpl<Provider> implements ProviderDao {

    private static final String TAG = ProviderDaoImpl.class.getSimpleName();

    protected ProviderDaoImpl() {
        super(Provider.class);
    }

    @Override
    public Provider getByProviderByName(String providerName) throws ParseException, IOException {

        StringBuilder query = new StringBuilder();
        if (!StringUtil.isEmpty(providerName)) {
            query.append("name:").append(providerName);
        }
        return service.getObject(query.toString(), daoClass);
    }

    @Override
    public List<Provider> getMatchingProvidersByName(String name) throws ParseException, IOException {
        StringBuilder query = new StringBuilder();
        if (!StringUtil.isEmpty(name)) {
            query.append("name:").append(name);
        }
        return service.getObjects(query.toString(), daoClass);
    }
}
