package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.EvaluatedCohort;
import com.muzima.api.model.Member;
import com.muzima.search.api.model.object.Searchable;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class EvaluatedCohortAlgorithm extends BaseOpenmrsAlgorithm {

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param serialized the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        EvaluatedCohort evaluatedCohort = new EvaluatedCohort();

        Object jsonObject = JsonPath.read(serialized, "$");

        Cohort cohort = new Cohort();
        String cohortUuid = JsonPath.read(jsonObject, "$['definition.uuid']");
        cohort.setUuid(cohortUuid);
        String cohortName = JsonPath.read(jsonObject, "$['definition.name']");
        cohort.setName(cohortName);
        evaluatedCohort.setCohort(cohort);

        List<Object> patientList = JsonPath.read(jsonObject, "$['members']");
        for (Object patient : patientList) {
            String patientUuid = JsonPath.read(patient, "$['patient.uuid']");

            Member member = new Member();
            member.setCohortUuid(cohortUuid);
            member.setPatientUuid(patientUuid);

            evaluatedCohort.addMember(member);
        }

        return evaluatedCohort;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        EvaluatedCohort evaluatedCohort = new EvaluatedCohort();
        JSONObject jsonObject = new JSONObject();
        Cohort cohort = evaluatedCohort.getCohort();
        jsonObject.put("definition.uuid", cohort.getUuid());
        jsonObject.put("definition.name", cohort.getName());
        JSONArray memberObjectArray = new JSONArray();
        for (Member member : evaluatedCohort.getMembers()) {
            JSONObject memberObject = new JSONObject();
            memberObject.put("patient.uuid", member.getPatientUuid());
            memberObjectArray.add(memberObject);
        }
        jsonObject.put("members", memberObjectArray);
        return jsonObject.toJSONString();
    }
}
