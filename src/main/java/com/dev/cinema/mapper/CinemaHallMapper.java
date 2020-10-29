package com.dev.cinema.mapper;

import com.dev.cinema.dto.CinemaHallRequestDto;
import com.dev.cinema.dto.CinemaHallResponseDto;
import com.dev.cinema.model.CinemaHall;
import org.springframework.stereotype.Component;

@Component
public class CinemaHallMapper {
    public CinemaHall convertDtoToCinemaHall(CinemaHallRequestDto cinemaHallDto) {
        return new CinemaHall(cinemaHallDto.getCapacity(), cinemaHallDto.getDescription());
    }

    public CinemaHallResponseDto convertCinemaHallToDto(CinemaHall cinemaHall) {
        CinemaHallResponseDto cinemaHallDto = new CinemaHallResponseDto();
        cinemaHallDto.setId(cinemaHall.getId());
        cinemaHallDto.setCapacity(cinemaHall.getCapacity());
        cinemaHallDto.setDescription(cinemaHall.getDescription());
        return cinemaHallDto;
    }
}
