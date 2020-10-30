package com.dev.cinema.controller;

import com.dev.cinema.dto.OrderResponseDto;
import com.dev.cinema.mapper.OrderMapper;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService, OrderMapper orderMapper,
                           UserService userService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/complete")
    public void create(Authentication authentication) {
        User user = getUser(authentication);
        ShoppingCart userCart = shoppingCartService.getByUser(user);
        orderService.completeOrder(userCart.getTickets(), user);
    }

    @GetMapping
    public List<OrderResponseDto> getOrdersHistory(Authentication authentication) {
        return orderService.getOrderHistory(getUser(authentication))
                .stream()
                .map(orderMapper::convertOrderToDto)
                .collect(Collectors.toList());
    }

    private User getUser(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userService.findByEmail(userEmail);
    }
}
