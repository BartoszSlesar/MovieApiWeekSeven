package com.bard.movielistweekseven.api.v1;


import com.bard.movielistweekseven.model.Movie;
import com.bard.movielistweekseven.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieApi {


    private final MovieService movieService;


    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Movie>> getMovies() {
        return new ResponseEntity<>(this.movieService.getAllMovies(), HttpStatus.OK);
    }


    @GetMapping(params = "id", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Movie> getMovieById(@RequestParam(name = "id") long id) {
        Optional<Movie> getMovieById = this.movieService.getMovieById(id);
        return getMovieById
                .map(movie -> new ResponseEntity<>(movie, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping(params = "name", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Movie> getMovieByName(@RequestParam(name = "name") String name) {
        Optional<Movie> getMovieByName = this.movieService.getMovieByName(name);
        return getMovieByName
                .map(movie -> new ResponseEntity<>(movie, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(params = "year", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Movie>> getMoviesByYear(@RequestParam(name = "year") String year) {
        Optional<List<Movie>> getMoviesByYear = this.movieService.getMovieByYear(year);
        return getMoviesByYear
                .map(movies -> new ResponseEntity<>(movies, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Movie> addMovie(@Validated @RequestBody Movie movie) {
        Optional<Movie> addMovie = this.movieService.addMovie(movie);
        return addMovie
                .map(addedMovie -> new ResponseEntity<>(addedMovie, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    }
}
