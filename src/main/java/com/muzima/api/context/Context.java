/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.context;

import com.google.inject.Injector;
import com.jayway.jsonpath.JsonPath;
import com.muzima.api.config.Configuration;
import com.muzima.api.model.User;
import com.muzima.api.service.CohortService;
import com.muzima.api.service.FormService;
import com.muzima.api.service.LastSyncTimeService;
import com.muzima.api.service.LocationService;
import com.muzima.api.service.MuzimaInterface;
import com.muzima.api.service.ObservationService;
import com.muzima.api.service.PatientService;
import com.muzima.api.service.UserService;
import com.muzima.search.api.context.ServiceContext;
import com.muzima.search.api.exception.ServiceException;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.model.resolver.Resolver;
import com.muzima.search.api.model.serialization.Algorithm;
import com.muzima.search.api.resource.ObjectResource;
import com.muzima.search.api.resource.Resource;
import com.muzima.search.api.resource.ResourceConstants;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public class Context {

    private static final ThreadLocal<UserContext> userContextHolder = new ThreadLocal<UserContext>();
    private Injector injector;

    Context(final Injector injector) throws Exception {
        this.injector = injector;
        initService();
        initConfiguration();
    }

    /**
     * Initialize the service layer based on the configuration string passed to the search api. The order system will
     * look for the configuration document in the following location:
     * * Stream object in the properties with key: Constants.RESOURCE_CONFIGURATION_STRING
     * * File object in the properties with key: Constants.RESOURCE_CONFIGURATION_STRING
     * * File object in classpath with path defined in the properties with key: Constants.RESOURCE_CONFIGURATION_PATH
     * * File object in filesystem with path defined in the properties with key: Constants.RESOURCE_CONFIGURATION_PATH
     *
     * @throws Exception when the service can't find the input stream.
     */
    private void initService() throws Exception {
        InputStream inputStream = null;
        ServiceContext serviceContext = injector.getInstance(ServiceContext.class);
        String configurationString = ContextFactory.getProperty(Constants.RESOURCE_CONFIGURATION_STRING);
        if (!StringUtil.isEmpty(configurationString)) {
            inputStream = new ByteArrayInputStream(configurationString.getBytes());
        }

        if (inputStream == null) {
            String configurationPath = ContextFactory.getProperty(Constants.RESOURCE_CONFIGURATION_PATH);
            if (!StringUtil.isEmpty(configurationPath)) {
                inputStream = Context.class.getResourceAsStream(configurationPath);
                if (inputStream == null) {
                    inputStream = new FileInputStream(new File(configurationPath));
                }
            }
        }

        if (inputStream == null) {
            throw new IOException(
                    "Unable to find suitable configuration document to setup the service layer!" +
                            "Please configure it using: Constants.RESOURCE_CONFIGURATION_STRING or" +
                            "Constants.RESOURCE_CONFIGURATION_PATH property in the ContextFactory.");
        }
        registerResources(inputStream, serviceContext);
    }

    /**
     * Internal method to register resource configurations inside the input stream.
     *
     * @param inputStream the configuration's input stream.
     * @throws IOException when the parser fail to read the configuration file
     */
    private void registerResources(final InputStream inputStream, final ServiceContext serviceContext) throws Exception {
        List<Object> configurations = JsonPath.read(inputStream, "$['configurations']");
        for (Object configuration : configurations) {
            Resource resource = createResource(String.valueOf(configuration));
            serviceContext.registerResource(resource.getName(), resource);
        }
    }

    /**
     * Internal method to convert configuration string into the resource object.
     *
     * @param configuration the configuration.
     * @return the resource object
     * @throws IOException when the parser fail to read the configuration file
     */
    private Resource createResource(final String configuration) throws Exception {

        String name = JsonPath.read(configuration, ResourceConstants.RESOURCE_NAME);
        String root = JsonPath.read(configuration, ResourceConstants.ROOT_NODE);
        if (StringUtil.isEmpty(root)) {
            throw new ServiceException("Unable to create resource because of missing root node.");
        }

        String searchableName = JsonPath.read(configuration, ResourceConstants.SEARCHABLE_CLASS);
        if (StringUtil.isEmpty(root)) {
            throw new ServiceException("Unable to create resource because of missing searchable node.");
        }
        Class searchableClass = Class.forName(searchableName);
        Searchable searchable = (Searchable) getInjector().getInstance(searchableClass);

        String algorithmName = JsonPath.read(configuration, ResourceConstants.ALGORITHM_CLASS);
        if (StringUtil.isEmpty(root)) {
            throw new ServiceException("Unable to create resource because of missing algorithm node.");
        }
        Class algorithmClass = Class.forName(algorithmName);
        Algorithm algorithm = (Algorithm) getInjector().getInstance(algorithmClass);

        String resolverName = JsonPath.read(configuration, ResourceConstants.RESOLVER_CLASS);
        if (StringUtil.isEmpty(root)) {
            throw new ServiceException("Unable to create resource because of missing resolver node.");
        }
        Class resolverClass = Class.forName(resolverName);
        Resolver resolver = (Resolver) getInjector().getInstance(resolverClass);

        List<String> uniqueFields = new ArrayList<String>();
        String uniqueField = JsonPath.read(configuration, ResourceConstants.UNIQUE_FIELD);
        if (uniqueField != null) {
            uniqueFields = Arrays.asList(StringUtil.split(uniqueField, ","));
        }
        Resource resource = new ObjectResource(name, root, searchable.getClass(), algorithm, resolver);
        Object searchableFields = JsonPath.read(configuration, ResourceConstants.SEARCHABLE_FIELD);
        if (searchableFields instanceof Map) {
            Map map = (Map) searchableFields;
            for (Object fieldName : map.keySet()) {
                Boolean unique = Boolean.FALSE;
                if (uniqueFields.contains(String.valueOf(fieldName))) {
                    unique = Boolean.TRUE;
                }
                String expression = String.valueOf(map.get(fieldName));
                resource.addFieldDefinition(String.valueOf(fieldName), expression, unique);
            }
        }
        return resource;
    }

    /**
     * Initialize the OpenMRS configuration which will be used in the current thread.
     *
     * @throws IOException when the injector which will be used to hold the configuration is not ready.
     */
    private void initConfiguration() throws IOException {
        if (getUserContext() != null) {
            Configuration savedConfiguration = getUserContext().getConfiguration();
            Configuration configuration = getInjector().getInstance(Configuration.class);
            configuration.configure(
                    savedConfiguration.getUsername(), savedConfiguration.getPassword(), savedConfiguration.getServer());
        }
    }

    private UserContext getUserContext() {
        return userContextHolder.get();
    }

    private void setUserContext(final UserContext userContext) {
        userContextHolder.set(userContext);
    }

    private void removeUserContext() {
        userContextHolder.remove();
    }

    /**
     * Open a new session to perform operation on the muzima api. This method will remove current active user (meaning
     * you need to perform authentication again). If you want to re-use the active user, just perform:
     * <pre>
     *     Context context = ContextFactory.createContext();
     * </pre>
     */
    public void openSession() {
        setUserContext(new UserContext());
    }

    /**
     * Close the current active session (effectively removing the authenticated user).
     */
    public void closeSession() {
        removeUserContext();
    }

    /**
     * Perform authentication of the username and password in to the server. When the user is offline, the
     * authentication process will be performed against the local lucene repository.
     *
     * @param username the username to be authenticated.
     * @param password the password of the username to be authenticated.
     * @param server   the remote server where the authentication will be performed.
     * @throws IOException    when the system fail to authenticate the user.
     * @throws ParseException when the system unable to parse the lucene query.
     */
    public void authenticate(final String username, final String password, final String server)
            throws IOException, ParseException {
        Configuration configuration = getInjector().getInstance(Configuration.class);
        configuration.configure(username, password, server);
        getUserContext().setConfiguration(configuration);
        getUserContext().authenticate(username, password, getUserService());
    }

    /**
     * Get currently active user.
     *
     * @return the active user.
     * @throws IOException when the system unable to get the current active user.
     */
    public User getAuthenticatedUser() throws IOException {
        if (getUserContext() == null)
            throw new IOException("UserContext is not ready. You probably missed the openSession() call?");
        return getUserContext().getAuthenticatedUser();
    }

    /**
     * Logging out the user from the muzima api.
     *
     * @throws IOException when the system unable to log out the current user.
     */
    public void deauthenticate() throws IOException {
        if (getUserContext() == null)
            throw new IOException("UserContext is not ready. You probably missed the openSession() call?");
        getUserContext().deauthenticate();
    }

    /**
     * Check whether the current thread have active user or not.
     *
     * @return true when the current thread have active user.
     * @throws IOException when the system unable to determine whether a user is active or not in the current thread.
     */
    public boolean isAuthenticated() throws IOException {
        if (getUserContext() == null)
            throw new IOException("UserContext is not ready. You probably missed the openSession() call?");
        return getUserContext().isAuthenticated();
    }

    private Injector getInjector() throws IOException {
        if (injector == null)
            throw new IOException("Guice is not properly started. We need Guice to wire up the API.");
        return injector;
    }

    /**
     * Get user defined service outside the default service provided by the muzima-api. User can add their own service,
     * and muzima's interceptor should be able to register the service for future use.
     *
     * @param serviceClass the service class
     * @param <T>          the generic type of the service class.
     * @return the custom service class added by user.
     * @throws IOException when the injector unable to find registered class.
     */
    public <T extends MuzimaInterface> T getService(final Class<T> serviceClass) throws IOException {
        return getInjector().getInstance(serviceClass);
    }

    /**
     * Get the cohort service to perform operation related to the cohort object.
     *
     * @return the cohort service class.
     * @throws IOException when the system unable to find the correct service object.
     */
    public CohortService getCohortService() throws IOException {
        return getService(CohortService.class);
    }

    /**
     * Get the form service to perform operation related to the form object.
     *
     * @return the form service class.
     * @throws IOException when the system unable to find the correct service object.
     */
    public FormService getFormService() throws IOException {
        return getService(FormService.class);
    }

    /**
     * Get the observation service to perform operation related to the observation object.
     *
     * @return the observation service class.
     * @throws IOException when the system unable to find the correct service object.
     */
    public ObservationService getObservationService() throws IOException {
        return getService(ObservationService.class);
    }

    /**
     * Get the patient service to perform operation related to the patient object.
     *
     * @return the patient service class.
     * @throws IOException when the system unable to find the correct service object.
     */
    public PatientService getPatientService() throws IOException {
        return getService(PatientService.class);
    }

    /**
     * Get the patient service to perform operation related to the patient object.
     *
     * @return the patient service class.
     * @throws IOException when the system unable to find the correct service object.
     */
    public LastSyncTimeService getLastSyncTimeService() throws IOException {
        return getService(LastSyncTimeService.class);
    }

    /**
     * Get the user service to perform operation related to the user object.
     *
     * @return the user service class.
     * @throws IOException when the system unable to find the correct service object.
     */
    public UserService getUserService() throws IOException {
        return getService(UserService.class);
    }

    /**
     * Get the location service to perform operation related to the location object.
     *
     * @return the location service class.
     * @throws IOException when the system unable to find the correct service object.
     */
    public LocationService getLocationService() throws IOException {
        return getService(LocationService.class);
    }
}
