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
package com.muzima.api.service.impl;

import com.google.inject.Inject;
import com.muzima.api.dao.CredentialDao;
import com.muzima.api.dao.PrivilegeDao;
import com.muzima.api.dao.RoleDao;
import com.muzima.api.dao.UserDao;
import com.muzima.api.model.Credential;
import com.muzima.api.model.Privilege;
import com.muzima.api.model.Role;
import com.muzima.api.model.User;
import com.muzima.api.service.UserService;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Inject
    private CredentialDao credentialDao;

    @Inject
    private PrivilegeDao privilegeDao;

    @Inject
    private RoleDao roleDao;

    protected UserServiceImpl() {
    }

    /**
     * Download a single user record from the user rest resource into the local lucene repository.
     *
     * @param uuid the uuid of the user.
     * @throws IOException when search api unable to process the resource.
     * @should download user with matching uuid.
     */
    @Override
    public User downloadUserByUuid(final String uuid) throws IOException {
        List<User> users = userDao.download(uuid, Constants.UUID_USER_RESOURCE);
        if (users.size() > 1) {
            throw new IOException("Unable to uniquely identify a form record.");
        } else if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    /**
     * Download a single user record from the user rest resource into the local lucene repository.
     *
     * @param username the username of the user.
     * @throws IOException when search api unable to process the resource.
     * @should download user with matching uuid.
     */
    @Override
    public User downloadUserByUsername(final String username) throws IOException {
        List<User> users = userDao.download(username, Constants.SEARCH_USER_RESOURCE);
        if (users.size() > 1) {
            throw new IOException("Unable to uniquely identify a form record.");
        } else if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    /**
     * Download all users with name similar to the partial name passed in the parameter.
     *
     * @param name the partial name of the user to be downloaded. When empty, will return all users available.
     * @throws IOException when search api unable to process the resource.
     * @should download all user with partially matched name.
     * @should download all user when name is empty.
     */
    @Override
    public List<User> downloadUsersByName(final String name) throws IOException {
        return userDao.download(name, Constants.SEARCH_USER_RESOURCE);
    }

    /**
     * Save user to the local lucene repository.
     *
     * @param user the user to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void saveUser(final User user) throws IOException {
        userDao.save(user, Constants.UUID_USER_RESOURCE);
    }

    /**
     * Update user in the local lucene repository.
     *
     * @param user the user to be updated.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void updateUser(final User user) throws IOException {
        userDao.update(user, Constants.UUID_USER_RESOURCE);
    }

    /**
     * Get a single user using the user's uuid.
     *
     * @param uuid the user uuid.
     * @return user with matching uuid or null when no user match the uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return user with matching uuid.
     * @should return null when no user match the uuid.
     */
    @Override
    public User getUserByUuid(final String uuid) throws IOException {
        return userDao.getByUuid(uuid);
    }

    /**
     * Get user using the user's name.
     *
     * @param name the name of the user.
     * @return user with matching name or null when no user match the name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return user with matching username.
     * @should return null when no user match the username.
     */
    @Override
    public List<User> getUserByName(final String name) throws IOException, ParseException {
        return userDao.getByName(name);
    }

    /**
     * Get a single user using the user name.
     *
     * @param username the user username.
     * @return user with matching username or null when no user match the username.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return user with matching username.
     * @should return null when no user match the username.
     */
    @Override
    public User getUserByUsername(final String username) throws IOException, ParseException {
        return userDao.getByUsername(username);
    }

    /**
     * Get all saved users in the local repository.
     *
     * @return all registered users or empty list when no user is registered.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return all registered users.
     * @should return empty list when no user is registered.
     */
    @Override
    public List<User> getAllUsers() throws IOException, ParseException {
        return userDao.getAll();
    }

    /**
     * Delete a user record from the local repository.
     *
     * @param user the user to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete the user record from the local repository.
     */
    @Override
    public void deleteUser(final User user) throws IOException {
        userDao.delete(user, Constants.UUID_USER_RESOURCE);
    }

    /**
     * Save a new credential record in the local repository.
     *
     * @param credential the new credential to be saved.
     * @throws IOException when search api unable to process the resource.
     * @should save the new credential record.
     */
    @Override
    public void saveCredential(final Credential credential) throws IOException {
        credentialDao.save(credential, Constants.LOCAL_CREDENTIAL_RESOURCE);
    }

    /**
     * Update a credential record in the local repository.
     *
     * @param credential the credential record to be updated.
     * @throws IOException when search api unable to process the resource.
     * @should update the credential record.
     */
    @Override
    public void updateCredential(final Credential credential) throws IOException {
        credentialDao.update(credential, Constants.LOCAL_CREDENTIAL_RESOURCE);
    }

    /**
     * Get a credential record using the uuid of the record.
     *
     * @param uuid the uuid of the record.
     * @return the credential with matching uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return credential with matching uuid.
     */
    @Override
    public Credential getCredentialByUuid(final String uuid) throws IOException {
        return credentialDao.getByUuid(uuid);
    }

    /**
     * Get a credential record for a username.
     *
     * @param username the username.
     * @return the credential record for the username.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return credential for the username.
     */
    @Override
    public Credential getCredentialByUsername(final String username) throws IOException, ParseException {
        return credentialDao.getByUsername(username);
    }

    /**
     * Get all credential records.
     *
     * @return all credential records from the local repository.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return all saved credential records from local repository.
     */
    @Override
    public List<Credential> getAllCredentials() throws IOException, ParseException {
        return credentialDao.getAll();
    }

    /**
     * Delete a credential record from the local repository.
     *
     * @param credential the credential record to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete credential from local repository.
     */
    @Override
    public void deleteCredential(final Credential credential) throws IOException {
        credentialDao.delete(credential, Constants.LOCAL_CREDENTIAL_RESOURCE);
    }

    /**
     * Download privilege record using the privilege uuid.
     *
     * @param uuid the uuid for the privilege.
     * @throws IOException when search api unable to process the resource.
     * @should download privilege with matching uuid.
     */
    @Override
    public Privilege downloadPrivilege(final String uuid) throws IOException {
        List<Privilege> privileges = privilegeDao.download(uuid, Constants.UUID_PRIVILEGE_RESOURCE);
        if (privileges.size() > 1) {
            throw new IOException("Unable to uniquely identify a form record.");
        } else if (privileges.size() == 0) {
            return null;
        }
        return privileges.get(0);
    }

    /**
     * Download all privilege records matching the privilege name.
     *
     * @param name the partial name of the privileges.
     * @throws IOException when search api unable to process the resource.
     * @should download all privileges with matching name.
     */
    @Override
    public List<Privilege> downloadPrivileges(final String name) throws IOException {
        return privilegeDao.download(name, Constants.SEARCH_PRIVILEGE_RESOURCE);
    }

    /**
     * Save privilege object to the local lucene repository.
     *
     * @param privilege the privilege object to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void savePrivilege(final Privilege privilege) throws IOException {
        privilegeDao.save(privilege, Constants.UUID_PRIVILEGE_RESOURCE);
    }

    /**
     * Update privilege object to the local lucene repository.
     *
     * @param privilege the privilege object to be updated.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void updatePrivilege(final Privilege privilege) throws IOException {
        privilegeDao.update(privilege, Constants.UUID_PRIVILEGE_RESOURCE);
    }

    /**
     * Get privilege from local repository using the privilege uuid.
     *
     * @param uuid the uuid of the privilege.
     * @return the privilege with matching uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return privilege with matching uuid.
     */
    @Override
    public Privilege getPrivilegeByUuid(final String uuid) throws IOException {
        return privilegeDao.getByUuid(uuid);
    }

    /**
     * Get privilege records from local repository using the privilege name.
     *
     * @param name the partial name of the privileges.
     * @return all privileges with matching name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return all privileges with matching name.
     * @should return empty list when no privilege record match the name.
     */
    @Override
    public List<Privilege> getPrivilegesByName(final String name) throws IOException, ParseException {
        return privilegeDao.getByName(name);
    }

    /**
     * Delete privilege from the local repository.
     *
     * @param privilege the privilege to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete privilege from the local repository.
     */
    @Override
    public void deletePrivilege(final Privilege privilege) throws IOException {
        privilegeDao.delete(privilege, Constants.UUID_PRIVILEGE_RESOURCE);
    }

    /**
     * Download role with matching uuid.
     *
     * @param uuid the uuid of the role.
     * @throws IOException when search api unable to process the resource.
     * @should download role with matching uuid.
     */
    @Override
    public Role downloadRole(final String uuid) throws IOException {
        List<Role> roles = roleDao.download(uuid, Constants.UUID_ROLE_RESOURCE);
        if (roles.size() > 1) {
            throw new IOException("Unable to uniquely identify a form record.");
        } else if (roles.size() == 0) {
            return null;
        }
        return roles.get(0);
    }

    /**
     * Download role with matching name.
     *
     * @param name the name of the role.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should download roles with matching name.
     */
    @Override
    public List<Role> downloadRoles(final String name) throws IOException, ParseException {
        return roleDao.download(name, Constants.SEARCH_ROLE_RESOURCE);
    }

    /**
     * Save the role object to the local lucene repository.
     *
     * @param role the role object to be saved.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void saveRole(final Role role) throws IOException {
        roleDao.save(role, Constants.UUID_ROLE_RESOURCE);
    }

    /**
     * Update the role object in the local lucene repository.
     *
     * @param role the role to be updated.
     * @throws java.io.IOException when search api unable to process the resource.
     */
    @Override
    public void updateRole(final Role role) throws IOException {
        roleDao.update(role, Constants.UUID_ROLE_RESOURCE);
    }

    /**
     * Get role from local repository with matching uuid.
     *
     * @param uuid the uuid of the role.
     * @return the role with matching uuid.
     * @throws IOException when search api unable to process the resource.
     * @should return role with matching uuid.
     */
    @Override
    public Role getRoleByUuid(final String uuid) throws IOException {
        return roleDao.getByUuid(uuid);
    }

    /**
     * Get role records from local repository with matching name.
     *
     * @param name the partial name of the role.
     * @return all roles with matching name.
     * @throws ParseException when query parser from lucene unable to parse the query string.
     * @throws IOException    when search api unable to process the resource.
     * @should return role records with matching name.
     * @should return empty list when no record matching the name.
     */
    @Override
    public List<Role> getRolesByName(final String name) throws IOException, ParseException {
        return roleDao.getByName(name);
    }

    /**
     * Delete role record from the local repository.
     *
     * @param role the role record to be deleted.
     * @throws IOException when search api unable to process the resource.
     * @should delete role record from local repository.
     */
    @Override
    public void deleteRole(final Role role) throws IOException {
        roleDao.delete(role, Constants.UUID_ROLE_RESOURCE);
    }
}
