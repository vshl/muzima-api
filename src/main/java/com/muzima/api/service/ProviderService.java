package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.Provider;
import com.muzima.api.service.impl.ProviderServiceImpl;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by vikas on 11/03/15.
 */
@ImplementedBy(ProviderServiceImpl.class)
public interface ProviderService extends MuzimaInterface {

    Provider downloadProvidersBySystemId(final String systemId) throws IOException;

    Provider getProviderBySystemId(final String systemId) throws IOException;
    /**
     * Download a single Provider record from the Provider rest resource into the local lucene repository.
     *
     * @param uuid the uuid of the Provider.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download Provider with matching uuid.
     */
    Provider downloadProviderByUuid(final String uuid) throws IOException;

    /**
     * Download all Providers with name similar to the partial Provider name passed in the parameter.
     *
     * @param name     the partial name of the Provider to be downloaded. When empty, will return all Providers available.
     * @param syncDate time the Providers where last synched to the server.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download all Providers with partially matched name.
     * @should download all Providers when name is empty.
     */
    List<Provider> downloadProvidersByName(final String name, final Date syncDate) throws IOException;

    /**
     * Get a single Provider record from the local repository with matching uuid.
     *
     * @param uuid the Provider uuid
     * @return Provider with matching uuid or null when no Provider match the uuid
     * @throws java.io.IOException when search api unable to process the resource.
     * @should return Provider with matching uuid
     * @should return null when no Provider match the uuid
     */

    Provider getProviderByUuid(final String uuid) throws IOException;

    /**
     * Download all Providers with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the Providers to be downloaded. When empty, will return all Providers available.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should download all Providers with partially matched name.
     * @should download all Providers when name is empty.
     */
    List<Provider> downloadProvidersByName(final String name) throws IOException;

    /**
     * Save Provider to the local lucene repository.
     *
     * @param provider the Provider to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should save Provider to local data repository.
     */
    Provider saveProvider(final Provider provider) throws IOException;

    /**
     * Save list of Providers to the local lucene repository.
     *
     * @param providers the Providers to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should save Providers to local data repository.
     */
    void saveProviders(final List<Provider> providers) throws IOException;

    /**
     * Get all saved Providers in the local repository.
     *
     * @return all registered Providers or empty list when no Provider is registered.
     * @throws java.io.IOException when search api unable to process the resource.
     * @should return all registered Providers.
     * @should return empty list when no Provider is registered.
     */
    List<Provider> getAllProviders() throws IOException;

    /**
     * Count all Provider objects.
     *
     * @return the total number of Provider objects.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    Integer countAllProviders() throws IOException;

    /**
     * Get list of Providers with name similar to the search term.
     *
     * @param name the Provider name.
     * @return list of all Provider with matching name or empty list when no Provider match the name.
     * @throws org.apache.lucene.queryParser.ParseException when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException    when search api unable to process the resource.
     * @should return list of all Providers with matching name partially.
     * @should return empty list when no Providers match the name.
     */
    List<Provider> getProvidersByName(final String name) throws IOException, ParseException;

    /**
     * Delete a Provider from the local data repository.
     *
     * @param provider the Provider to be deleted.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should delete Provider from local data repository.
     */
    void deleteProvider(final Provider provider) throws IOException;

    /**
     * Delete a list of Providers from the local data repository.
     *
     * @param providers the Providers to be deleted.
     * @throws java.io.IOException when the search api unable to process the resource.
     * @should delete list of Providers from local data repository.
     */
    void deleteProviders(final List<Provider> providers) throws IOException;
}
