/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.context;

import com.muzima.api.config.Configuration;
import com.muzima.api.exception.AuthenticationException;
import com.muzima.api.model.Credential;
import com.muzima.api.model.User;
import com.muzima.api.service.UserService;
import com.muzima.search.api.util.DigestUtil;
import com.muzima.search.api.util.StringUtil;
import com.muzima.util.Constants;
import com.muzima.util.NetworkUtils;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.net.ConnectException;
import java.util.UUID;

/**
 * TODO: Write brief description about the class here.
 */
class UserContext {

    private User user;

    private Credential credential;

    private Configuration configuration;

    UserContext() {
    }

    /**
     * Authenticate user using the username and password on the url.
     *
     * @param username the username.
     * @param password the password.
     */
    public void authenticate(final String username, final String password,
                             final UserService userService, final boolean isUpdatePasswordRequired)
            throws IOException, ParseException {
        if (isUpdatePasswordRequired) {
            //if the user has forgotten his password and has asked for a reset/change in password on the server
            //then we force online authentication to authenticate against latest password.
            authenticateOnlineAndUpdateCredentialsWithNewPassword(username, password, userService);
        } else {
            // check device's local repo for user, if user is found proceed with offline authentication
            // if user  does not exist in device's local repo, online authentication is the way to go
            user = userService.getUserByUsername(username);
            if (user != null) {
                authenticateOffline(username, password, userService);
            } else {
                authenticateOnline(username, password, userService);
            }
        }

    }

    private void authenticateOnlineAndUpdateCredentialsWithNewPassword(String username, String password, UserService userService) throws IOException, ParseException {

        user = userService.getUserByUsername(username);
        boolean isDeviceOnline = NetworkUtils.isAddressReachable(getConfiguration().getServer(), Constants.CONNECTION_TIMEOUT);

        if (user != null) { //check if user record exists on device
            if (isDeviceOnline) {
                user = userService.downloadUserByUsername(username); //download changed user details and update the user credentials
                if(user!=null){
                    credential = userService.getCredentialByUsername(username);
                    String salt = credential.getSalt();
                    String hashedPassword = DigestUtil.getSHA1Checksum(salt + ":" + password);
                    credential.setPassword(hashedPassword);
                    userService.updateCredential(credential);
                }else{
                    throw new AuthenticationException("Unable to authenticate user for username: " + username);
                }
            } else {
                throw new ConnectException("Unable to connect to the server to authenticate user. Please connect to the internet and try again." + username);
            }
        } else {
            throw new AuthenticationException("Unable to authenticate user for username: " + username);
        }
    }

    /**
     * Authenticate user using the username and password on the url.
     *
     * @param username the username.
     * @param password the password.
     */
    public void authenticateOnline(final String username, final String password,
                                   final UserService userService)
            throws IOException, ParseException {
        // Process:
        // * Check if this user and his credential already exist on the device's local repo, if yes proceed with offline authentication
        // * If we are unable to find this user and his credential on then proceed with online authentication
        // * Download the user from the server by the username first.
        // * If we get a user, we write the current user credential object. The context is now authenticated.
        boolean isDeviceOnline = NetworkUtils.isAddressReachable(getConfiguration().getServer(), Constants.CONNECTION_TIMEOUT);
        if (user != null && StringUtil.equals(user.getUsername(),username)){
            authenticateOffline(username,password,userService);
        } else if(isDeviceOnline) {
            user = userService.downloadUserByUsername(username);
            if (user != null) {
                userService.saveUser(user);

                String uuid = UUID.randomUUID().toString();
                String salt = DigestUtil.getSHA1Checksum(uuid);
                String hashedPassword = DigestUtil.getSHA1Checksum(salt + ":" + password);

                credential = new Credential();
                credential.setUuid(uuid);
                credential.setSalt(salt);
                credential.setUserUuid(uuid);
                credential.setUsername(username);
                credential.setPassword(hashedPassword);
                userService.saveCredential(credential);
            } else {
                throw new AuthenticationException("Unable to authenticate user for username: " + username);
            }
        } else {
            throw new ConnectException("Unable to connect to the server to authenticate user. Server cannot be reached." + username);
        }
    }

    /**
     * Authenticate user using the username and password on the url.
     *
     * @param username the username.
     * @param password the password.
     */
    public void authenticateOffline(final String username, final String password,
                                    final UserService userService)
            throws IOException, ParseException {
        // TODO: Need to update this authentication method.
        // Process:
        // * Get the credentials for this user from the device's local repo
        // * If we found a match, get the user with the username. The context is now authenticated.
        credential = userService.getCredentialByUsername(username);
        String salt = credential.getSalt();
        String hashedPassword = DigestUtil.getSHA1Checksum(salt + ":" + password);
        if (!StringUtil.equals(hashedPassword, credential.getPassword())) {
            throw new IOException("Unable to authenticate user for username: " + username);
        }
    }


    /**
     * Get currently authenticated user.
     *
     * @return active user who has been authenticated.
     */
    public User getAuthenticatedUser() {
        return user;
    }

    /**
     * Get whether this user context have been authenticated or not.
     *
     * @return true if user has been authenticated in this UserContext
     */
    public boolean isAuthenticated() {
        return user != null;
    }

    /**
     * Logs out the "active" (authenticated) user within this UserContext
     *
     * @see #authenticate
     */
    public void deauthenticate() {
        user = null;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(final Configuration configuration) {
        this.configuration = configuration;
    }
}
