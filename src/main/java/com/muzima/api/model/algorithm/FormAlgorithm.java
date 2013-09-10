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
import com.muzima.api.model.Form;
import com.muzima.api.model.Tag;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class FormAlgorithm extends BaseOpenmrsAlgorithm {

    /**
     * Implementation of this method will define how the observation will be serialized from the JSON representation.
     *
     * @param serialized the json representation
     * @return the concrete observation object
     */
    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Form form = new Form();
        form.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        form.setName(JsonUtils.readAsString(serialized, "$['name']"));
        form.setDescription(JsonUtils.readAsString(serialized, "$['description']"));
        JSONArray tagObjects = JsonPath.read(serialized, "$['tags']");
        Tag[] tags = new Tag[tagObjects.size()];
        for (int i = 0; i < tagObjects.size(); i++) {
            Tag tag = new Tag();
            String tagObject = String.valueOf(tagObjects.get(i));
            tag.setName(JsonUtils.readAsString(tagObject, "name"));
            tag.setUuid(JsonUtils.readAsString(tagObject, "uuid"));
            tags[i] = tag;
        }
        form.setTags(tags);
        form.setVersion("1");
        return form;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        // serialize the minimum needed to identify an object for deletion purposes.
        Form form = (Form) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", form.getUuid());
        JsonUtils.writeAsString(jsonObject, "name", form.getName());
        JsonUtils.writeAsString(jsonObject, "description", form.getDescription());
        JsonUtils.writeAsString(jsonObject, "version", form.getVersion());
        jsonObject.put("tags", readTagsToJsonArray(form));
        return jsonObject.toJSONString();
    }

    private JSONArray readTagsToJsonArray(Form form) {
        JSONArray jsonArray = new JSONArray();
        Tag[] tags = form.getTags();
        for (Tag tag : tags) {
            JSONObject jsonObject = new JSONObject();
            JsonUtils.writeAsString(jsonObject, "name", tag.getName());
            JsonUtils.writeAsString(jsonObject, "uuid", tag.getUuid());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
