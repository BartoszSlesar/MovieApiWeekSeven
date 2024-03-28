package com.bard.movielistweekseven.services;


import com.bard.movielistweekseven.model.Movie;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private List<Movie> movieList;


    private static long movieId = 5L;


    public List<Movie> getAllMovies() {
        return this.movieList;
    }

    public Optional<Movie> getMovieById(long id) {
        return this.movieList.stream().filter(e -> e.getMovieID() == id).findFirst();
    }

    public Optional<Movie> getMovieByName(String name) {
        return this.movieList.stream().filter(e -> e.getMovieName().equals(name)).findFirst();
    }


    public Optional<List<Movie>> getMovieByYear(String year) {
        return Optional.of(this.movieList.stream().filter(e -> e.getYear().equals(year)).toList());
    }

    public Optional<Movie> addMovie(Movie movie) {
        Optional<Movie> checkedMovie = this.movieList.stream().filter(e -> e.equals(movie)).findFirst();
        if (checkedMovie.isPresent()) {
            return Optional.empty();
        }

        movie.setMovieID(getId());
        this.movieList.add(movie);
        return Optional.of(movie);

    }

    private static long getId() {
        long toAssign = MovieService.movieId;
        MovieService.movieId = toAssign + 1;

        return toAssign;
    }


    @EventListener(ApplicationReadyEvent.class)
    private void setBootstrapData() {
        Movie bladeRunner = new Movie(1L, "Blade Runner", "2017", "Denis Villeneuve");
        Movie valerian = new Movie(2L, "Valerian i Miasto Tysiąca Planet", "2017", "Luc Besson");
        Movie diuna = new Movie(3L, "Diuna", "2021", "Denis Villeneuve");
        Movie greenMile = new Movie(4L, "Zielona mila", "1999", "Frank Darabont");
        Movie spiderManUniversum = new Movie(5L, "Spider-Man Uniwersum", "2018", "Bob Persichetti, Peter Ramsey");
        this.movieList = Arrays.asList(bladeRunner, valerian, diuna, greenMile, spiderManUniversum);

    }
}
