package com.dev.cinema.dto;

import javax.validation.constraints.NotNull;

public class MovieSessionRequestDto {
    @NotNull(message = "MovieID should not be null")
    private Long movieId;
    @NotNull(message = "CinemaHallID should not be null")
    private Long cinemaHallId;
    @NotNull(message = "ShowTime should not be null")
    private String showTime;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getCinemaHallId() {
        return cinemaHallId;
    }

    public void setCinemaHallId(Long cinemaHallId) {
        this.cinemaHallId = cinemaHallId;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    @Override
    public String toString() {
        return "MovieSession{" + "movie=" + movieId + ", cinemaHall="
                + cinemaHallId + ", showTime=" + showTime + '}';
    }
}
