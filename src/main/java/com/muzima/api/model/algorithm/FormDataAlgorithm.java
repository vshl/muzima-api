/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.FormData;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.model.serialization.Algorithm;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class FormDataAlgorithm implements Algorithm {

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param json the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String json) throws IOException {
        FormData formData = new FormData();

        Object jsonObject = JsonPath.read(json, "$");

        String uuid = JsonPath.read(jsonObject, "$['uuid']");
        formData.setUuid(uuid);

        String status = JsonPath.read(jsonObject, "$['status']");
        formData.setStatus(status);

        String discriminator = JsonPath.read(jsonObject, "$['discriminator']");
        formData.setDiscriminator(discriminator);

        String jsonPayload = JsonPath.read(jsonObject, "$['jsonPayload']");
        formData.setJsonPayload(jsonPayload);

        String xmlPayload = JsonPath.read(jsonObject, "$['xmlPayload']");
        formData.setXmlPayload(xmlPayload);

        String templateUuid = JsonPath.read(jsonObject, "$['template.uuid']");
        formData.setTemplateUuid(templateUuid);

        String patientUuid = JsonPath.read(jsonObject, "$['patient.uuid']");
        formData.setPatientUuid(patientUuid);

        String userUuid = JsonPath.read(jsonObject, "$['user.uuid']");
        formData.setUserUuid(userUuid);

        Date saveTime = JsonUtils.readAsDateTime(json, "$['formSaveTime']");
        formData.setSaveTime(saveTime);

        Date encounterDate = JsonUtils.readAsDate(json, "$['encounterDate']");
        formData.setEncounterDate(encounterDate);

        return formData;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        FormData formData = (FormData) object;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", formData.getUuid());
        jsonObject.put("status", formData.getStatus());
        jsonObject.put("discriminator", formData.getDiscriminator());
        jsonObject.put("xmlPayload", formData.getXmlPayload());
        jsonObject.put("jsonPayload", formData.getJsonPayload());
        jsonObject.put("template.uuid", formData.getTemplateUuid());
        jsonObject.put("patient.uuid", formData.getPatientUuid());
        jsonObject.put("user.uuid", formData.getUserUuid());
        JsonUtils.writeAsDateTime(jsonObject, "formSaveTime", formData.getSaveTime());
        JsonUtils.writeAsDateTime(jsonObject,"encounterDate",formData.getEncounterDate());
        return jsonObject.toJSONString();
    }
}