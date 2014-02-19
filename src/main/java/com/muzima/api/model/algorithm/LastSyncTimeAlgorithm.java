package com.muzima.api.model.algorithm;

import com.muzima.api.model.LastSyncTime;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;

import static com.muzima.api.model.APIName.getAPIName;

public class LastSyncTimeAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String STANDARD_LAST_SYNC_TIME_REPRESENTATION = "(uuid,apiName,paramSignature,lastSyncDate)";

    @Override
    public Searchable deserialize(String serialized) throws IOException {
        LastSyncTime lastSyncTime = new LastSyncTime();
        lastSyncTime.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        lastSyncTime.setApiName(getAPIName(JsonUtils.readAsString(serialized, "$['apiName']")));
        lastSyncTime.setParamSignature(JsonUtils.readAsString(serialized, "$['paramSignature']"));
        lastSyncTime.setLastSyncDate(JsonUtils.readAsDateTime(serialized, "$['lastSyncDate']"));
        return lastSyncTime;
    }

    @Override
    public String serialize(Searchable object) throws IOException {
        LastSyncTime lastSyncTime = (LastSyncTime) object;
        JSONObject jsonObject = new JSONObject();
        JsonUtils.writeAsString(jsonObject, "uuid", lastSyncTime.getUuid());
        JsonUtils.writeAsString(jsonObject, "apiName", lastSyncTime.getApiName().toString());
        JsonUtils.writeAsString(jsonObject, "paramSignature", lastSyncTime.getParamSignature());
        JsonUtils.writeAsDateTime(jsonObject, "lastSyncDate", lastSyncTime.getLastSyncDate());
        return jsonObject.toString();
    }
}
