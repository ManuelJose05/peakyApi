package com.peakyapi.controllers;

import com.peakyapi.exception.GlobalExceptionHandler;
import com.peakyapi.models.User;
import com.peakyapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
public class UserController {
    @Autowired
    UserService userService;

    Map<String,Object> response = new HashMap<>();

    String privateKey = "9b8e0c3a4d7f6a9832c1d4b5e6a7f8c9a1b2c3d4e5f67890123456789abcdef0";

    //Get method which return a list of all users in BBDD
    @GetMapping(value = "/users")
    public ResponseEntity<Map<String, Object>> listAll(@RequestParam String token) {
        //Removes all the mappings from response
        response.clear();

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid token", HttpStatus.UNAUTHORIZED);

        Iterable<User> users = userService.listAll();
        if (users == null) return new GlobalExceptionHandler().customException(null,"No users found",HttpStatus.NO_CONTENT);
        ArrayList<String> emails = new ArrayList<>();
        users.forEach(user -> emails.add(user.getEmail()));

        //ADD TO RESPONSE ALL THE USERS EMAILS AND THE TOTAL OF THOSE
        response.put("users",emails);
        response.put("total_users",emails.size());

        //Return the response and the Http Status (OK == 200)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //USERS

    //Método Post que crea un usuario en la BBDD y lo devuelve al usuario para que vea que se ha creado correctamente
    @PostMapping(value = "/register")
    public ResponseEntity<Map<String,Object>> createUser(@RequestBody User user) {
        //Removes all the mappings from response
        response.clear();

        //Instaciamos bcrypt que será un objeto de la clase BcryptPasswordEnconder para hashear las contraseñas
        //de los usuarios que se vayan creando
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        //Desectructuramos el contenido del cuerpo y guardamos en variables el email / password
        //La contraseña será hasheada mediante el enconder

        if (user == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid user", HttpStatus.UNAUTHORIZED);

        //Generamos un token con la ayuda de Jwts
        String token = Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,privateKey).compact();

        //Creamos un objeto de tipo user, lo subimos a la base de datos mediante el servicio
        User temp = new User(user.getEmail(),bcrypt.encode(user.getPassword()),token,user.getRol());

        if (!userService.addUser(temp)) return new GlobalExceptionHandler().customException("User already exists","A user with the given details already exists in the database",HttpStatus.CONFLICT);
        response.put("token", token);
        response.put("status", HttpStatus.CREATED);

        //Retornamos el objeto de tipo User para que el usuario vea que se ha realizado correctamente
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String,Object>> lgoin(@RequestBody User user) {
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
