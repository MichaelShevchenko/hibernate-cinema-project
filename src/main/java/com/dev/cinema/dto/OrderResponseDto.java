package com.dev.cinema.dto;

import java.util.List;

public class OrderResponseDto {
    private Long id;
    private String orderDate;
    private Long userId;
    private List<TicketResponseDto> tickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<TicketResponseDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResponseDto> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "OrderResponseDto{" + "orderDate=" + orderDate + ", userId=" + userId
                + ", tickets=" + tickets + '}';
    }
}
