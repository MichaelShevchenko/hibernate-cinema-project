package com.dev.cinema.mapper;

import com.dev.cinema.dto.ShoppingCartResponseDto;
import com.dev.cinema.model.ShoppingCart;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {
    private final TicketMapper ticketMapper;

    public ShoppingCartMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public ShoppingCartResponseDto convertShoppingCartToDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto shoppingCartDto = new ShoppingCartResponseDto();
        shoppingCartDto.setId(shoppingCart.getId());
        shoppingCartDto.setUserId(shoppingCart.getUser().getId());
        shoppingCartDto.setTickets(shoppingCart.getTickets()
                .stream()
                .map(ticketMapper::convertTicketToDto)
                .collect(Collectors.toList()));
        return shoppingCartDto;
    }
}
