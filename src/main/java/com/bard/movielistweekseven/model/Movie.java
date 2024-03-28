package com.bard.movielistweekseven.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Objects;


@AllArgsConstructor
@Data
public class Movie {



    private long movieID;
    @NotBlank
    @Size(min = 1, max = 255)
    private String movieName;

    @NotBlank
    @Size(min = 4,max = 4)
    private String year;

    @NotBlank
    @Size(min = 1,max = 50)
    private String director;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(movieName, movie.movieName) && Objects.equals(year, movie.year) && Objects.equals(director, movie.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieName, year, director);
    }
}
