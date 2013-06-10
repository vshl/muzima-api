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
