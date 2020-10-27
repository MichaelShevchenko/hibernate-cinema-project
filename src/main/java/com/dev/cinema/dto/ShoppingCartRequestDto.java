package com.dev.cinema.dto;

import java.util.List;

public class ShoppingCartRequestDto {
    private List<TicketResponseDto> tickets;
    private Long userId;

    public List<TicketResponseDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResponseDto> tickets) {
        this.tickets = tickets;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShoppingCartRequestDto{" + "tickets=" + tickets + ", userId=" + userId + '}';
    }
}
