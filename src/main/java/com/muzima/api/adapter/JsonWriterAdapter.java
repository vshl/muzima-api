package com.muzima.api.adapter;


import org.json.JSONException;

/**
 * Created by vikas on 06/01/15.
 */
public interface JsonWriterAdapter {
    JsonWriterAdapter array() throws JSONException;
    JsonWriterAdapter object() throws JSONException;
    JsonWriterAdapter key(String key) throws JSONException;
    JsonWriterAdapter value(String value) throws JSONException;
    JsonWriterAdapter endObject() throws JSONException;
    JsonWriterAdapter endArray() throws JSONException;
}
