package com.peakyapi.services;

import com.peakyapi.models.Episode;
import com.peakyapi.repositories.EpisodesRespository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class EpisodeService {
    @Autowired
    EpisodesRespository episodesRespository;

    @Autowired
    EntityManager entityManager;

    public ArrayList<Episode> getEpisodes() {
        return (ArrayList<Episode>) episodesRespository.findAll();
    }

    //TODO
    public ArrayList<Episode> getEpisodesPagination(Pageable peable) {
        return (ArrayList<Episode>) episodesRespository.findAll(peable);
    }

    public ArrayList<Episode> getEpisodesBySeasonNumber(int seasonNumber) {
        return (ArrayList<Episode>) episodesRespository.findEpisodeBySeasonNumber(seasonNumber);
    }

    public Episode getEpisodeById(int id) {
        return episodesRespository.findById(id).orElse(null);
    }

    public Episode getEpisodeByTitle(String title) {
        return episodesRespository.findByTitle(title);
    }

    public void saveEpisode(Episode episode) {
        episodesRespository.saveEpisode(episode);
    }

    public int removeEpisodeById(int id) {
        return episodesRespository.removeEpisodeById(id);
    }

    public Episode getEpisodeBySeasonAndByNumberEpisode(int seasonNumber, int episodeNumber) {
        return episodesRespository.findEpisodeBySeasonNumberAndEpisodeNumber(seasonNumber, episodeNumber);
    }

    public ArrayList<Episode> getEpisodesByFilter(Integer id, Integer season_number, Integer episode_number, String title) {
        StringBuilder queryBuilder = new StringBuilder("SELECT e from Episode e where 1=1");

        Map<String,Object> params = new HashMap<>();

        if (id != null && id != 1) {
            params.put("id",id);
            queryBuilder.append("AND e.id = :id");
        }
        if (season_number != null && season_number >= 1) {
            params.put("season_number",season_number);
            queryBuilder.append(" AND e.seasonNumber = :season_number");
        }
        if (episode_number != null && episode_number != -1) {
            params.put("episode_number",episode_number);
            queryBuilder.append(" AND e.episodeNumber = :episode_number");
        }
        if (title != null && !title.isEmpty()) {
            params.put("title",title);
            queryBuilder.append(" AND e.title = :title");
        }

        TypedQuery<Episode> query = entityManager.createQuery(queryBuilder.toString(),Episode.class);

        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(),param.getValue());
        }
        return (ArrayList<Episode>) query.getResultList();
    }
}
