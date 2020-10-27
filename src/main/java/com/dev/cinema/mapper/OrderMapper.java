package com.dev.cinema.mapper;

import com.dev.cinema.dto.OrderResponseDto;
import com.dev.cinema.model.Order;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final TicketMapper ticketMapper;

    public OrderMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public OrderResponseDto convertOrderToDto(Order order) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setOrderDate(order.getOrderDate().format(DateTimeFormatter.ISO_DATE_TIME));
        orderDto.setTickets(order.getTickets()
                .stream()
                .map(ticketMapper::convertTicketToDto)
                .collect(Collectors.toList()));
        return orderDto;
    }
}
