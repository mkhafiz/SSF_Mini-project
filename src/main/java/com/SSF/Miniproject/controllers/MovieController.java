package com.SSF.Miniproject.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SSF.Miniproject.models.Movie;
import com.SSF.Miniproject.models.MovieSearch;
import com.SSF.Miniproject.services.MovieService;

import jakarta.json.Json;

@Controller
@RequestMapping
public class MovieController {

    // 1 Req = POST & GET request --> search + detail/save + home + login + index
    // 2 Req = @PathVariable
    // 3 Req = Support more than 1 user
    // 4 Req = min 3 views

    @Autowired
    private MovieService movieSvc;

    // TmdbMovies movie = new
    // TmdbApi("ec6d862bc5e4d8d19702e33728e1980a").getMovies();
    // MovieDb movies = movie.getMovie(78, "en", MovieMethod.credits,
    // MovieMethod.images, MovieMethod.similar);
    // sess.setAttribute("sess", movie);
    // model.addAttribute("movie", movie);
    // return "index";


    // path + return = home
    @RequestMapping(path = { "/home" })
    public String homePage() {
        return "home";
    }

    // Trending wk
    @GetMapping(path = "/")
    public String trendMovies(Model model, HttpSession sess) {
        List<Movie> movies = movieSvc.trendMovies();
        sess.setAttribute("sess", movies);
        model.addAttribute("movies", movies);
        return "index";
    }

    // WIP (search)
    @GetMapping(path = "/search")
    public String searchMovies(Model model, @RequestParam String word) {
        List<MovieSearch> search = movieSvc.searchMovie(word);
        model.addAttribute("search", search);
        model.addAttribute("word", word);
        return "search";
    }

    // Detailed page
    @GetMapping(path = "/search/{id}")
    public String getBookById(Model model, @PathVariable int id) {
    List<MovieSearch> movieDetails = movieSvc.detailMovie(id);
    model.addAttribute("detail", movieDetails);
    return "detail";
    }


    // Json
    @PostMapping(path = "/movies") // edit PATH
    public String savedMovie(HttpSession sess) {

        List<Movie> myMovies = (List<Movie>) sess.getAttribute("sess");
        for (int i = 0; i < 10; i++) {
            String payload = Json.createObjectBuilder()
                    .add("id", myMovies.get(i).getId())
                    .add("original_language", myMovies.get(i).getOriginal_language())
                    .add("title", myMovies.get(i).getTitle())
                    .add("overview", myMovies.get(i).getOverview())
                    .add("popularity", myMovies.get(i).getPopularity())
                    .add("poster_path", myMovies.get(i).getPoster_path())
                    .add("release_date", myMovies.get(i).getRelease_date())
                    // .add("title", myMovies.get(i).getTitle())
                    // .add("video", myMovies.get(i).getVideo())
                    .add("vote_average", myMovies.get(i).getVote_average())
                    .add("vote_count", myMovies.get(i).getVote_count())
                    .build().toString();

            movieSvc.saveToRepo(myMovies.get(i).getId(), payload);
        }
        System.out.println("All Saved");
        return "redirect:/";
    }
}