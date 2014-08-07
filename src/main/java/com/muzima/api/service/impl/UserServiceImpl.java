/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
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
import com.muzima.search.api.util.CollectionUtil;
import com.muzima.util.Constants;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * {@inheritDoc}
     *
     * @see UserService#downloadUserByUuid(String)
     */
    @Override
    public User downloadUserByUuid(final String uuid) throws IOException {
        User user = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<User> users = userDao.download(parameter, Constants.UUID_USER_RESOURCE);
        if (!CollectionUtil.isEmpty(users)) {
            if (users.size() > 1) {
                throw new IOException("Unable to uniquely identify a form record.");
            }
            user = users.get(0);
        }
        return user;
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#downloadUserByUsername(String)
     */
    @Override
    public User downloadUserByUsername(final String username) throws IOException {
        User user = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("username", username);
        }};
        List<User> users = userDao.download(parameter, Constants.SEARCH_USER_RESOURCE);
        if (!CollectionUtil.isEmpty(users)) {
            if (users.size() > 1) {
                throw new IOException("Unable to uniquely identify a form record.");
            }
            user = users.get(0);
        }
        return user;
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#downloadUsersByName(String)
     */
    @Override
    public List<User> downloadUsersByName(final String name) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        return userDao.download(parameter, Constants.SEARCH_USER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#saveUser(com.muzima.api.model.User)
     */
    @Override
    public void saveUser(final User user) throws IOException {
        userDao.save(user, Constants.UUID_USER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#updateUser(com.muzima.api.model.User)
     */
    @Override
    public void updateUser(final User user) throws IOException {
        userDao.update(user, Constants.UUID_USER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getUserByUuid(String)
     */
    @Override
    public User getUserByUuid(final String uuid) throws IOException {
        return userDao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getUserByName(String)
     */
    @Override
    public List<User> getUserByName(final String name) throws IOException {
        return userDao.getByName(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getUserByUsername(String)
     */
    @Override
    public User getUserByUsername(final String username) throws IOException, ParseException {
        return userDao.getByUsername(username);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.UserService#getAllUsers()
     */
    @Override
    public List<User> getAllUsers() throws IOException {
        return userDao.getAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#deleteUser(com.muzima.api.model.User)
     */
    @Override
    public void deleteUser(final User user) throws IOException {
        userDao.delete(user, Constants.UUID_USER_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#saveCredential(com.muzima.api.model.Credential)
     */
    @Override
    public void saveCredential(final Credential credential) throws IOException {
        credentialDao.save(credential, Constants.LOCAL_CREDENTIAL_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#updateCredential(com.muzima.api.model.Credential)
     */
    @Override
    public void updateCredential(final Credential credential) throws IOException {
        credentialDao.update(credential, Constants.LOCAL_CREDENTIAL_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getCredentialByUuid(String)
     */
    @Override
    public Credential getCredentialByUuid(final String uuid) throws IOException {
        return credentialDao.getCredentialByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getCredentialByUsername(String)
     */
    @Override
    public Credential getCredentialByUsername(final String username) throws IOException {
        return credentialDao.getByUsername(username);
    }

    /**
     * {@inheritDoc}
     *
     * @see com.muzima.api.service.UserService#getAllCredentials()
     */
    @Override
    public List<Credential> getAllCredentials() throws IOException {
        return credentialDao.getAll();
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#deleteCredential(com.muzima.api.model.Credential)
     */
    @Override
    public void deleteCredential(final Credential credential) throws IOException {
        credentialDao.delete(credential, Constants.LOCAL_CREDENTIAL_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#downloadPrivilege(String)
     */
    @Override
    public Privilege downloadPrivilege(final String uuid) throws IOException {
        Privilege privilege = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<Privilege> privileges = privilegeDao.download(parameter, Constants.UUID_PRIVILEGE_RESOURCE);
        if (!CollectionUtil.isEmpty(privileges)) {
            if (privileges.size() > 1) {
                throw new IOException("Unable to uniquely identify a form record.");
            }
            privilege = privileges.get(0);
        }
        return privilege;
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#downloadPrivileges(String)
     */
    @Override
    public List<Privilege> downloadPrivileges(final String name) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        return privilegeDao.download(parameter, Constants.SEARCH_PRIVILEGE_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#savePrivilege(com.muzima.api.model.Privilege)
     */
    @Override
    public void savePrivilege(final Privilege privilege) throws IOException {
        privilegeDao.save(privilege, Constants.UUID_PRIVILEGE_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#updatePrivilege(com.muzima.api.model.Privilege)
     */
    @Override
    public void updatePrivilege(final Privilege privilege) throws IOException {
        privilegeDao.update(privilege, Constants.UUID_PRIVILEGE_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getPrivilegeByUuid(String)
     */
    @Override
    public Privilege getPrivilegeByUuid(final String uuid) throws IOException {
        return privilegeDao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getPrivilegesByName(String)
     */
    @Override
    public List<Privilege> getPrivilegesByName(final String name) throws IOException {
        return privilegeDao.getByName(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#deletePrivilege(com.muzima.api.model.Privilege)
     */
    @Override
    public void deletePrivilege(final Privilege privilege) throws IOException {
        privilegeDao.delete(privilege, Constants.UUID_PRIVILEGE_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#downloadRole(String)
     */
    @Override
    public Role downloadRole(final String uuid) throws IOException {
        Role role = null;
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("uuid", uuid);
        }};
        List<Role> roles = roleDao.download(parameter, Constants.UUID_ROLE_RESOURCE);
        if (CollectionUtil.isEmpty(roles)) {
            if (roles.size() > 1) {
                throw new IOException("Unable to uniquely identify a form record.");
            }
            role = roles.get(0);
        }
        return role;
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#downloadRoles(String)
     */
    @Override
    public List<Role> downloadRoles(final String name) throws IOException {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put("q", name);
        }};
        return roleDao.download(parameter, Constants.SEARCH_ROLE_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#saveRole(com.muzima.api.model.Role)
     */
    @Override
    public void saveRole(final Role role) throws IOException {
        roleDao.save(role, Constants.UUID_ROLE_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#updateRole(com.muzima.api.model.Role)
     */
    @Override
    public void updateRole(final Role role) throws IOException {
        roleDao.update(role, Constants.UUID_ROLE_RESOURCE);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getRoleByUuid(String)
     */
    @Override
    public Role getRoleByUuid(final String uuid) throws IOException {
        return roleDao.getByUuid(uuid);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getRolesByName(String)
     */
    @Override
    public List<Role> getRolesByName(final String name) throws IOException {
        return roleDao.getByName(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#deleteRole(com.muzima.api.model.Role)
     */
    @Override
    public void deleteRole(final Role role) throws IOException {
        roleDao.delete(role, Constants.UUID_ROLE_RESOURCE);
    }
}
