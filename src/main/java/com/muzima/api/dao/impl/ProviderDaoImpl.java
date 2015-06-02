package com.muzima.api.dao.impl;

import com.muzima.api.dao.ProviderDao;
import com.muzima.api.model.Provider;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public Provider getBySystemId(String systemId) throws IOException {

        Provider provider = null;
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(systemId)) {
            Filter filter = FilterFactory.createFilter("identifier", systemId);
            filters.add(filter);
        }
        List<Provider> providers = service.getObjects(filters, daoClass);
        if (!CollectionUtil.isEmpty(providers)) {
            if (providers.size() > 1)
                throw new IOException("Unable to uniquely identify a provider using the systemId");
            provider = providers.get(0);
        }
        return provider;
    }
}
