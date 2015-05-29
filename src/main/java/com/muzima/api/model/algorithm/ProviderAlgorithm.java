package com.muzima.api.model.algorithm;

import com.muzima.api.model.Provider;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;


public class ProviderAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String PROVIDER_STANDARD_REPRESENTATION = "(uuid,name,id,identifier)";

    @Override
    public Searchable deserialize(final String serialized) throws IOException {
        Provider provider = new Provider();
        provider.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        provider.setName(JsonUtils.readAsString(serialized, "$['name']"));
        provider.setId(JsonUtils.readAsInteger(serialized, "$['id']"));
        provider.setSystemId(JsonUtils.readAsString(serialized, "$['identifier']"));
        return provider;
    }

    @Override
    public String serialize(final Searchable object) throws IOException {
        Provider provider = (Provider) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", provider.getUuid());
        JsonUtils.writeAsString(jsonObject, "name", provider.getName());
        JsonUtils.writeAsInteger(jsonObject, "id", provider.getId());
        JsonUtils.writeAsString(jsonObject, "identifier", provider.getSystemId());
        return jsonObject.toString();
    }
}
