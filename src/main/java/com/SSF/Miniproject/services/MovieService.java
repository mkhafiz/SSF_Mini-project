package com.SSF.Miniproject.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.SSF.Miniproject.models.Movie;
import com.SSF.Miniproject.repositories.MovieRepository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class MovieService {

    private static final String URL = "https://api.themoviedb.org/3/trending/all/week";
    // https://api.themoviedb.org/3/trending/all/week
    // https://api.themoviedb.org/3/discover/movie (initial)

    @Value("${API_KEY}")
    private String key;

    @Autowired
    private MovieRepository movieRepo;

    public List<Movie> getMovies() {

        String url = UriComponentsBuilder.fromUriString(URL)
                // .queryParam("sort_by", "popularity.desc/550")
                .queryParam("api_key", key)
                .toUriString();
        System.out.println(url);

        // Create the GET request, GET url
        RequestEntity<Void> req = RequestEntity.get(url).build();
        // calling API
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp; // = template.exchange(req, String.class);

        try {
            // Throws an exception if status code not in between 200 - 399
            // Get response from the client with our request
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            return Collections.emptyList();
        }

        // Get the payload and do something with it
        String payload = resp.getBody();
        System.out.println("payload: " + payload);

        // Convert the String payload to JsonObject
        Reader strReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject movieResult = jsonReader.readObject();
        // get JsonArray "Data"
        JsonArray movieData = movieResult.getJsonArray("results");
        List<Movie> list = new LinkedList<>();

        // loop to get each object in array -> create method to create article
        for (int i = 0; i < movieData.size(); i++) {
            JsonObject jo = movieData.getJsonObject(i);
            list.add(Movie.create(jo));
        }
        return list;
    }

    public void saveToRepo(int i, String payload) {
        movieRepo.save(i, payload);
    }

    public Optional<Movie> getMovieById(String id) {
        return getMovieById(id.toString());
    }

    public Optional<Movie> getMovieById(int id) {
        // check if repo has the value
        String result = movieRepo.get(id);
        if (null == result)
            return Optional.empty();
            // create obj with values retrieved from redis
        return Optional.of(Movie.createNew(result));
    }
}