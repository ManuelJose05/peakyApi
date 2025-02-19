package com.peakyapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "users")
@Schema(description = "User model which represents a user in the system.")
public class User {
    @Id
    @Column(name = "email", nullable = false, length = 100)
    @Schema(name = "Email", description = "User's email address", example = "usuario@example.com", required = true)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    @Schema(name = "Password", description = "User's password", example = "password123", required = true)
    private String password;

    @Lob
    @Column(name = "token", nullable = false)
    @Schema(name = "Token", description = "User's authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", required = true)
    private String token;

    @ColumnDefault("'user'")
    @Column(name = "rol", nullable = false, length = 100)
    @Schema(name = "Rol", description = "User's role", example = "admin", required = true)
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
