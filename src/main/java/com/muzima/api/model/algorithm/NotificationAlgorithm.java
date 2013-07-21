/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Notification;
import com.muzima.search.api.model.object.Searchable;
import net.minidev.json.JSONObject;

import java.io.IOException;


public class NotificationAlgorithm extends BaseOpenmrsAlgorithm {

     /**
     * Implementation of this method will define how the observation will be serialized from the JSON representation.
     *
     *
      * @param json the json representation
      * @return the concrete observation object
     */
    @Override
    public Notification deserialize(final String json) throws IOException {

        Notification notification = new Notification();

        // get the full json object representation and then pass this around to the next JsonPath.read()
        // this should minimize the time for the subsequent read() call
        Object jsonObject = JsonPath.read(json, "$");

        String uuid = JsonPath.read(jsonObject, "$['uuid']");
        notification.setUuid(uuid);

        String subject = JsonPath.read(jsonObject, "$['subject']");
        notification.setSubject(subject);

        String receiver = JsonPath.read(jsonObject, "$['receiver']");
        notification.setReceiver(receiver);

        String sender = JsonPath.read(jsonObject, "$['sender']");
        notification.setSender(sender);

        String payload = JsonPath.read(jsonObject, "$['payload']");
        notification.setPayload(payload);



        return notification;
    }

    /**
     * Implementation of this method will define how the object will be de-serialized into the String representation.
     *
     * @param object the object
     * @return the string representation
     */
    @Override
    public String serialize(final Searchable object) throws IOException {
        Notification notification = (Notification) object;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", notification.getUuid());
        jsonObject.put("subject", notification.getSubject());
        jsonObject.put("receiver", notification.getReceiver());
        jsonObject.put("sender", notification.getSender());
        jsonObject.put("payload", notification.getPayload());

        return jsonObject.toJSONString();
    }
}
