/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.model.algorithm;

import com.jayway.jsonpath.JsonPath;
import com.muzima.api.model.Notification;
import com.muzima.api.model.Person;
import com.muzima.search.api.model.object.Searchable;
import com.muzima.util.JsonUtils;
import net.minidev.json.JSONObject;

import java.io.IOException;


public class NotificationAlgorithm extends BaseOpenmrsAlgorithm {

    public static final String NOTIFICATION_STANDARD_REPRESENTATION =
            "(uuid,subject,dateCreated,source,status,payload," +
                    "sender:" + PersonAlgorithm.PERSON_STANDARD_REPRESENTATION + "," +
                    "receiver:" + PersonAlgorithm.PERSON_STANDARD_REPRESENTATION + ")";

    private PersonAlgorithm personAlgorithm;

    public NotificationAlgorithm() {
        this.personAlgorithm = new PersonAlgorithm();
    }

    /**
     * Implementation of this method will define how the observation will be serialized from the JSON representation.
     *
     * @param serialized the json representation
     * @return the concrete observation object
     */
    @Override
    public Notification deserialize(final String serialized) throws IOException {
        Notification notification = new Notification();
        notification.setUuid(JsonUtils.readAsString(serialized, "$['uuid']"));
        notification.setSubject(JsonUtils.readAsString(serialized, "$['subject']"));
        notification.setDateCreated(JsonUtils.readAsDate(serialized, "$['dateCreated']"));
        notification.setSource(JsonUtils.readAsString(serialized, "$['source']"));
        notification.setStatus(JsonUtils.readAsString(serialized, "$['status']"));
        notification.setPayload(JsonUtils.readAsString(serialized, "$['payload']"));

        Object senderObject = JsonUtils.readAsObject(serialized, "$['sender']");
        notification.setSender((Person) personAlgorithm.deserialize(String.valueOf(senderObject)));

        Object receiverObject = JsonUtils.readAsObject(serialized, "$['receiver']");
        notification.setReceiver((Person) personAlgorithm.deserialize(String.valueOf(receiverObject)));
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
        JsonUtils.writeAsString(jsonObject, "uuid", notification.getUuid());
        JsonUtils.writeAsString(jsonObject, "subject", notification.getSubject());
        JsonUtils.writeAsDate(jsonObject, "dateCreated", notification.getDateCreated());
        JsonUtils.writeAsString(jsonObject, "source", notification.getSource());
        JsonUtils.writeAsString(jsonObject, "status", notification.getStatus());
        JsonUtils.writeAsString(jsonObject, "payload", notification.getPayload());

        String sender = personAlgorithm.serialize(notification.getSender());
        jsonObject.put("sender", JsonPath.read(sender, "$"));

        String receiver = personAlgorithm.serialize(notification.getReceiver());
        jsonObject.put("receiver", JsonPath.read(receiver, "$"));
        return jsonObject.toJSONString();
    }
}
