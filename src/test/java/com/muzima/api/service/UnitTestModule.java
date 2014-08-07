/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */


package com.muzima.api.service;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.net.URL;

/**
 * TODO: Write brief description about the class here.
 */
public class UnitTestModule extends AbstractModule {

    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        // custom data store bindings for self signed certificates
        bind(String.class)
                .annotatedWith(Names.named("key.store.type"))
                .toInstance("PKCS12");
        bind(String.class)
                .annotatedWith(Names.named("key.store.password"))
                .toInstance("amrstestserver");
        URL resourceUrl = UnitTestModule.class.getResource("keystore/keystore.pks");
        bind(String.class)
                .annotatedWith(Names.named("key.store.path"))
                .toInstance(resourceUrl.getPath());
    }
}
