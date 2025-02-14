package com.peakyapi.services;

import com.peakyapi.models.Character;
import com.peakyapi.repositories.CharactersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CharacterService {
    @Autowired
    private CharactersRepository charactersRepository;

    @Autowired
    private EntityManager entityManager;

    public ArrayList<Character> getCharacters() {
        return (ArrayList<Character>) charactersRepository.findAll();
    }

    public Character getCharacterByName(String name) {
        return charactersRepository.findByName(name);
    }

    public Character getCharacterById(int id) {
        return charactersRepository.findById(id);
    }

    public Character saveCharacter(Character character) {
        return charactersRepository.save(character);
    }

    public int deleteCharacter(int id) {
        return charactersRepository.removeCharacterById(id);
    }

    public ArrayList<Character> findCharactersByNationality(String nationality) {
        return charactersRepository.findCharactersByNationality(nationality);
    }

    public ArrayList<Character> findCharactersByYear(String year) {
        return charactersRepository.findCharactersByBirthDate(year);
    }

    public ArrayList<Character> findByFilters(String year,int id,String name,String nationality){
        Map<String,Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT c FROM Character c where 1=1");

        if (year != null && !year.isEmpty()) {
            params.put("year", year);
            queryBuilder.append(" AND c.birth_date = :year");
        }
        if (id != -1) {
            params.put("id", id);
            queryBuilder.append(" AND c.id = :id");
        }
        if (name != null && !name.isEmpty()) {
            params.put("name", name);
            queryBuilder.append(" AND c.name = :name");
        }
        if (nationality != null && !nationality.isEmpty()) {
            params.put("nationality", nationality);
            queryBuilder.append(" AND c.nationality = :nationality");
        }

        TypedQuery<Character> query = entityManager.createQuery(queryBuilder.toString(),Character.class);

        for (Map.Entry<String, Object> param: params.entrySet()) {
            query.setParameter(param.getKey(),param.getValue());
        }

        return (ArrayList<Character>) query.getResultList();
    }

}
