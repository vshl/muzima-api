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

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.CohortDefinitionDaoImpl;
import com.muzima.api.model.CohortDefinition;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

@ImplementedBy(CohortDefinitionDaoImpl.class)
public interface CohortDefinitionDao extends OpenmrsDao<CohortDefinition> {

    /**
     * Get cohort definition by the name of it. Passing empty string will returns all registered cohort definitions.
     *
     * @param name the partial name of the cohort definition or empty string.
     * @return the list of all matching cohort definition on the name.
     * @throws org.apache.lucene.queryParser.ParseException
     *                             when query parser from lucene unable to parse the query string.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    List<CohortDefinition> getByName(final String name) throws ParseException, IOException;
}
