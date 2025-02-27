package com.peakyapi.repositories;

import com.peakyapi.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.peakyapi.models.Character;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String correo);

    User findByToken(String token);

    User deleteUserByEmail(String email);

    @Transactional
    default boolean saveUser(User user) {
        if (findByEmail(user.getEmail()) != null) return false;
        save(user);
        return true;
    }

}
