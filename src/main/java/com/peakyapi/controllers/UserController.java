package com.peakyapi.controllers;

import com.peakyapi.exception.GlobalExceptionHandler;
import com.peakyapi.models.User;
import com.peakyapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@Tag(name = "User",description = "User controller")
public class UserController {
    @Autowired
    UserService userService;

    Map<String,Object> response = new HashMap<>();

    String privateKey = "9b8e0c3a4d7f6a9832c1d4b5e6a7f8c9a1b2c3d4e5f67890123456789abcdef0";

    @Operation(summary = "List all users", description = "Returns a list of all users in the database")
    @GetMapping(value = "/users")
    public ResponseEntity<Map<String, Object>> listAll(
            @RequestParam String token) {
        response.clear();

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid token", HttpStatus.UNAUTHORIZED);

        Iterable<User> users = userService.listAll();
        if (users == null) return new GlobalExceptionHandler().customException(null,"No users found",HttpStatus.NO_CONTENT);
        ArrayList<String> emails = new ArrayList<>();
        users.forEach(user -> emails.add(user.getEmail()));

        response.put("users",emails);
        response.put("total_users",emails.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Create a new user", description = "Registers a new user and returns the created user with a token.")
    @PostMapping(value = "/register")
    public ResponseEntity<Map<String,Object>> createUser(
            @RequestBody User user) {
        response.clear();

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        if (user == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid user", HttpStatus.UNAUTHORIZED);

        String token = Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,privateKey).compact();

        User temp = new User(user.getEmail(),bcrypt.encode(user.getPassword()),token,user.getRol());

        if (!userService.addUser(temp)) return new GlobalExceptionHandler().customException("User already exists","A user with the given details already exists in the database",HttpStatus.CONFLICT);
        response.put("token", token);
        response.put("status", HttpStatus.CREATED);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "Login a user", description = "Authenticates a user and returns their information along with a token.")
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String,Object>> lgoin(
            @RequestBody User user) {
        response.clear();
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        if (user == null) return new GlobalExceptionHandler().customException("Error request","Email,password and token are required",HttpStatus.BAD_REQUEST);

        User bdUser = userService.findUserByEmail(user.getEmail());

        if (!bcrypt.matches(user.getPassword(),bdUser.getPassword())) return new GlobalExceptionHandler().customException("Unauthorized","Invalid password",HttpStatus.UNAUTHORIZED);

        user.setToken(bdUser.getToken());

        response.put("status", HttpStatus.ACCEPTED);
        response.put("message", "Successfully login. Welcome friend!!");
        response.put("user",user);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
