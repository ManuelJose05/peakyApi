package com.peakyapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.beans.ConstructorProperties;

@Entity
@Table(name = "characters")
@Schema(description = "Character model representing a character from the series")
public class Character {
    @Id
    @Column(name = "id", nullable = false)
    @Schema(name = "id",type = "Integer",example = "1",required = true,description = "Character ID assigned in the database")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    @Schema(name = "name", type = "String", example = "Thomas Shelby", required = true, description = "Character's name")
    private String name;

    @Column(name = "birth_date", length = 50)
    @Schema(name = "birthDate", type = "String", example = "1890-01-01", description = "Character's date of birth")
    private String birthDate;

    @Column(name = "nationality", length = 50)
    @Schema(name = "nationality", type = "String", example = "British", description = "Character's nationality")
    private String nationality;

    @Lob
    @Column(name = "image_url")
    @Schema(name = "imageUrl", type = "String", example = "https://example.com/image.jpg", description = "URL of the character image")
    private String imageUrl;

    @Column(name = "actor", length = 100)
    @Schema(name = "actor", type = "String", example = "Cillian Murphy", description = "Actor playing the character")
    private String actor;

    @Lob
    @Column(name = "description")
    @Schema(name = "description", type = "String", example = "Líder astuto y calculador de los Peaky Blinders", description = "Character's description")
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