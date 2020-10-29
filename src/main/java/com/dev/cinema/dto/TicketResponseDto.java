package com.dev.cinema.dto;

public class TicketResponseDto {
    private Long id;
    private String movieTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Override
    public String toString() {
        return "TicketResponseDto{" + "movieTitle='" + movieTitle + "'}";
    }
}
