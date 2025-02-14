package com.peakyapi.models;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.annotations.ColumnDefault;

import javax.management.relation.Role;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Lob
    @Column(name = "token", nullable = false)
    private String token;

    @ColumnDefault("'user'")
    @Column(name = "rol", nullable = false, length = 100)
    private String rol;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {}

    public User(String email, String password, String token, String rol) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}