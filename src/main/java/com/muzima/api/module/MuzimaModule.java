/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.muzima.api.config.Configuration;
import com.muzima.util.Constants;

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
        pathBuilder.append(System.getProperty("java.io.tmpdir"));
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
