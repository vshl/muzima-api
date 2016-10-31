package com.muzima.api.service;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.SetupConfiguration;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


public class SetupConfigurationServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(SetupConfigurationServiceTest.class.getSimpleName());

    private SetupConfiguration setupConfiguration;
    private List<SetupConfiguration> setupConfigurations;
    private Context context;
    private SetupConfigurationService setupConfigurationService;

    private static int nextInt(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    @Before
    public void prepare() throws Exception {
        String path = System.getProperty("java.io.tmpdir") + "/muzima/" + UUID.randomUUID().toString();
        ContextFactory.setProperty(Constants.LUCENE_DIRECTORY_PATH, path);
        context = ContextFactory.createContext();
        context.setPreferredLocale("en");
        context.openSession();
        if (!context.isAuthenticated()) {
            context.authenticate("admin", "test", "http://demo2.muzima.org", false);
        }
        setupConfigurationService = context.getSetupConfigurationService();
        setupConfigurations = setupConfigurationService.downloadSetupConfigurationsByName(StringUtil.EMPTY);
        logger.debug("Number of downloaded setup Configurations: {}", setupConfigurations.size());
        setupConfiguration = setupConfigurations.get(nextInt(setupConfigurations.size()));
    }

    @After
    public void cleanUp() throws Exception {
        String lucenePath = ContextFactory.getProperty(Constants.LUCENE_DIRECTORY_PATH);
        File luceneDirectory = new File(lucenePath);
        for (String filename : luceneDirectory.list()) {
            File file = new File(luceneDirectory, filename);
            Assert.assertTrue(file.delete());
        }
        context.deauthenticate();
        context.closeSession();
    }

    /**
     * @verifies download all setup configurations with partially matched name.
     * @see SetupConfigurationService#downloadSetupConfigurationsByName(String)
     */
    @Test
    public void downloadSetupConfigurationsByName_shouldDownloadAllSetupConfigurationsWithPartiallyMatchedName() throws Exception {
        String name = setupConfiguration.getName();
        String partialName = name.substring(0, name.length() - 1);
        List<SetupConfiguration> downloadedSetupConfigurations = setupConfigurationService.downloadSetupConfigurationsByName(partialName);
        for (SetupConfiguration downloadedSetupConfiguration : downloadedSetupConfigurations) {
            assertThat(downloadedSetupConfiguration.getName(), containsString(partialName));
        }
    }

    /**
     * @verifies download all setup configurations when name is empty.
     * @see SetupConfigurationService#downloadSetupConfigurationsByName(String)
     */
    @Test
    public void downloadSetupConfigurationsByName_shouldDownloadAllSetupConfigurationsWhenNameIsEmpty() throws Exception {
        List<SetupConfiguration> downloadedSetupConfigurations = setupConfigurationService.downloadSetupConfigurationsByName(StringUtil.EMPTY);
        assertThat(downloadedSetupConfigurations, hasSize(setupConfigurations.size()));
    }

    /**
     * @verifies count setup configurations.
     * @see SetupConfigurationService#countAllSetupConfigurations()
     */
    @Test
    public void countSetupConfigurations_shouldReturnCorrectSetupConfigurationsCount() throws Exception{
        assertThat(0, equalTo(setupConfigurationService.countAllSetupConfigurations()));
        setupConfigurationService.saveSetupConfigurations(setupConfigurations);
        assertThat(setupConfigurationService.countAllSetupConfigurations(), equalTo(setupConfigurations.size()));
    }

    /**
     * @verifies get all setup configurations when no setup configuration is registered.
     * @see SetupConfigurationService#getAllSetupConfigurations()
     */
    @Test
    public void getAllSetupConfigurations_shouldReturnEmptyListWhenNoSetupConfigurationIsRegistered() throws Exception{
        List<SetupConfiguration> setupConfigurations = setupConfigurationService.getAllSetupConfigurations();
        assertThat(setupConfigurations, hasSize(0));
    }

    /**
     * @verifies get all setup configurations when setup configurations exist.
     * @see SetupConfigurationService#getAllSetupConfigurations()
     */
    @Test
    public void getAllSetupConfigurations_shouldReturnAllSetupConfigurationsWhenSetupConfigurationsExist() throws Exception{
        setupConfigurationService.saveSetupConfigurations(setupConfigurations);
        List<SetupConfiguration> savedSetupConfigurations = setupConfigurationService.getAllSetupConfigurations();
        assertThat(savedSetupConfigurations, hasSize(setupConfigurations.size()));
    }

    /**
     * @verifies get all setup configurations when setup configurations exist.
     * @see SetupConfigurationService#getAllSetupConfigurations()
     */
    @Test
    public void saveSetupConfigurations_shouldReturnAllSetupConfigurationsWhenSetupConfigurationsExist() throws Exception{
        setupConfigurationService.saveSetupConfigurations(setupConfigurations);
        List<SetupConfiguration> savedSetupConfigurations = setupConfigurationService.getAllSetupConfigurations();
        assertThat(savedSetupConfigurations, hasSize(setupConfigurations.size()));
    }
}
