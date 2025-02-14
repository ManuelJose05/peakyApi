package com.peakyapi.repositories;

import com.peakyapi.models.Character;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface CharactersRepository extends JpaRepository<Character, Integer>, JpaSpecificationExecutor<Character> {

    Character findByName(String name);

    Character findById(int id);

    ArrayList<Character> findCharactersByNationality(String nationality);

    @Query("SELECT Character FROM Character c WHERE c.birthDate = :year")
    ArrayList<Character> findCharactersByBirthDate(String year);

    @Transactional
    int removeCharacterById(int id);

    List<Character> findCharactersByBirthDateAndIdAndNameAndNationality(String birthDate,int id, String name, String nationality);

}
