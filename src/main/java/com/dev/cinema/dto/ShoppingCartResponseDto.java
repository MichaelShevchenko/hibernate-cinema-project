package com.dev.cinema.dto;

import java.util.List;

public class ShoppingCartResponseDto {
    private Long id;
    private List<TicketResponseDto> tickets;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return "ShoppingCartResponseDto{" + "tickets=" + tickets + ", userId=" + userId + '}';
    }
}
