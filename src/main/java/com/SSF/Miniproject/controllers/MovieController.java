package com.SSF.Miniproject.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SSF.Miniproject.models.Movie;
import com.SSF.Miniproject.models.MovieSearch;
// import com.SSF.Miniproject.repositories.UserRepository;
import com.SSF.Miniproject.services.MovieService;

import jakarta.json.Json;

@Controller
@RequestMapping
public class MovieController {

    @Autowired
    private MovieService movieSvc;
    

    @GetMapping(path ="/")
    public String loginPage() {
        return "login";
    }

    @PostMapping(path = "/login", consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String CheckLogin(
            @RequestParam(name = "name") String username,
            @RequestParam(name = "password") String password,
            HttpSession sess, Model model) 
            {
            sess.setAttribute("name", username);
            model.addAttribute("name", username.toUpperCase());
            return "home";
    }

    @GetMapping(path = "/home")
    public String trendMovies(Model model, HttpSession sess) {
        List<Movie> movies = movieSvc.trendMovies();
        sess.setAttribute("sess", movies);
        model.addAttribute("movies", movies);
        return "home";
    }

    @GetMapping(path = "/search")
    public String searchMovies(Model model, @RequestParam String word) {
        List<MovieSearch> search = movieSvc.searchMovie(word);
        model.addAttribute("search", search);
        model.addAttribute("word", word);
        return "search";
    }

    @GetMapping(path = "/search/{id}")
    public String detailMovie(Model model, @PathVariable int id) {
        List<MovieSearch> movieDetails = movieSvc.detailMovie(id);
        model.addAttribute("detail", movieDetails);
        return "detail";
    }


    @PostMapping(path = "/movie")
    public String savedMovie(HttpSession sess) {

    List<MovieSearch> myMovies = (List<MovieSearch>) sess.getAttribute("sess");
    for (int i = 0; i < 12; i++) {
    String payload = Json.createObjectBuilder()
    .add("id", myMovies.get(i).getId())
    .add("backdrop_path", myMovies.get(i).getBackdrop_path())
    .add("original_title", myMovies.get(i).getOriginal_title())
    .add("overview", myMovies.get(i).getOverview())
    .add("release_date", myMovies.get(i).getRelease_date())
    .add("budget", myMovies.get(i).getBudget())
    .add("revenue", myMovies.get(i).getRevenue())
    .add("status", myMovies.get(i).getStatus())
    .add("tagline", myMovies.get(i).getTagline())
    .add("name", myMovies.get(i).getName())
    .add("poster_path", myMovies.get(i).getPoster_path())
    .build().toString();

    movieSvc.saveToRepo(myMovies.get(i).getId(), payload);
    }
    System.out.println("All Saved");
    return "redirect:/";
    }
}