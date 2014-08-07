/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortMember;
import com.muzima.api.model.Patient;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class CohortMemberAlgorithm extends BaseOpenmrsAlgorithm {

    private CohortAlgorithm cohortAlgorithm;
    private PatientAlgorithm patientAlgorithm;

    public CohortMemberAlgorithm() {
        this.cohortAlgorithm = new CohortAlgorithm();
        this.patientAlgorithm = new PatientAlgorithm();
    }

    /**
     * Implementation of this method will define how the patient will be serialized from the JSON representation.
     *
     * @param serialized the json representation
     * @return the concrete patient object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        CohortMember cohortMember = new CohortMember();
        Object cohortObject = JsonUtils.readAsObject(serialized, "$['cohort']");
        cohortMember.setCohort((Cohort) cohortAlgorithm.deserialize(String.valueOf(cohortObject)));
        Object patientObject = JsonUtils.readAsObject(serialized, "$['patient']");
        cohortMember.setPatient((Patient) patientAlgorithm.deserialize(String.valueOf(patientObject)));
        return cohortMember;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        CohortMember cohortMember = (CohortMember) object;
        JSONObject jsonObject = new JSONObject();
        String cohort = cohortAlgorithm.serialize(cohortMember.getCohort());
        jsonObject.put("cohort", JsonPath.read(cohort, "$"));
        String patient = patientAlgorithm.serialize(cohortMember.getPatient());
        jsonObject.put("patient", JsonPath.read(patient, "$"));
        return jsonObject.toJSONString();
    }
}
