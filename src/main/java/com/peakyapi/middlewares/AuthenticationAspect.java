package com.peakyapi.middlewares;

import com.peakyapi.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuthenticationAspect {
    @Autowired
    UserService userService;

    @Before("execution(* com.peakyapi.controllers.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestParam)")
    public void checkAuthentication(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token == null)
            throw new RuntimeException();

        if (userService.findUserByToken(token) == null)
            throw new RuntimeException();
    }

}
