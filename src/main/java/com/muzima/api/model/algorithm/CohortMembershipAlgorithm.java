/**
 * Copyright (c) 2014 - 2017. The Trustees of Indiana University, Moi University, and Vanderbilt
 * University Medical Center.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional healthcare
 * disclaimer. If the user is an entity intending to commercialize any application that uses this code in
 * a for-profit venture, please contact the copyright holder.
 */
package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Cohort;
import com.muzima.api.model.CohortMembership;
import com.muzima.api.model.Patient;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class CohortMembershipAlgorithm extends BaseOpenmrsAlgorithm {

	private CohortAlgorithm cohortAlgorithm;

	private PatientAlgorithm patientAlgorithm;

	public CohortMembershipAlgorithm() {
		this.cohortAlgorithm = new CohortAlgorithm();
		this.patientAlgorithm = new PatientAlgorithm();
	}

	public static final String COHORT_MEMBERSHIP_DATA_REPRESENTATION =
			"(cohort:" + CohortAlgorithm.COHORT_STANDARD_REPRESENTATION + "," +
			"patient:" + PatientAlgorithm.PATIENT_STANDARD_REPRESENTATION + ",startDate,endDate,voided,uuid)";

	@Override
	public Searchable deserialize(final String serialized) throws IOException {
		Cohort cohort;
		Patient patient;
		Object cohortObject = JsonPath.read(serialized, "$['cohort']");
		cohort = (Cohort) cohortAlgorithm.deserialize(String.valueOf(cohortObject));

		Object patientObject = JsonPath.read(serialized, "$['patient']");
		patient = (Patient) patientAlgorithm.deserialize(String.valueOf(patientObject));

		Date startDate = JsonUtils.readAsDateTime(serialized, "$['startDate']");
		Date endDate = JsonUtils.readAsDateTime(serialized, "$['endDate']");
		boolean voided = JsonUtils.readAsBoolean(serialized, "$['voided']");
		String uuid = JsonUtils.readAsString(serialized, "$['uuid']");

		CohortMembership membership = new CohortMembership(patient, startDate);
		membership.setCohort(cohort);
		if (endDate != null) {
			membership.setEndDate(endDate);
		}
		membership.setVoided(voided);
		membership.setUuid(uuid);
		return membership;
	}

	@Override
	public String serialize(Searchable searchable) throws IOException {
        CohortMembership membership = (CohortMembership) searchable;
        JSONObject jsonObject = new JSONObject();
        String cohort = cohortAlgorithm.serialize(membership.getCohort());
        jsonObject.put("cohort", JsonPath.read(cohort, "$"));
        String patient = patientAlgorithm.serialize(membership.getPatient());
        jsonObject.put("patient", JsonPath.read(patient, "$"));
        JsonUtils.writeAsDateTime(jsonObject, "startDate", membership.getStartDate());
        JsonUtils.writeAsDateTime(jsonObject, "endDate", membership.getEndDate());
        JsonUtils.writeAsBoolean(jsonObject, "voided", membership.getVoided());
        JsonUtils.writeAsString(jsonObject, "uuid", membership.getUuid());
        return jsonObject.toJSONString();
	}
}
