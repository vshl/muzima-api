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

/**
 * Cohort is a structure to hold collection of patients. Cohort will have a one to one connection with a Member object
 * where we can find the uuid of patients in the cohort.
 */
public class Cohort extends OpenmrsSearchable {

    private boolean dynamic;

    private boolean voided;

    private String name;

    /**
     * Flag whether the cohort is coming from dynamic or static data.
     *
     * @return true if the cohort is dynamic.
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * Set whether the cohort is dynamic or static.
     *
     * @param dynamic the flag for the cohort.
     */
    public void setDynamic(final boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isVoided() {
        return voided;
    }

    public void setVoided(final boolean voided) {
        this.voided = voided;
    }

    /**
     * Get the name for the cohort.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the cohort.
     *
     * @param name the name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }
}
