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
import com.muzima.api.model.FormTemplate;
import com.muzima.search.api.model.object.Searchable;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class FormTemplateAlgorithm extends BaseOpenmrsAlgorithm {

    /**
     * Implementation of this method will define how the object will be serialized from the String representation.
     *
     * @param json the string representation
     * @return the concrete object
     */
    @Override
    public Searchable deserialize(final String json) throws IOException {
        FormTemplate formTemplate = new FormTemplate();

        Object jsonObject = JsonPath.read(json, "$");

        String uuid = JsonPath.read(jsonObject, "$['uuid']");
        formTemplate.setUuid(uuid);

        String model = JsonPath.read(jsonObject, "$['model']");
        formTemplate.setModel(model);

        String modelJson = JsonPath.read(jsonObject, "$['modelJson']");
        formTemplate.setModelJson(modelJson);

        String html = JsonPath.read(jsonObject, "$['html']");
        formTemplate.setHtml(html);

        return formTemplate;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        FormTemplate formTemplate = (FormTemplate) object;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", formTemplate.getUuid());
        jsonObject.put("model", formTemplate.getModel());
        jsonObject.put("modelJson", formTemplate.getModelJson());
        jsonObject.put("html", formTemplate.getHtml());
        return jsonObject.toJSONString();
    }
}
