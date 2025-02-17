package com.peakyapi.repositories;

import com.peakyapi.models.Episode;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;


public interface EpisodesRespository extends JpaRepository<Episode, Integer>, PagingAndSortingRepository<Episode, Integer> {

   Episode findByTitle(String title);

   Iterable<Episode> findEpisodeBySeasonNumber(int seasonNumber);

   Episode findEpisodeBySeasonNumberAndEpisodeNumber(int seasonNumber, int episodeNumber);

  @Transactional
  public int removeEpisodeById(int id);

  @Transactional
  default void saveEpisode(Episode episode) {
    save(episode);
  }

   Page<Episode> findAll(final Pageable pageable);
}
