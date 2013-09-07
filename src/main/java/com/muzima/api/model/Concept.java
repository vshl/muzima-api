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

/**
 * TODO: Write brief description about the class here.
 */
public class Concept extends OpenmrsSearchable {

    private String unit;

    private ConceptType conceptType;

    private List<ConceptName> conceptNames;

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public ConceptType getConceptType() {
        return conceptType;
    }

    public void setConceptType(final ConceptType conceptType) {
        this.conceptType = conceptType;
    }

    public void addName(final ConceptName conceptName) {
        getConceptNames().add(conceptName);
    }

    public List<ConceptName> getConceptNames() {
        if (conceptNames == null) {
            conceptNames = new ArrayList<ConceptName>();
        }
        return conceptNames;
    }

    public void setConceptNames(final List<ConceptName> conceptNames) {
        this.conceptNames = conceptNames;
    }

    public String getName() {
        String name = StringUtil.EMPTY;
        for (ConceptName conceptName : conceptNames) {
            name = conceptName.getName();
            if (conceptName.isPreferred()) {
                return name;
            }
        }
        return name;
    }
}
