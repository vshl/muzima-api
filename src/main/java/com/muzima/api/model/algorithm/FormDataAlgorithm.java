/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.FormData;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.search.api.model.serialization.Algorithm;
import net.minidev.json.JSONObject;

import java.io.IOException;

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

        String payload = JsonPath.read(jsonObject, "$['payload']");
        formData.setJsonPayload(payload);

        String templateUuid = JsonPath.read(jsonObject, "$['template.uuid']");
        formData.setTemplateUuid(templateUuid);

        String patientUuid = JsonPath.read(jsonObject, "$['patient.uuid']");
        formData.setPatientUuid(patientUuid);

        String userUuid = JsonPath.read(jsonObject, "$['user.uuid']");
        formData.setUserUuid(userUuid);

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
        jsonObject.put("payload", formData.getXmlPayload());
        jsonObject.put("template.uuid", formData.getTemplateUuid());
        jsonObject.put("patient.uuid", formData.getPatientUuid());
        jsonObject.put("user.uuid", formData.getUserUuid());
        return jsonObject.toJSONString();
    }
}
