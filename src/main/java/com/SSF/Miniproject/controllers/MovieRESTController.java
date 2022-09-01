// package com.SSF.Miniproject.controllers;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.SSF.Miniproject.models.Movie;
// import com.SSF.Miniproject.services.MovieService;

// import jakarta.json.Json;
// import jakarta.json.JsonObject;

// @RestController
// @RequestMapping(path = "/movie")
// public class MovieRESTController {

//     @Autowired
//     private MovieService movieSvc;

//     @GetMapping("{id}")
//     public ResponseEntity<String> getMovieDetails(@PathVariable String id) { 
//         Optional<Movie> opt = movieSvc.getMovieById(id);

//         if (opt.isEmpty()) {
//             JsonObject err = Json.createObjectBuilder()
//                 .add("error", "Cannot find movie details %s".formatted(id))
//                 .build();
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                 .body(err.toString());
//         }
//         Movie m = opt.get();
//         return ResponseEntity.ok(m.toJson().toString());
//     }
// }