package com.SSF.Miniproject.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
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
// import com.SSF.Miniproject.repositories.UserRepository;

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
            list.toString();
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

            // JsonObject results = movieResult.asJsonObject();
            // Integer total_results = results.getInt("total_results");
            // Integer total_pages = results.getInt("total_pages");

            list.add(MovieSearch.searchMovie(
                    id,
                    // poster_path,
                    original_title,
                    overview,
                    release_date
                    // total_results,
                    // total_pages
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

        ArrayList<MovieSearch> list = new ArrayList<>();

        JsonObject results = movieResult.asJsonObject();
        String backdrop_path = results.getString("backdrop_path");
        String original_title = results.getString("original_title");
        String overview = results.getString("overview");
        String release_date = results.getString("release_date");

        Integer budget = results.getInt("budget");
        Integer revenue = results.getInt("revenue");
        String status = results.getString("status");
        String tagline = results.getString("tagline");
        JsonArray spoken_lang = results.getJsonArray("spoken_languages");
        JsonObject spoke = spoken_lang.getJsonObject(0);
        String name = spoke.getString("name"); // lang
        String poster_path = results.getString("poster_path");


        list.add(MovieSearch.specMovie(id, backdrop_path, original_title, overview,
                release_date,
                budget,
                revenue,
                status,
                tagline,
                name,
                poster_path
                ));

        return list;
    }

    public void saveToRepo( int id, String payload) {
        movieRepo.save( id, payload);
    }

    public Optional<MovieSearch> getMovieById(String id) {
        return getMovieById(id.toString());
    }

    public Optional<MovieSearch> getMovieById(int id) {
        // check if repo has the value
        String result = movieRepo.get(id);
        if (null == result)
            return Optional.empty();
        // create obj with values retrieved from redis
        return Optional.of(MovieSearch.createNew(result));
    }
}