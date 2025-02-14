package com.peakyapi.models;

import jakarta.persistence.*;

import java.beans.ConstructorProperties;

@Entity
@Table(name = "characters")
public class Character {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "birth_date", length = 50)
    private String birthDate;

    @Column(name = "nationality", length = 50)
    private String nationality;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "actor", length = 100)
    private String actor;

    @Lob
    @Column(name = "description")
    private String description;

    public Character() {}

    public Character(String name, String birthDate, String nationality, String imageUrl, String actor, String description) {
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.imageUrl = imageUrl;
        this.actor = actor;
        this.description = description;
    }
    public Character(int id,String name, String birthDate, String nationality, String imageUrl, String actor, String description) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.imageUrl = imageUrl;
        this.actor = actor;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", nationality='" + nationality + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", actor='" + actor + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}