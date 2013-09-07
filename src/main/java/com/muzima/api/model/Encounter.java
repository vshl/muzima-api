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

import java.util.Date;

/**
 * TODO: Write brief description about the class here.
 */
public class Encounter extends OpenmrsSearchable {

    private Patient patient;

    private Person provider;

    private Location location;

    private EncounterType encounterType;

    private Date encounterDatetime;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    public Person getProvider() {
        return provider;
    }

    public void setProvider(final Person provider) {
        this.provider = provider;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public EncounterType getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(final EncounterType encounterType) {
        this.encounterType = encounterType;
    }

    public Date getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(final Date encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
    }
}
