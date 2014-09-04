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

public class PersonAttribute extends OpenmrsSearchable {


    private String value;
    private String attribute;

    private PersonAttributeType attributeType;

    public String getValue() {
        return value;
    }


    /**
     * Get the patient attribute
     *
     * @return the patient attribute
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Set the patient attribute
     *
     * @param attribute the patient attribute
     */
    public void setAttribute(final String attribute) {
        this.attribute = attribute;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public PersonAttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(PersonAttributeType attributeType) {
        this.attributeType = attributeType;
    }
}
