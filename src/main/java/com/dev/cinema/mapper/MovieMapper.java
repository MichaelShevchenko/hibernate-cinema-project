package com.dev.cinema.mapper;

import com.dev.cinema.dto.MovieRequestDto;
import com.dev.cinema.dto.MovieResponseDto;
import com.dev.cinema.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public Movie convertDtoToMovie(MovieRequestDto movieDto) {
        return new Movie(movieDto.getTitle(), movieDto.getDescription());
    }

    public MovieResponseDto convertMovieToDto(Movie movie) {
        MovieResponseDto movieDto = new MovieResponseDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDescription(movie.getDescription());
        return movieDto;
    }
}
