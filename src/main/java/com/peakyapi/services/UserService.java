package com.peakyapi.services;

import com.peakyapi.models.User;
import com.peakyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> listAll() {
        return userRepository.findAll();
    }

    public boolean addUser(User user) {
        return userRepository.saveUser(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByToken(String token) {
        return userRepository.findByToken(token);
    }
}
