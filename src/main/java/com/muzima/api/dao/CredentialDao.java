package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.CredentialDaoImpl;
import com.muzima.api.model.Credential;

import java.io.IOException;

/**
 * TODO: Write brief description about the class here.
 */
@ImplementedBy(CredentialDaoImpl.class)
public interface CredentialDao extends SearchableDao<Credential> {

    /**
     * Get a credential record by the username of the user.
     *
     * @param username the username of the user.
     * @return credential with matching username.
     * @throws IOException when search api unable to process the resource.
     */
    Credential getByUsername(final String username) throws IOException;

    Credential getCredentialByUuid(String uuid) throws IOException;
}
