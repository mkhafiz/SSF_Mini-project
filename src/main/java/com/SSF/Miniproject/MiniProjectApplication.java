package com.SSF.Miniproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MiniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectApplication.class, args);

		// TmdbMovies movies = new TmdbApi("<ec6d862bc5e4d8d19702e33728e1980a>").getMovies();
		// MovieDb movie = movies.getMovie(5353, "en");
		// System.out.println(movie);

	}

}
