package com.dev.cinema.controller;

import com.dev.cinema.dto.MovieRequestDto;
import com.dev.cinema.dto.MovieResponseDto;
import com.dev.cinema.mapper.MovieMapper;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @GetMapping("/inject")
    public String inject() {
        movieService.add(new Movie("Fast & Furious", "action"));
        movieService.add(new Movie("Matrix", "Science fiction"));
        movieService.add(new Movie("Fearless", "Martial arts"));
        return "Data added successfully";
    }

    @PostMapping
    public void add(@RequestBody MovieRequestDto movieDto) {
        movieService.add(movieMapper.convertDtoToMovie(movieDto));
    }

    @GetMapping
    public List<MovieResponseDto> getAll() {
        return movieService.getAll().stream()
                .map(movieMapper::convertMovieToDto)
                .collect(Collectors.toList());
    }
}
