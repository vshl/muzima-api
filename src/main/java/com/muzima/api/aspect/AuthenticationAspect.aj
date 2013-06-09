package com.muzima.api.aspect;

import com.muzima.api.annotation.Authorization;
import com.muzima.api.context.Context;
import com.muzima.api.context.ContextFactory;
import com.muzima.api.model.User;
import org.aspectj.lang.Signature;

/**
 * TODO: Write brief description about the class here.
 */
public aspect AuthenticationAspect {

    pointcut serviceMethod(Authorization authorization): execution(@Authorization * *(..))
            && @annotation(authorization);

    before(Authorization authorization): serviceMethod(authorization) {
        Signature signature = thisJoinPoint.getSignature();
        System.out.printf("Entering method: %s. \n", signature);
        System.out.printf("Annotation class: %s. \n", authorization);
        for (String privilege : authorization.privileges()) {
            System.out.printf("Privilege: %s.\n", privilege);
        }

        try {
            Context context = ContextFactory.createContext();
            if (!context.isAuthenticated()) {
                User user = context.getAuthenticatedUser();
                System.out.println("Context is not authenticated!");
                System.out.println("Authenticated user is: " + user.getUsername());
            } else {
                System.out.println("Context is authenticated!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
