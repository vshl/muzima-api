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
package com.muzima.api.config;

import com.muzima.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: move this to model because we need to persist it between use.
 */
public class Configuration {

    private Map<String, String> registry;

    public Configuration() {
        registry = new HashMap<String, String>();
    }

    /**
     * Configure this configuration with the username, password and the server.
     *
     * @param username the username.
     * @param password the password.
     * @param server   the server.
     */
    public void configure(final String username, final String password, final String server) {
        setUsername(username);
        setPassword(password);
        setServer(server);
    }

    /**
     * Get the base url of the openmrs server.
     *
     * @return the base url of the openmrs server.
     */
    public String getServer() {
        return registry.get(Constants.CONNECTION_SERVER);
    }

    /**
     * Set the base url of the openmrs server.
     *
     * @param server the base url of the openmrs server.
     */
    public void setServer(final String server) {
        registry.put(Constants.CONNECTION_SERVER, server);
    }

    /**
     * Get the username of the openmrs server.
     *
     * @return the username of the openmrs server.
     */
    public String getUsername() {
        return registry.get(Constants.CONNECTION_USERNAME);
    }

    /**
     * Set the username of the openmrs server.
     *
     * @param username the username of the openmrs server.
     */
    public void setUsername(final String username) {
        registry.put(Constants.CONNECTION_USERNAME, username);
    }

    /**
     * Set the password of the openmrs server.
     *
     * @return the password of the openmrs server.
     */
    public String getPassword() {
        return registry.get(Constants.CONNECTION_PASSWORD);
    }

    /**
     * Set the password of the openmrs server.
     *
     * @param password the password of the openmrs server.
     */
    public void setPassword(final String password) {
        registry.put(Constants.CONNECTION_PASSWORD, password);
    }
}
