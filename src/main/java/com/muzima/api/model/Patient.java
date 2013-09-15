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
package com.muzima.api.model;

import com.muzima.search.api.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {

    private List<PatientIdentifier> identifiers;

    public void addIdentifier(final PatientIdentifier identifier) {
        getIdentifiers().add(identifier);
    }

    public List<PatientIdentifier> getIdentifiers() {
        if (identifiers == null) {
            identifiers = new ArrayList<PatientIdentifier>();
        }
        return identifiers;
    }

    public void setIdentifiers(final List<PatientIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    /**
     * Get the patient identifier
     *
     * @return the patient identifier
     */
    public String getIdentifier() {
        String identifier = StringUtil.EMPTY;
        for (PatientIdentifier patientIdentifier : getIdentifiers()) {
            identifier = patientIdentifier.getIdentifier();
            if (patientIdentifier.isPreferred()) {
                return identifier;
            }
        }
        return identifier;
    }
}
