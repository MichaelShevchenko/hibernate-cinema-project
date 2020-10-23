package com.dev.cinema.mapper;

import com.dev.cinema.dto.MovieSessionRequestDto;
import com.dev.cinema.dto.MovieSessionResponseDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionMapper {
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;

    @Autowired
    public MovieSessionMapper(MovieService movieService, CinemaHallService cinemaHallService) {
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    public MovieSession convertDtoToMovieSession(MovieSessionRequestDto movieSessionDto) {
        MovieSession newMovieSession = new MovieSession();
        newMovieSession.setMovie(movieService.getById(movieSessionDto.getMovieId()));
        newMovieSession.setCinemaHall(cinemaHallService.getById(movieSessionDto.getCinemaHallId()));
        newMovieSession.setShowTime(LocalDateTime.parse(movieSessionDto.getShowTime(),
                DateTimeFormatter.BASIC_ISO_DATE));
        return newMovieSession;
    }

    public MovieSessionResponseDto convertMovieSessionToDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setId(movieSession.getId());
        movieSessionResponseDto.setMovieTitle(movieSession.getMovie().getTitle());
        movieSessionResponseDto.setCinemaHallId(movieSession.getCinemaHall().getId());
        movieSessionResponseDto.setShowTime(
                movieSession.getShowTime().format(DateTimeFormatter.ISO_DATE_TIME));
        return movieSessionResponseDto;
    }
}
