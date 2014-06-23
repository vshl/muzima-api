/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muzima.api.context;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.muzima.api.module.MuzimaModule;
import com.muzima.api.module.SslModule;
import com.muzima.search.api.module.SearchModule;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public class ContextFactory {
    private static final Map<String, Object> parameters = new HashMap<String, Object>();

    private static final List<Module> modules = new ArrayList<Module>();

    static {
        // override this property to match the location of your resource configurations.
        String resourcePath = "../service/j2l/config.json";
        parameters.put(Constants.RESOURCE_CONFIGURATION_PATH, resourcePath);
        // override this property if using custom folder.
        StringBuilder lucenePath = new StringBuilder();
        lucenePath.append(System.getProperty("java.io.tmpdir"));
        lucenePath.append("/muzima");
        parameters.put(Constants.LUCENE_ENCRYPTION_KEY, "this-is-supposed-to-be-a-secure-key");
        parameters.put(Constants.LUCENE_DIRECTORY_PATH, lucenePath.toString());
        parameters.put(Constants.LUCENE_DEFAULT_FIELD, "uuid");
        parameters.put(Constants.LUCENE_USE_ENCRYPTION, true);
    }

    /**
     * Set a property's value for the </code>ContextFactory</code>'s properties.
     *
     * @param property      the property name.
     * @param propertyValue the property value.
     */
    public static void setProperty(final String property, final String propertyValue) {
        parameters.put(property, propertyValue);
    }

    /**
     * Get a property's value from the </code>ContextFactory</code>'s properties.
     *
     * @param property the property name.
     */
    public static String getProperty(final String property) {
        String propertyValue = StringUtil.EMPTY;
        Object object = getProperties().get(property);
        if (object != null) {
            propertyValue = object.toString();
        }
        return propertyValue;
    }

    /**
     * Get copy of the properties with the current properties as the default value. Changing values in the returned
     * properties will not change the actual values stored inside the context properties. Use the
     * <code>setProperty</code> or <code>setProperties</code> instead.
     *
     * @return copy of the properties with the values from the context's properties as the default.
     */
    public static Map<String, Object> getProperties() {
        return new HashMap<String, Object>(parameters);
    }

    /**
     * Register a custom module to the context factory. The custom module can be used to override the default
     * behavior of the muzima and search API.
     *
     * @param module the module to be registered.
     */
    public static void registerModule(final Module module) {
        modules.add(module);
    }

    /**
     * Create context object for the muzima api. Before requesting a new context object, please set the following
     * properties to suit your need:
     * * Constants.LUCENE_DIRECTORY_PATH
     * * Constants.LUCENE_DEFAULT_FIELD
     * * Constants.RESOURCE_CONFIGURATION_PATH
     *
     * @return a fresh context.
     * @throws IOException when creating context failed.
     */
    public static Context createContext() throws Exception {
        MuzimaModule muzimaModule = new MuzimaModule();
        muzimaModule.setRepositoryPath(getProperty(Constants.LUCENE_DIRECTORY_PATH));
        muzimaModule.setEncryptionKey(getProperty(Constants.LUCENE_ENCRYPTION_KEY));
        muzimaModule.setUseEncryption(false);
        SslModule sslModule = new SslModule();
        SearchModule searchModule = new SearchModule();
        Injector injector = Guice.createInjector(muzimaModule, sslModule, searchModule);
        return new Context(injector);
    }
}
