package com.muzima.api.model.algorithm;

import com.muzima.api.model.SetupConfiguration;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

public class SetupConfigurationAlgorithm  extends BaseOpenmrsAlgorithm {

    public static final String SETUP_CONFIGURATION_STANDARD_REPRESENTATION = "(uuid,name,description,retired)";

    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        SetupConfiguration configuration = new SetupConfiguration();
        configuration.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        configuration.setName(JsonUtils.readAsString(serialized, "$['name']"));
        configuration.setDescription(JsonUtils.readAsString(serialized, "$['description']"));
        configuration.setRetired(JsonUtils.readAsBoolean(serialized, "$['retired']"));
        return configuration;
    }

    @Override
    public String serialize(final Searchable object) throws IOException {
        SetupConfiguration setupConfiguration = (SetupConfiguration)object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", setupConfiguration.getUuid());
        JsonUtils.writeAsBoolean(jsonObject, "retired", setupConfiguration.isRetired());
        JsonUtils.writeAsString(jsonObject, "name", setupConfiguration.getName());
        JsonUtils.writeAsString(jsonObject, "description", setupConfiguration.getDescription());
        return jsonObject.toJSONString();
    }

}
