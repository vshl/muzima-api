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
}
