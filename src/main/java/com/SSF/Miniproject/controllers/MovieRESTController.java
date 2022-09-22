package com.SSF.Miniproject.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.SSF.Miniproject.models.MovieSearch;
import com.SSF.Miniproject.services.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
// @RequestMapping(path="/movie")
public class MovieRESTController {

    @Autowired
    private MovieService movieSvc;

    @GetMapping(path="/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMovieDetails(@RequestParam int id) {
        
        Optional<MovieSearch> opt = movieSvc.getMovieById(id);

        if (opt.isEmpty()) {
            JsonObject err = Json.createObjectBuilder()
                    .add("Error", " movie details %s not found".formatted(id))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(err.toString());
        }
        MovieSearch ms = opt.get();
        return ResponseEntity.ok(ms.toJson().toString());
    }

}