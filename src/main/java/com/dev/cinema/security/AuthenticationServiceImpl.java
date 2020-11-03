package com.dev.cinema.security;

import com.dev.cinema.model.User;
import com.dev.cinema.service.RoleService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = Logger.getLogger(AuthenticationServiceImpl.class);
    private final UserService userService;
    private final RoleService roleService;
    private final ShoppingCartService cartService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     RoleService roleService,
                                     ShoppingCartService cartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.cartService = cartService;
    }

    @Override
    public User register(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRoles(Set.of(roleService.getRoleByName("USER")));
        userService.add(newUser);
        cartService.registerNewShoppingCart(newUser);
        logger.info("Registered a new user with email: " + email + " and added shopping cart");
        return newUser;
    }
}
