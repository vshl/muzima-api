/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
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
