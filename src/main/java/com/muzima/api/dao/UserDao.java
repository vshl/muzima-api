/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.dao;

import com.google.inject.ImplementedBy;
import com.muzima.api.dao.impl.UserDaoImpl;
import com.muzima.api.model.User;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;
import java.util.List;

@ImplementedBy(UserDaoImpl.class)
public interface UserDao extends OpenmrsDao<User> {

    /**
     * Get a user record by the user name of the user.
     *
     * @param username the username
     * @return user with matching username.
     * @throws IOException when search api unable to process the resource.
     */
    User getByUsername(final String username) throws ParseException, IOException;

    /**
     * Get user by the name of the user. Passing empty string will returns all registered users.
     *
     * @param name the partial name of the user or empty string.
     * @return the list of all matching user on the user name.
     * @throws IOException when search api unable to process the resource.
     */
    List<User> getUserByName(final String name) throws ParseException, IOException;
}
