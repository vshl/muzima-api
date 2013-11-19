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
package com.muzima.api.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.muzima.api.config.Configuration;
import com.muzima.util.Constants;

import static com.muzima.api.context.ContextFactory.APP_DIR;

public class MuzimaModule extends AbstractModule {

    private String documentKey;

    private String repositoryPath;

    private boolean useEncryption;

    private boolean useCompression;

    private String encryptionKey;

    private Configuration configuration;

    public MuzimaModule() {
        // default field of the document to queried on when the query doesn't specify any.
        this.documentKey = "uuid";
        // data repository path
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(APP_DIR);
        pathBuilder.append("/muzima");
        this.repositoryPath = pathBuilder.toString();
        // encryption and compression
        this.useEncryption = false;
        this.encryptionKey = "this-is-supposed-to-be-a-secure-key";
        this.useCompression = false;
        // server configuration
        this.configuration = new Configuration();
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(final String documentKey) {
        this.documentKey = documentKey;
    }

    public String getRepositoryPath() {
        return repositoryPath;
    }

    public void setRepositoryPath(final String repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    public boolean isUseEncryption() {
        return useEncryption;
    }

    public void setUseEncryption(final boolean useEncryption) {
        this.useEncryption = useEncryption;
    }

    public boolean isUseCompression() {
        return useCompression;
    }

    public void setUseCompression(final boolean useCompression) {
        this.useCompression = useCompression;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(final String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(Names.named(Constants.LUCENE_DIRECTORY_PATH))
                .toInstance(repositoryPath);
        bind(String.class)
                .annotatedWith(Names.named(Constants.LUCENE_DEFAULT_FIELD))
                .toInstance(documentKey);

        bind(Configuration.class).toInstance(configuration);

        bind(String.class)
                .annotatedWith(Names.named("configuration.lucene.encryption"))
                .toInstance("AES/ECB/PKCS5Padding");
        bind(String.class)
                .annotatedWith(Names.named("configuration.lucene.encryption.key"))
                .toInstance(this.encryptionKey);
        bind(Boolean.class)
                .annotatedWith(Names.named("configuration.lucene.usingEncryption"))
                .toInstance(this.useEncryption);
        bind(Boolean.class)
                .annotatedWith(Names.named("configuration.lucene.usingCompression"))
                .toInstance(this.useCompression);
    }
}
