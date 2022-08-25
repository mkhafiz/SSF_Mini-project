package com.SSF.Miniproject.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SSF.Miniproject.models.Movie;
import com.SSF.Miniproject.services.MovieService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

@Controller
@RequestMapping
public class MovieController {

    @Autowired
    private MovieService movieSvc;

    // no need for parameter + @requestparam
    @GetMapping(path = "/")
    public String getArticles(Model model, HttpSession sess) {

        // List<Movie> movies = movieSvc.getMovies();
        // sess.setAttribute("sess", movies);
        // model.addAttribute("movies", movies);
        // return "index";

        TmdbMovies movies = new TmdbApi("ec6d862bc5e4d8d19702e33728e1980a").getMovies();
		MovieDb movie = movies.getMovie(5353, "en");
        sess.setAttribute("sess", movie);
        model.addAttribute("movie", movie);
        return "index";

    }

    
    
}