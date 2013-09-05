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
package com.muzima.api.dao;

import com.muzima.api.model.OpenmrsSearchable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public interface OpenmrsDao<T extends OpenmrsSearchable> extends SearchableDao<T> {

    /**
     * Download the searchable object matching the uuid. This process involve executing the REST call, pulling the
     * resource and then saving it to local lucene repository.
     *
     * @param resourceParams the parameters to be passed to search object to filter the searchable object.
     * @param resource       resource descriptor used to convert the resource to the correct object.
     * @throws IOException when search api unable to process the resource.
     */
    List<T> download(final Map<String, String> resourceParams, final String resource) throws IOException;

    /**
     * Get the searchable object using the uuid.
     *
     * @param uuid the uuid of the searchable object.
     * @return the searchable object.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    T getByUuid(final String uuid) throws IOException;

    /**
     * Count the number of cohort records in the local lucene repository.
     *
     * @param name the partial name of the cohort.
     * @return number of cohorts in the lucene repository.
     */
    Integer countByName(String name) throws IOException;

    /**
     * Get searchable by the name of the searchable. Passing empty string will returns all
     * registered searchable objects.
     *
     * @param name the partial name of the searchable or empty string.
     * @return the list of all matching searchable on the searchable name.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    List<T> getByName(final String name) throws IOException;

    /**
     * Get searchable by the name of the searchable. Passing empty string will returns all
     * registered searchable objects.
     *
     * @param name     the partial name of the searchable or empty string.
     * @param page     the page number.
     * @param pageSize the number of elements in the page.
     * @return list of objects less or equals than the page size parameter.
     * @throws java.io.IOException
     */
    List<T> getByName(final String name, final Integer page, final Integer pageSize) throws IOException;
}
