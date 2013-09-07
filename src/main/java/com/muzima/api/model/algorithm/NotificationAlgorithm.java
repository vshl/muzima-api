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
import com.muzima.api.model.Person;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonPathUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;


public class NotificationAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String NOTIFICATION_STANDAR_REPRESENTATION =
            "(uuid,subject,payload," +
                    "sender:" + PersonAlgorithm.PERSON_STANDARD_REPRESENTATION + "," +
                    "receiver:" + PersonAlgorithm.PERSON_STANDARD_REPRESENTATION + ")";

    private PersonAlgorithm personAlgorithm;

    public NotificationAlgorithm() {
        this.personAlgorithm = new PersonAlgorithm();
    }

    /**
     * Implementation of this method will define how the observation will be serialized from the JSON representation.
     *
     * @param json the json representation
     * @return the concrete observation object
     */
    @Override
    public Notification deserialize(final String json) throws IOException {
        Notification notification = new Notification();
        Object jsonObject = JsonPath.read(json, "$");
        notification.setUuid(JsonPathUtils.readAsString(jsonObject, "$['uuid']"));
        notification.setSubject(JsonPathUtils.readAsString(jsonObject, "$['subject']"));
        notification.setPayload(JsonPathUtils.readAsString(jsonObject, "$['payload']"));
        Object senderObject = JsonPath.read(jsonObject, "$['sender']");
        notification.setSender((Person) personAlgorithm.deserialize(senderObject.toString()));
        Object receiverObject = JsonPath.read(jsonObject, "$['receiver']");
        notification.setReceiver((Person) personAlgorithm.deserialize(receiverObject.toString()));
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
        jsonObject.put("payload", notification.getPayload());
        String sender = personAlgorithm.serialize(notification.getSender());
        jsonObject.put("sender", JsonPath.read(sender, "$"));
        String receiver = personAlgorithm.serialize(notification.getReceiver());
        jsonObject.put("receiver", JsonPath.read(receiver, "$"));
        return jsonObject.toJSONString();
    }
}
