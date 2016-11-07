package com.muzima.api.model.algorithm;

import com.muzima.api.model.SetupConfigurationTemplate;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class SetupConfigurationTemplateAlgorithm  extends BaseOpenmrsAlgorithm {

    public static final String SETUP_CONFIGURATION_TEMPLATE_STANDARD_REPRESENTATION = "(uuid,configJson)";

    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        SetupConfigurationTemplate setupConfigurationTemplate = new SetupConfigurationTemplate();
        setupConfigurationTemplate.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        setupConfigurationTemplate.setConfigJson(JsonUtils.readAsString(serialized, "$['configJson']"));
        return setupConfigurationTemplate;
    }

    @Override
    public String serialize(final Searchable object) throws IOException {
        SetupConfigurationTemplate setupConfigurationTemplate = (SetupConfigurationTemplate)object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", setupConfigurationTemplate.getUuid());
        JsonUtils.writeAsString(jsonObject, "configJson", setupConfigurationTemplate.getConfigJson());
        return jsonObject.toJSONString();
    }
}
