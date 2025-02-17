package com.peakyapi.controllers;
import com.peakyapi.exception.GlobalExceptionHandler;
import com.peakyapi.models.Episode;
import com.peakyapi.models.Rol;
import com.peakyapi.models.User;
import com.peakyapi.services.EpisodeService;
import com.peakyapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class EpisodesController {
    @Autowired
    EpisodeService episodeService;

    @Autowired
    UserService userService;

    Map<String,Object> response = new HashMap<>();


    //EPISODES

    @GetMapping(value = "/episodes")
    public ResponseEntity<Map<String,Object>> episodes(@RequestParam String token,@RequestParam(required = false) Integer id,
                                                       @RequestParam(required = false) Integer season_number,
                                                       @RequestParam(required = false) Integer episode_number,
                                                       @RequestParam(required = false) String title) {
        response.clear();

        System.out.println(season_number);

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized", "Invalid token", HttpStatus.UNAUTHORIZED);

        ArrayList<Episode> episodes = episodeService.getEpisodesByFilter(id,season_number,episode_number,title);

        if (episodes == null || episodes.isEmpty()) return new GlobalExceptionHandler().customException("No episodes","episodes not found",HttpStatus.NOT_FOUND);

        response.put("messgae","Successfully retrieved episodes");
        response.put("status",HttpStatus.OK);
        response.put("episodes",episodes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/episodes/list")
    public ResponseEntity<Map<String, Object>> listEpisodes(@RequestParam String token) {
        //Removes all the mappings from response
        response.clear();

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid token", HttpStatus.UNAUTHORIZED);

        ArrayList<Episode> episodes = episodeService.getEpisodes();
        if (episodes == null) return new GlobalExceptionHandler().customException(null,"No episodes found",HttpStatus.NO_CONTENT);
        response.put("episodes",episodes);
        response.put("total_episodes",episodes.size());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/episodes")
    public ResponseEntity<Map<String,Object>> addEpisode(@RequestBody Map<String,String> body, @RequestParam String token) {
        response.clear();

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid token",HttpStatus.UNAUTHORIZED);
        if (temp.getRol().equals(Rol.USER)) return new GlobalExceptionHandler().customException("Unauthorized","Only admin'users can add episodes",HttpStatus.UNAUTHORIZED);

        int season_number = body.get("season_number") == null ? 0 : Integer.parseInt(body.get("season_number"));
        int episode_number = body.get("episode_number") == null ? 0 : Integer.parseInt(body.get("episode_number"));
        String title = body.get("title") == null ? "Episode " + episode_number : body.get("title");
        String description = body.get("description") == null ? "" : body.get("description");

        Episode aux = new Episode(season_number,episode_number,title,description);

        episodeService.saveEpisode(aux);
        response.put("status", HttpStatus.OK);
        response.put("message", "Successfully added episode");
        response.put("episode", temp);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/episodes/update/{id}")
    public ResponseEntity<Map<String,Object>> updateEpisode(@RequestBody Map<String,Object> body, @PathVariable int id,@RequestParam String token) {
        response.clear();

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid token",HttpStatus.UNAUTHORIZED);
        if (temp.getRol().equals(Rol.USER)) return new GlobalExceptionHandler().customException("Unauthorized","Only admin'users can update episodes",HttpStatus.UNAUTHORIZED);

        int season_number = body.get("season_number") == null ? 0 : Integer.parseInt(body.get("season_number").toString());
        int episode_number = body.get("episode_number") == null ? 0 : Integer.parseInt(body.get("episode_number").toString());
        String title = body.get("title") == null ? "Episode " + episode_number : body.get("title").toString();
        String description = body.get("description") == null ? "" : body.get("description").toString();

        Episode aux = new Episode(id,season_number,episode_number,title,description);

        episodeService.saveEpisode(aux);
        response.put("status", HttpStatus.OK);
        response.put("message", "Successfully updated episode");
        response.put("episode", aux);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping(value = "/episodes/delete/{id}")
    public ResponseEntity<Map<String,Object>> deleteEpisode(@PathVariable int id, @RequestParam String token) {
        response.clear();

        User temp = userService.findUserByToken(token);
        if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid token",HttpStatus.UNAUTHORIZED);
        if (temp.getRol().equals(Rol.USER)) return new GlobalExceptionHandler().customException("Unauthorized","Only admin'users can delete episodes",HttpStatus.UNAUTHORIZED);

        if (episodeService.removeEpisodeById(id) == 0) return new GlobalExceptionHandler().customException("BBDD error","Cannot remove episode from BD",HttpStatus.INTERNAL_SERVER_ERROR);

        response.put("status",HttpStatus.OK);
        response.put("message", "Successfully deleted episode");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //TODO
    @GetMapping(value = "/episodes/listPagination")
    public ResponseEntity<Map<String,Object>> getEpisodesPagination(@PageableDefault(size=6) Pageable pageable, @RequestParam String token) {
       response.clear();

       User temp = userService.findUserByToken(token);
       if (temp == null) return new GlobalExceptionHandler().customException("Unauthorized","Invalid token",HttpStatus.UNAUTHORIZED);

       Page<Episode> episodios = episodeService.getEpisodesPagination(pageable);

       if (episodios.isEmpty()) return new GlobalExceptionHandler().customException("Not found","Server cannot find episodes",HttpStatus.NOT_FOUND);

        response.put("episodes", episodios.getContent());
        response.put("total_episodes", episodios.getTotalElements());
        response.put("total_pages", episodios.getTotalPages());
        response.put("current_page", episodios.getNumber());
        response.put("page_size", episodios.getSize());
        response.put("has_next", episodios.hasNext());
        response.put("has_previous", episodios.hasPrevious());
        response.put("next","http://localhost:8080/api/episodes/listPagination?page=" + (episodios.getNumber() + 1));

       return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
