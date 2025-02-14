package com.peakyapi.repositories;

import com.peakyapi.models.Episode;
import com.peakyapi.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface EpisodesRespository extends JpaRepository<Episode, Integer> {

  public Episode findByTitle(String title);

  public Iterable<Episode> findEpisodeBySeasonNumber(int seasonNumber);

  public Episode findEpisodeBySeasonNumberAndEpisodeNumber(int seasonNumber, int episodeNumber);

  @Transactional
  public int removeEpisodeById(int id);

  @Transactional
  default void saveEpisode(Episode episode) {
    save(episode);
  }

  //TODO
  public Iterable<Episode> findAll(Pageable pageable);
}
