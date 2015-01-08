package com.muzima.api.adapter.impl;

import com.muzima.api.adapter.JsonWriterAdapter;
import com.muzima.api.adapter.JsonWriterAdapterFactory;

import java.io.Writer;

/**
 * Created by vikas on 06/01/15.
 */
public class DefaultJsonWriterAdapterFactory implements JsonWriterAdapterFactory {

    @Override
    public JsonWriterAdapter jsonWriterAdapter(Writer writer) {
        return new DefaultJsonWriterAdapter(writer);
    }
}
