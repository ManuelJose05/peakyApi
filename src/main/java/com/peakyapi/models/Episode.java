package com.peakyapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "episodes")
@Schema(description = "Model episode which depicts an episode of the series")
public class Episode {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "ID", description = "Unique episode identifier", example = "1", required = true)
    private Integer id;

    @Column(name = "season_number", nullable = false)
    @Schema(name = "Season Number", description = "Season number in which the episode appears", example = "3", required = true)
    private Integer seasonNumber;

    @Column(name = "episode_number", nullable = false)
    @Schema(name = "Episode Number", description = "Episode number within the season", example = "5", required = true)
    private Integer episodeNumber;

    @Column(name = "title")
    @Schema(name = "Title", description = "Episode title", example = "The Redemption", required = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    @Schema(name = "Description", description = "Detailed character description", example = "En este episodio, Tommy enfrenta sus demonios del pasado.", required = true)
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
