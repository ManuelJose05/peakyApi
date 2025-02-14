package com.peakyapi.controllers;

import com.peakyapi.exception.GlobalExceptionHandler;
import com.peakyapi.models.Character;
import com.peakyapi.models.Rol;
import com.peakyapi.models.User;
import com.peakyapi.services.CharacterService;
import com.peakyapi.services.UserService;
import com.peakyapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping //Initial URL { https://localhost:8080/api/characters }
public class CharactersController {
    //Auth Services
    @Autowired
    UserService userService;

    @Autowired
    CharacterService characterService;

    //Create a map object which will be the response of HttpRequests
    Map<String,Object> response = new HashMap<>();


    //CHARACTERS

    //Get method which send user all saved characters in BBDD
    @GetMapping(value = "/characters/list")
    public ResponseEntity<Map<String, Object>> listCharacters(@RequestParam String token) {
        //Removes all the mappings from response
        response.clear();

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid token",HttpStatus.UNAUTHORIZED);

        ArrayList<Character> characters = characterService.getCharacters();
        if (characters == null) return new GlobalExceptionHandler().customException("Error","No characters found",HttpStatus.NO_CONTENT);
        response.put("characters", characters);
        response.put("total_characters",characters.size());

        //Return the response which contains the map and Http Status (OK == 200)
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PostMapping(value = "/characters")
    public ResponseEntity<Map<String,Object>> addCharacter(@RequestBody Character character, @RequestParam String token) {
        response.clear();

        User temp = userService.findUserByToken(token);
        if (!Utils.tokenValidate(token, temp)) return new GlobalExceptionHandler().customException("Access denegade","Invalid token",HttpStatus.UNAUTHORIZED);
        if (temp.getRol().equals(Rol.USER)) return new GlobalExceptionHandler().customException("Access denegade","Only admin'users can modify BD info",HttpStatus.UNAUTHORIZED);


        if (character.getName().isEmpty() || character.getActor().isEmpty()) return new GlobalExceptionHandler().customException("Bad request","Name and actor are required",HttpStatus.BAD_REQUEST);
        if (characterService.getCharacterByName(character.getName()) != null) return new GlobalExceptionHandler().customException("Created error","Character already exists",HttpStatus.CONFLICT);


        characterService.saveCharacter(character);
        response.put("status", HttpStatus.CREATED);
        response.put("message", "Successfully added character");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/characters/update/{id}")
    public ResponseEntity<Map<String,Object>> updateCharacter(@PathVariable int id, @RequestBody Character character, @RequestParam String token) {
        System.out.println("hace el update");
        response.clear();

        User temp = userService.findUserByToken(token);
        System.out.println(temp.toString());
        if (!Utils.tokenValidate(token, temp)) return new GlobalExceptionHandler().customException("Access denegade","Invalid token",HttpStatus.UNAUTHORIZED);
        if (temp.getRol().equals(Rol.USER)) return new GlobalExceptionHandler().customException("Access denegade","Only admin'users can modify BD info",HttpStatus.UNAUTHORIZED);

        if (characterService.getCharacterById(id) == null) return new GlobalExceptionHandler().customException("ID error","Character not found",HttpStatus.NOT_FOUND);

        if (characterService.saveCharacter(character) == null) return new GlobalExceptionHandler().customException("Error","BBDD cannot update character",HttpStatus.INTERNAL_SERVER_ERROR);
        else System.out.println("Lo actualiza");

        response.put("status", HttpStatus.OK);
        response.put("message", "Successfully updated character");
        response.put("character", character);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping(value = "/characters/delete/{id}")
    public ResponseEntity<Map<String,Object>> deleteCharacter(@PathVariable int id,@RequestParam String token) {
        response.clear();

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Access denegade","Invalid token",HttpStatus.UNAUTHORIZED);

        if (temp.getRol().equals(Rol.USER)) return new GlobalExceptionHandler().customException("Access denegade","Only admin'users can modify BD info",HttpStatus.UNAUTHORIZED);

        if (characterService.getCharacterById(id) == null) return new GlobalExceptionHandler().customException("Id error","Character not found",HttpStatus.NOT_FOUND);

        if (characterService.deleteCharacter(id) == 0) return new GlobalExceptionHandler().customException("Error","Cannot delete character",HttpStatus.INTERNAL_SERVER_ERROR);

        response.put("status", HttpStatus.OK);
        response.put("message", "Successfully deleted character");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    //FILTERS
    //https://www.youtube.com/watch?v=ly8rdlapkgU
    @GetMapping(value = "/characters")
    public ResponseEntity<Map<String, Object>> characters(@RequestParam String token,
                                                          @RequestParam(required = false) String nationality,
                                                          @RequestParam(required = false) String birthDate,
                                                          @RequestParam(required = false)
                                                          String name,@RequestParam(required = false) Integer id) {
        response.clear();

        if (userService.findUserByToken(token) == null) return new GlobalExceptionHandler().customException("Access denegade","Invalid token",HttpStatus.UNAUTHORIZED);

        ArrayList<Character> personajes = characterService.findByFilters(birthDate,id == null? -1 : id,name,nationality);

        if (personajes == null || personajes.isEmpty()) return new GlobalExceptionHandler().customException("No episodes","Episodes not found",HttpStatus.NOT_FOUND);

        response.put("status", HttpStatus.OK);
        response.put("message", "Successfully retrieved characters");
        response.put("characters", personajes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
