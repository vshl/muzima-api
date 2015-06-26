package com.muzima.api.adapter;

import java.io.Writer;

/**
 * Created by vikas on 06/01/15.
 */
public interface JsonWriterAdapterFactory {
    JsonWriterAdapter jsonWriterAdapter(Writer writer);
}
