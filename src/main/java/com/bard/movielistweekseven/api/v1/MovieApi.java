package com.bard.movielistweekseven.api.v1;


import com.bard.movielistweekseven.model.Movie;
import com.bard.movielistweekseven.services.MovieService;
import com.bard.movielistweekseven.services.UserService;
import com.bard.movielistweekseven.utils.SentConfirmationEmail;
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

    private final UserService userService;


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


    @GetMapping(path = "/personalToken", params = "email", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> getToken(@RequestParam(name = "email") String email) {
        Optional<String> optionalToken = userService.generateToken(email);
        return optionalToken
                .map(personalToken -> new ResponseEntity<>(personalToken, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>("User Already exist", HttpStatus.FORBIDDEN));

    }


    @PostMapping
    @SentConfirmationEmail
    public ResponseEntity<Movie> addMovie(@RequestParam(name = "token") String token, @Validated @RequestBody Movie movie) {
        if (this.userService.checkIfPresent(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<Movie> addMovie = this.movieService.addMovie(movie);
        return addMovie
                .map(addedMovie -> new ResponseEntity<>(addedMovie, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    }
}
