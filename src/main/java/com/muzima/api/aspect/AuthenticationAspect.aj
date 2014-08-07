/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.api.aspect;

import com.muzima.api.annotation.Authorization;
import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.User;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Write brief description about the class here.
 */
public aspect AuthenticationAspect {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationAspect.class.getSimpleName());

    pointcut serviceMethod(Authorization authorization): execution(@Authorization * *(..))
            && @annotation(authorization);

    before(Authorization authorization): serviceMethod(authorization) {
        Signature signature = thisJoinPoint.getSignature();
        logger.info("Entering method: {}", signature);
        logger.info("Annotation class: {}", authorization);
        for (String privilege : authorization.privileges()) {
            logger.info("Privilege: {}", privilege);
        }

        try {
            Context context = ContextFactory.createContext();
            if (!context.isAuthenticated()) {
                User user = context.getAuthenticatedUser();
                logger.info("Context is not authenticated!");
                logger.info("Authenticated user is: {}", user.getUsername());
            } else {
                logger.info("Context is authenticated!");
            }
        } catch (Exception e) {
            logger.error("Unable to create and authenticate context!", e);
        }

    }
}
