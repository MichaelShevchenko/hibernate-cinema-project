package com.dev.cinema.service;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Ticket> tickets, User user) {
        shoppingCartService.clear(shoppingCartService.getByUser(user));
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setTickets(new ArrayList<>(tickets));
        newOrder.setOrderDate(LocalDateTime.now());
        return orderDao.add(newOrder);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getOrderHistory(user);
    }
}
