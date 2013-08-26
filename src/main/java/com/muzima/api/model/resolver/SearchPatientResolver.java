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
package com.muzima.api.model.resolver;


import com.muzima.search.api.util.StringUtil;

import java.io.IOException;
import java.util.Map;

public class SearchPatientResolver extends BaseOpenmrsResolver {

    private static final String REPRESENTATION =
            "?v=custom:(uuid,gender,birthdate,personName.givenName,personName.middleName,personName.familyName," +
                    "patientIdentifier.identifier,patientIdentifier.identifierType.name)";

    /**
     * Return the full REST resource based on the parameters passed to the method.
     *
     * @param resourceParams the parameters of the resource to resolved.
     * @return full uri to the REST resource.
     */
    public String resolve(final Map<String, String> resourceParams) throws IOException {
        StringBuilder paramBuilder = new StringBuilder();
        for (String key : resourceParams.keySet()) {
            paramBuilder.append("&").append(key).append("=").append(resourceParams.get(key));
        }
        return getConfiguration().getServer() + "/ws/rest/v1/patient" + REPRESENTATION + paramBuilder.toString();
    }
}
