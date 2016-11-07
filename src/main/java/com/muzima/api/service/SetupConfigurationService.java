package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.SetupConfiguration;
import com.muzima.api.model.SetupConfigurationTemplate;
import com.muzima.api.service.impl.SetupConfigurationServiceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@ImplementedBy(SetupConfigurationServiceImpl.class)
public interface SetupConfigurationService extends MuzimaInterface {

    /**
     * Download all setup configurations with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the Configuration to be downloaded. When empty, will return all Configurations available.
     * @throws IOException when search api unable to process the resource.
     * @should download all setup configurations with partially matched name.
     * @should download all setup configurations when name is empty.
     */
    List<SetupConfiguration> downloadSetupConfigurationsByName(final String name) throws IOException;
    /**
     * Download all setup configurations with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the Configuration to be downloaded. When empty, will return all Configurations available.
     * @param syncDate last sync date of the Configuration
     * @throws IOException when search api unable to process the resource.
     * @should download all setup configurations with partially matched name.
     * @should download all setup configurations when name is empty.
     */
    List<SetupConfiguration> downloadSetupConfigurationsByName(final String name, final Date syncDate) throws IOException;

    /**
     * @return all setup configurations or empty list when no configuration is available.
     * @throws IOException when search api unable to process the resource.
     * @should return all setup configurations.
     * @should return empty list when no setup configuration is registered.
     */
    List<SetupConfiguration> getAllSetupConfigurations() throws IOException;

    /**
     * Count the total number of setup configurations in the local lucene repository.
     *
     * @return number of cohorts.
     * @throws IOException when search api unable to process the resource.
     * @should return number of setup configurations in the local repository.
     */
    Integer countAllSetupConfigurations() throws IOException;

    /**
     * Save setup configuration template to the repository.
     *
     * @param setupConfigurations the setup configurations to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save the list of setup configurations to local data repository.
     */
    void saveSetupConfigurations(List<SetupConfiguration> setupConfigurations) throws IOException;

    /**
     * Download setup configuration template by the uuid of the setup configuration associated with the setup configuration template.
     *
     * @param uuid the uuid of the setup configuration.
     * @return the setup configuration template with matching uuid downloaded from the server.
     * @throws IOException when search api unable to process the resource.
     * @should download the setup configuration template by the uuid of the setup configuration.
     */
    SetupConfigurationTemplate downloadSetupConfigurationTemplateByUuid(final String uuid) throws IOException;

    /**
     * Save setup configuration template to the repository.
     *
     * @param setupConfigurationTemplate the setup configuration template to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save the setup configuration template to local data repository.
     */
    void saveSetupConfigurationTemplate(final SetupConfigurationTemplate setupConfigurationTemplate) throws IOException;

    /**
     * Get a setup configuration template by the uuid.
     *
     * @param uuid the setup configuration template uuid.
     * @return the setup configuration template.
     * @throws IOException when search api unable to process the resource.
     * @should get the setup configuration template by the uuid.
     */
    SetupConfigurationTemplate getSetupConfigurationTemplate(final String uuid) throws IOException;
}
