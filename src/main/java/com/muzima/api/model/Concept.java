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
public class Concept extends OpenmrsSearchable implements Comparable<Concept> {

    public static final String NUMERIC_TYPE = "Numeric";

    public static final String CODED_TYPE = "Coded";

    public static final String DATETIME_TYPE = "Datetime";

    public static final String DATE_TYPE = "Date";

    private String unit;

    private boolean precise;

    private ConceptType conceptType;

    private List<ConceptName> conceptNames;

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }

    public boolean isPrecise() {
        return precise;
    }

    public void setPrecise(final boolean precise) {
        this.precise = precise;
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
        for (ConceptName conceptName : getConceptNames()) {
            name = conceptName.getName();
            if (conceptName.isPreferred()) {
                return name;
            }
        }
        return name;
    }

    public boolean isNumeric() {
        boolean numeric = false;
        if (getConceptType() != null) {
            numeric = StringUtil.equals(getConceptType().getName(), NUMERIC_TYPE);
        }
        return numeric;
    }

    public boolean isCoded() {
        boolean coded = false;
        if (getConceptType() != null) {
            coded = StringUtil.equals(getConceptType().getName(), CODED_TYPE);
        }
        return coded;
    }

    public boolean isDatetime() {
        boolean datetime = false;
        if (getConceptType() != null) {
            datetime = (StringUtil.equals(getConceptType().getName(), DATE_TYPE)
                    || StringUtil.equals(getConceptType().getName(), DATETIME_TYPE));
        }
        return datetime;
    }

    @Override
    public int compareTo(Concept otherConcept) {
        return this.getName().toLowerCase().compareTo(otherConcept.getName().toLowerCase());
    }
}
