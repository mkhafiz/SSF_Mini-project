package com.SSF.Miniproject.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.SSF.Miniproject.models.Movie;
import com.SSF.Miniproject.models.MovieSearch;
import com.SSF.Miniproject.repositories.MovieRepository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class MovieService {

    private static String trendURL = "https://api.themoviedb.org/3/trending/all/week";
    private static String searchURL = "https://api.themoviedb.org/3/search/movie";
    private static String searchIdURL = "https://api.themoviedb.org/3/movie/{id}";

    @Value("${API_KEY}")
    private String key;

    @Autowired
    private MovieRepository movieRepo;

    public List<Movie> trendMovies() {

        String url = UriComponentsBuilder.fromUriString(trendURL)
                .queryParam("api_key", key)
                .toUriString();

        // Create the GET request, GET url
        RequestEntity<Void> req = RequestEntity.get(url).build();

        // calling API
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

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
    

    public List<MovieSearch> searchMovie(String word) {

        String url = UriComponentsBuilder.fromUriString(searchURL)
                .queryParam("include_adult", "false")
                .queryParam("api_key", key)
                .queryParam("query", word)
                .toUriString();

        // Create the GET request, GET url
        RequestEntity<Void> req = RequestEntity.get(url).build();
        // calling API
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        // Get the payload and do something with it
        String payload = resp.getBody();
        System.out.println("payload: " + payload);

        // Convert the String payload to JsonObject
        Reader strReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject movieResult = jsonReader.readObject();
        // get JsonArray "Data"
        JsonArray movieData = movieResult.getJsonArray("results");

        List<MovieSearch> list = new LinkedList<>();
        // loop to get each object in array -> create method to create article
        for (int i = 0; i < movieData.size(); i++) {

            JsonObject jo = movieData.getJsonObject(i);
            Integer id = jo.getInt("id");
            // String poster_path = jo.getString("poster_path");
            String original_title = jo.getString("original_title");
            String overview = jo.getString("overview");
            String release_date = jo.getString("release_date");
            // String backdrop_path = jo.getString("backdrop_path");

            // Integer total_results = jo.getInt("total_results");
            // Integer total_pages = jo.getInt("total_pages");

            list.add(MovieSearch.createMovie(id,
            // poster_path,
                    original_title,
                    overview,
                    release_date
                    // backdrop_path
                    ));
        }
        return list;
    }

    public List<MovieSearch> detailMovie(int id) {

        Map<String, Integer> urlParams = new HashMap<>();
        urlParams.put("id", id);

        String url = UriComponentsBuilder.fromUriString(searchIdURL)
                .queryParam("api_key", key)
                .buildAndExpand(urlParams)
                .toUriString();

        // String fragment =
        // UriComponentsBuilder.fromPath("/{id}").queryParam("api_key",
        // key).toUriString();
        // String fullPath = UriComponentsBuilder.fromUriString(detailURL)
        // .fragment(fragment)
        // .path("/{id}")
        // .queryParam("api_key", key)
        // .toUriString();

        // Create the GET request, GET url
        RequestEntity<Void> req = RequestEntity.get(url).build();

        // calling API
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        // Get the payload and do something with it
        String payload = resp.getBody();
        System.out.println("payload: " + payload);

        // Convert the String payload to JsonObject
        Reader strReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject movieResult = jsonReader.readObject();
        // JsonArray movieData = movieResult.getJsonArray("results");
        ArrayList<MovieSearch> list = new ArrayList<>();

        JsonObject results = movieResult.asJsonObject();
        String backdrop_path = results.getString("backdrop_path");
        String original_title = results.getString("original_title");
        String overview = results.getString("overview");
        String release_date = results.getString("release_date");

        list.add(MovieSearch.specMovie(id,
                backdrop_path,
                original_title,
                overview,
                release_date));
                
        

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