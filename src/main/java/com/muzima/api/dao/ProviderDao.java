package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.ProviderDaoImpl;
import com.muzima.api.model.Provider;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Created by vikas on 09/03/15.
 */

@ImplementedBy(ProviderDaoImpl.class)
public interface ProviderDao extends OpenmrsDao<Provider> {


    /**
     * Get a provider by the user name of the provider.
     *
     * @param providerName the name of the provider
     * @return provider with matching provider.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    Provider getByProviderByName(final String providerName) throws ParseException, IOException, ParseException;

    /**
     * Get providers by the name of the provider. Passing empty string will returns all registered providers.
     *
     * @param name the partial name of the provider or empty string.
     * @return the list of all matching providers on the provider name.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    List<Provider> getMatchingProvidersByName(final String name) throws ParseException, IOException;

    Provider getBySystemId(String systemId) throws IOException;
}
