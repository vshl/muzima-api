/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.service;

import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.Credential;
import com.muzima.search.api.util.DigestUtil;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * TODO: Write brief description about the class here.
 */
public class UserServiceTest {

    @Test
    public void savingCredential() throws Exception {

        Context context = ContextFactory.createContext();
        UserService userService = context.getUserService();

        List<Credential> credentialList = userService.getAllCredentials();
        for (Credential credential : credentialList) {
            userService.deleteCredential(credential);
        }

        String username = "admin";
        String password = "test";

        Credential credential = userService.getCredentialByUsername(username);
        if (credential != null)
            userService.deleteCredential(credential);

        credential = userService.getCredentialByUsername(username);
        assertThat(credential, nullValue());

        String uuid = UUID.randomUUID().toString();
        String salt = DigestUtil.getSHA1Checksum(uuid);
        String hashedPassword = DigestUtil.getSHA1Checksum(salt + ":" + password);

        credential = new Credential();
        credential.setUuid(uuid);
        credential.setSalt(salt);
        credential.setPassword(hashedPassword);
        credential.setUsername(username);
        credential.setUserUuid(UUID.randomUUID().toString());
        userService.saveCredential(credential);

        Credential savedCredential = userService.getCredentialByUsername("admin");
        assertThat(savedCredential, notNullValue());
        assertThat(salt, equalTo(credential.getSalt()));
        assertThat(username, equalTo(credential.getUsername()));
        assertThat(hashedPassword, equalTo(credential.getPassword()));
    }
}
