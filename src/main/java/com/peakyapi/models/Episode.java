package com.peakyapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "season_number", nullable = false)
    private Integer seasonNumber;

    @Column(name = "episode_number", nullable = false)
    private Integer episodeNumber;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    public Episode(Integer id, Integer seasonNumber, Integer episodeNumber, String title, String description) {
        this.id = id;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.description = description;
    }

    public Episode(Integer seasonNumber, Integer episodeNumber, String title, String description) {
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.description = description;
    }

    public Episode() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}