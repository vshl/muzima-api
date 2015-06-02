package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.ProviderDao;
import com.muzima.api.model.Provider;
import com.muzima.api.service.ProviderService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;
import com.muzima.util.DateUtils;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.*;

/**
 * Created by vikas on 11/03/15.
 */
public class ProviderServiceImpl implements ProviderService {
    @Inject
    private ProviderDao providerDao;
    protected ProviderServiceImpl(){

    }

    @Override
    public Provider downloadProvidersBySystemId(final String systemId) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", systemId);
        }};
        Provider provider = null;
        List<Provider> providers = providerDao.download(parameter, Constants.SEARCH_PROVIDER_RESOURCE);
        if (!CollectionUtil.isEmpty(providers)) {
            if (providers.size() > 1) {
                throw new IOException("Unable to uniquely identify a provider record.");
            }
            provider = providers.get(0);
        }
        return provider;
    }

    @Override
    public Provider getProviderBySystemId(String systemId) throws IOException {
        return providerDao.getBySystemId(systemId);
    }

    @Override
    public Provider downloadProviderByUuid(final String uuid) throws IOException {

        Provider provider = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<Provider> providers = providerDao.download(parameter, Constants.UUID_PROVIDER_RESOURCE);
        if (!CollectionUtil.isEmpty(providers)) {
            if (providers.size() > 1) {
                throw new IOException("Unable to uniquely identify a provider record.");
            }
            provider = providers.get(0);
        }
        return provider;
    }

    @Override
    public List<Provider> downloadProvidersByName(final String name, Date syncDate) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        if (syncDate != null) {
            parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
        }
        return sortNameAscending(providerDao.download(parameter, Constants.SEARCH_PROVIDER_RESOURCE));
    }

    @Override
    public Provider getProviderByUuid(String uuid) throws IOException {
        return providerDao.getByUuid(uuid);
    }

    @Override
    public List<Provider> downloadProvidersByName(String name) throws IOException {
        return downloadProvidersByName(name, null);
    }

    @Override
    public Provider saveProvider(Provider provider) throws IOException {
        if(!locationExists(provider)){
            providerDao.save(provider, Constants.UUID_PROVIDER_RESOURCE);
            return provider;
        }
        return null;
    }

    @Override
    public void saveProviders(List<Provider> providers) throws IOException {
        providerDao.save(providers, Constants.UUID_PROVIDER_RESOURCE);
    }

    @Override
    public List<Provider> getAllProviders() throws IOException {
        return sortNameAscending(providerDao.getAll());
    }

    @Override
    public Integer countAllProviders() throws IOException {
        return providerDao.countAll();
    }

    @Override
    public List<Provider> getProvidersByName(String name) throws IOException, ParseException {
        return sortNameAscending(providerDao.getMatchingProvidersByName(name));
    }

    @Override
    public void deleteProvider(Provider provider) throws IOException {
            providerDao.delete(provider, Constants.UUID_PROVIDER_RESOURCE);
    }

    @Override
    public void deleteProviders(List<Provider> providers) throws IOException {
        providerDao.delete(providers, Constants.UUID_PROVIDER_RESOURCE);
    }

    private List<Provider> sortNameAscending(List<Provider> all) {
        Collections.sort(all);
        return all;
    }
    private boolean locationExists(Provider provider) throws IOException {
        return providerDao.getByUuid(provider.getUuid()) != null;
    }
}
