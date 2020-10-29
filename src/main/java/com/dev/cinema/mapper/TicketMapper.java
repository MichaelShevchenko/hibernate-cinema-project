package com.dev.cinema.mapper;

import com.dev.cinema.dto.TicketResponseDto;
import com.dev.cinema.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketResponseDto convertTicketToDto(Ticket ticket) {
        TicketResponseDto ticketDto = new TicketResponseDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setMovieTitle(ticket.getMovieSession().getMovie().getTitle());
        return ticketDto;
    }
}
