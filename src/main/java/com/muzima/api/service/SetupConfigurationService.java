package com.muzima.api.service;

import com.google.inject.ImplementedBy;
import com.muzima.api.model.SetupConfiguration;
import com.muzima.api.model.SetupConfigurationTemplate;
import com.muzima.api.service.impl.SetupConfigurationServiceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by savai on 10/21/16.
 */
@ImplementedBy(SetupConfigurationServiceImpl.class)
public interface SetupConfigurationService extends MuzimaInterface {

    /**
     * Download all forms with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the Configuration to be downloaded. When empty, will return all Configurations available.
     * @throws IOException when search api unable to process the resource.
     * @should download all form with partially matched name.
     * @should download all form when name is empty.
     */
    List<SetupConfiguration> downloadSetupConfigurationsByName(final String name) throws IOException;
    /**
     * Download all forms with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the Configuration to be downloaded. When empty, will return all Configurations available.
     * @param syncDate last sync date of the Configuration
     * @throws IOException when search api unable to process the resource.
     * @should download all Configurations with partially matched name.
     * @should download all Configurations when name is empty.
     */
    List<SetupConfiguration> downloadSetupConfigurationsByName(final String name, final Date syncDate) throws IOException;

    /**
     * @return all setup configurations or empty list when no configuration is available.
     * @throws IOException when search api unable to process the resource.
     * @should return all setup configurations.
     * @should return empty list when no setup configuration is registered.
     */
    List<SetupConfiguration> getAllSetupConfigurations() throws IOException;

    void saveSetupConfigurations(List<SetupConfiguration> setupConfigurations) throws IOException;
    /**
     * Download form template by the uuid of the form associated with the form template.
     *
     * @param uuid the uuid of the form.
     * @return the form template with matching uuid downloaded from the server.
     * @throws IOException when search api unable to process the resource.
     * @should download the form template by the uuid of the form.
     */
    SetupConfigurationTemplate downloadSetupConfigurationTemplateByUuid(final String uuid) throws IOException;
    void saveSetupConfigurationTemplate(final SetupConfigurationTemplate setupConfigurationTemplate) throws IOException;
    SetupConfigurationTemplate getSetupConfigurationTemplate(final String uuid) throws IOException;
}
