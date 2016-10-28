package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.SetupConfigurationDao;
import com.muzima.api.dao.SetupConfigurationTemplateDao;
import com.muzima.api.model.SetupConfiguration;
import com.muzima.api.model.SetupConfigurationTemplate;
import com.muzima.api.service.SetupConfigurationService;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;
import com.muzima.util.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by savai on 10/21/16.
 */
public class SetupConfigurationServiceImpl implements SetupConfigurationService {
    @Inject
    private SetupConfigurationDao setupConfigurationDao;
    @Inject
    private SetupConfigurationTemplateDao setupConfigurationTemplateDao;

    /**
     * {@inheritDoc}
     *
     * @see SetupConfigurationService#downloadSetupConfigurationsByName(String)
     */
    @Override
    public List<SetupConfiguration> downloadSetupConfigurationsByName(final String name) throws IOException {
        return downloadSetupConfigurationsByName(name,null);
    }

    /**
     * {@inheritDoc}
     *
     * @see SetupConfigurationService#downloadSetupConfigurationsByName(String,java.util.Date)
     */
    @Override
    public List<SetupConfiguration> downloadSetupConfigurationsByName(final String name, Date syncDate) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        if (syncDate != null) {
            parameter.put("syncDate", DateUtils.getUtcTimeInIso8601(syncDate));
        }
        return setupConfigurationDao.download(parameter, Constants.SEARCH_SETUP_CONFIGURATION_RESOURCE);
    }

    public List<SetupConfiguration> getAllSetupConfigurations() throws IOException{
        return setupConfigurationDao.getAll();
    }

    public void saveSetupConfigurations(List<SetupConfiguration> setupConfigurations) throws IOException{
        setupConfigurationDao.save(setupConfigurations,Constants.UUID_SETUP_CONFIGURATION_RESOURCE);
    }

    public SetupConfigurationTemplate downloadSetupConfigurationTemplateByUuid(final String uuid) throws IOException{
        SetupConfigurationTemplate setupConfigurationTemplate = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<SetupConfigurationTemplate> templates = setupConfigurationTemplateDao
                .download(parameter,Constants.UUID_SETUP_CONFIGURATION_TEMPLATE_RESOURCE);
        if (!CollectionUtil.isEmpty(templates)){
            if(templates.size() > 1){
                throw new IOException("Unable to uniquely identify a setup config template record.");
            }
            setupConfigurationTemplate = templates.get(0);
        }
        return setupConfigurationTemplate;

    }

    public void saveSetupConfigurationTemplate(final SetupConfigurationTemplate setupConfigurationTemplate) throws IOException{
        setupConfigurationTemplateDao.save(setupConfigurationTemplate,Constants.UUID_SETUP_CONFIGURATION_TEMPLATE_RESOURCE);
    }

    public SetupConfigurationTemplate getSetupConfigurationTemplate(final String uuid) throws IOException{
        return setupConfigurationTemplateDao.getByUuid(uuid);
    }
}
