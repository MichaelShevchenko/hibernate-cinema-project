package com.dev.cinema.security;

import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.HashUtil;
import java.util.Optional;
import org.apache.log4j.Logger;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationServiceImpl.class);
    @Inject
    private UserService userService;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && isPasswordValid(password, userFromDB.get())) {
            LOGGER.info("A user with email: " + email + " has been logged in");
            return userFromDB.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }

    @Override
    public User register(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        userService.add(newUser);
        shoppingCartService.registerNewShoppingCart(newUser);
        LOGGER.info("Registered a new user with email: " + email + " and added shopping cart");
        return newUser;
    }

    private boolean isPasswordValid(String password, User userFromDbPassword) {
        return HashUtil.hashPassword(password, userFromDbPassword.getSalt())
                .equals(userFromDbPassword.getPassword());
    }
}
