package com.peakyapi.middlewares;

import com.peakyapi.exception.GlobalExceptionHandler;
import com.peakyapi.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class AuthenticationAspect {
    @Autowired
    UserService userService;

    @Before("execution(* com.peakyapi.controllers.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void checkAuthentication(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token == null)
            throw new RuntimeException();

        if (userService.findUserByToken(token) == null)
            throw new RuntimeException();
    }

}
