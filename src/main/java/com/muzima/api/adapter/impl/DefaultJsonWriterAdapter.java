package com.muzima.api.adapter.impl;

import com.muzima.api.adapter.JsonWriterAdapter;
import org.json.JSONException;
import org.json.JSONWriter;

import java.io.Writer;

/**
 * Created by vikas on 06/01/15.
 */
public class DefaultJsonWriterAdapter implements JsonWriterAdapter {
    private JSONWriter jsonWriter;

    public DefaultJsonWriterAdapter(Writer writer) {
        this.jsonWriter = new JSONWriter(writer);
    }

    @Override
    public JsonWriterAdapter array() throws JSONException {
        jsonWriter.array();
        return this;
    }

    @Override
    public JsonWriterAdapter object() throws JSONException {
        jsonWriter.object();
        return this;
    }

    @Override
    public JsonWriterAdapter key(String key) throws JSONException {
        jsonWriter.key(key);
        return this;
    }

    @Override
    public JsonWriterAdapter value(String value) throws JSONException {
        jsonWriter.value(value);
        return this;
    }

    @Override
    public JsonWriterAdapter endObject() throws JSONException {
        jsonWriter.endObject();
        return this;
    }

    @Override
    public JsonWriterAdapter endArray() throws JSONException {
        jsonWriter.endArray();
        return this;
    }
}
