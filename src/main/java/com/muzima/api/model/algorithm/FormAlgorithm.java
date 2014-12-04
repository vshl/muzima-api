/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.muzima.api.model.Form;
import com.muzima.api.model.Tag;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String STANDARD_FORM_REPRESENTATION = "(uuid,name,discriminator,description,tags:(uuid,name),retired)";

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
        form.setRetired(JsonUtils.readAsBoolean(serialized, "$['retired']"));
        form.setDescription(JsonUtils.readAsString(serialized, "$['description']"));
        form.setDiscriminator(JsonUtils.readAsString(serialized, "$['discriminator']"));
        List<Object> objects = JsonUtils.readAsObjectList(serialized, "$['tags']");
        List<Tag> formTags = new ArrayList<Tag>();
        for (Object tagObject : objects) {
            Tag formTag = new Tag();
            String tagString = String.valueOf(tagObject);
            formTag.setName(JsonUtils.readAsString(tagString, "name"));
            formTag.setUuid(JsonUtils.readAsString(tagString, "uuid"));
            formTags.add(formTag);
        }
        form.setTags(formTags.toArray(new Tag[formTags.size()]));
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
        JsonUtils.writeAsBoolean(jsonObject, "voided", form.isRetired());
        JsonUtils.writeAsString(jsonObject, "description", form.getDescription());
        JsonUtils.writeAsString(jsonObject, "discriminator", form.getDiscriminator());
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
