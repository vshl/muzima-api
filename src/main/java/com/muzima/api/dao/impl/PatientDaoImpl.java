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
package com.muzima.api.dao.impl;

import com.muzima.api.dao.PatientDao;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Patient;
import com.muzima.search.api.filter.Filter;
import com.muzima.search.api.filter.FilterFactory;
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.search.api.util.StringUtil;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl extends OpenmrsDaoImpl<Patient> implements PatientDao {

    private static final String TAG = PatientDao.class.getSimpleName();

    protected PatientDaoImpl() {
        super(Patient.class);
    }

    /**
     * Get patient by using the identifier.
     *
     * @param identifier the identifier of the patient.
     * @return the patient with matching identifier.
     * @throws IOException when search api unable to process the resource.
     */
    @Override
    public Patient getByIdentifier(final String identifier) throws IOException {
        Patient patient = null;
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(identifier)) {
            Filter filter = FilterFactory.createFilter("identifier", identifier);
            filters.add(filter);
        }
        List<Patient> patients = service.getObjects(filters, daoClass);
        if (!CollectionUtil.isEmpty(patients)) {
            if (patients.size() > 1)
                throw new IOException("Unable to uniquely identify a Patient using the identifier");
            patient = patients.get(0);
        }
        return patient;
    }

    /**
     * Get cohort by the name of the cohort. Passing empty string will returns all registered cohorts.
     *
     * @param name the partial name of the cohort or empty string.
     * @return the list of all matching cohort on the cohort name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<Patient> getPatientByName(final String name) throws IOException, ParseException {
        StringBuilder query = new StringBuilder();
        if (!StringUtil.isEmpty(name)) {
            query.append("givenName:").append(name).append("*").append(" OR ");
            query.append("middleName:").append(name).append("*").append(" OR ");
            query.append("familyName:").append(name).append("*");
        }
        return service.getObjects(query.toString(), daoClass);
    }

    @Override
    public List<Patient> getPatientByName(final String name, final Integer page, final Integer pageSize)
            throws IOException, ParseException {
        StringBuilder query = new StringBuilder();
        if (!StringUtil.isEmpty(name)) {
            query.append("givenName:").append(name).append("*").append(" OR ");
            query.append("middleName:").append(name).append("*").append(" OR ");
            query.append("familyName:").append(name).append("*");
        }
        return service.getObjects(query.toString(), daoClass, page, pageSize);
    }

    /**
     * Search for patients matching the term on name and identifier.
     *
     * @param term the term that should match.
     * @return all patients with matching name or identifier.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     */
    @Override
    public List<Patient> search(final String term) throws ParseException, IOException {
        if (!StringUtil.isEmpty(term)) {
            if (containsDigit(term)) {
                StringBuilder query = new StringBuilder();
                query.append("identifier:").append(term).append("*");
                return service.getObjects(query.toString(), Patient.class);
            } else {
                StringBuilder query = new StringBuilder();
                query.append("givenName:").append(term).append("*").append(" OR ");
                query.append("middleName:").append(term).append("*").append(" OR ");
                query.append("familyName:").append(term).append("*");
                return service.getObjects(query.toString(), Patient.class);
            }
        }
        return service.getObjects(StringUtil.EMPTY, daoClass);
    }

    @Override
    public List<Patient> search(String term, String cohortUuid) throws ParseException, IOException {
        List<Filter> filters = new ArrayList<Filter>();
        if (!StringUtil.isEmpty(cohortUuid)) {
            Filter filter = FilterFactory.createFilter("cohortUuid", cohortUuid);
            filters.add(filter);
        }
        List<CohortMember> cohortMembers = service.getObjects(filters, CohortMember.class);
        List<Patient> patients = search(term);
        List<Patient> matchedPatients = new ArrayList<Patient>();
        for (Patient patient : patients) {
            for (CohortMember cohortMember : cohortMembers) {
                if (cohortMember.getPatientUuid().equalsIgnoreCase(patient.getUuid())) {
                    matchedPatients.add(patient);
                    break;
                }
            }
        }
        return matchedPatients;
    }

    @Override
    public List<Patient> search(final String term, final Integer page, final Integer pageSize)
            throws ParseException, IOException {
        if (!StringUtil.isEmpty(term)) {
            if (containsDigit(term)) {
                StringBuilder query = new StringBuilder();
                query.append("identifier:").append(term).append("*");
                return service.getObjects(query.toString(), Patient.class, page, pageSize);
            } else {
                StringBuilder query = new StringBuilder();
                query.append("givenName:").append(term).append("*").append(" OR ");
                query.append("middleName:").append(term).append("*").append(" OR ");
                query.append("familyName:").append(term).append("*");
                return service.getObjects(query.toString(), Patient.class, page, pageSize);
            }
        }
        return service.getObjects(StringUtil.EMPTY, daoClass, page, pageSize);
    }

    private boolean containsDigit(final String term) {
        for (char c : term.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
