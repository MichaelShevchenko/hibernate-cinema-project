import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.apache.log4j.Logger;

public class Application {
    private static Injector injector = Injector.getInstance("com.dev.cinema");
    private static final Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args) throws AuthenticationException {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie movie1 = new Movie();
        movie1.setTitle("Fast & Furious");
        movie1.setDescription("action");
        movieService.add(movie1);
        Movie movie2 = new Movie();
        movie2.setTitle("Matrix");
        movie2.setDescription("Science fiction");
        movieService.add(movie2);
        movieService.getAll().forEach(movie -> logger.info(movie));

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("normal");
        cinemaHallService.add(cinemaHall);
        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(35);
        cinemaHall1.setDescription("luxury");
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.getAll().forEach(hall -> logger.info(hall));

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie2);
        movieSession.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 20)));
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setMovie(movie1);
        movieSession2.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 55)));
        MovieSession movieSession3 = new MovieSession();
        movieSession3.setCinemaHall(cinemaHall1);
        movieSession3.setMovie(movie2);
        movieSession3.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 20)));
        MovieSession movieSession4 = new MovieSession();
        movieSession4.setCinemaHall(cinemaHall);
        movieSession4.setMovie(movie2);
        movieSession4.setShowTime(LocalDateTime.of(LocalDate.now().plusDays(1),
                                                    LocalTime.of(0, 0)));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        movieSessionService.findAvailableSessions(2L, LocalDate.now())
                .forEach(session -> logger.info(session));

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User visitor = new User();
        visitor.setEmail("jackie@yandex.com");
        visitor.setPassword("p@ssw0rd");
        userService.add(visitor);
        User buyer = new User();
        buyer.setEmail("texas.ranger@gmail.com");
        buyer.setPassword("M@rtia1Arts");
        userService.add(buyer);
        User explorer = new User();
        explorer.setEmail("diagram@mit.com");
        explorer.setPassword("N0be1Prize");
        userService.add(explorer);
        logger.info("Attempt to find user with email diagram@mit.com"
                + userService.findByEmail("diagram@mit.com"));

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        try {
            logger.info("Expected to register a new user with ranger@gmail.com email:\n "
                    + authenticationService.register("ranger@gmail.com", "M@rtia1Arts"));
        } catch (Exception e) {
            logger.warn("Registration new user with email ranger@gmail.com failed: " + e);
        }
        try {
            logger.info("Attempt to login for texas.ranger@gmail.com email "
                    + authenticationService.login("texas.ranger@gmail.com", "M@rtia1Arts"));
        } catch (Exception e) {
            logger.warn("Login user with texas.ranger@gmail.com email "
                    + "and M@rtia1Arts password failed: " + e);
        }

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        User testUser = userService.findByEmail("ranger@gmail.com").get();
        shoppingCartService.addSession(movieSession2, testUser);
        shoppingCartService.addSession(movieSession4, testUser);
        shoppingCartService.addSession(movieSession2, testUser);
        logger.info("checking shopping cart availability after registration for testUser: "
                + shoppingCartService.getByUser(testUser));
        shoppingCartService.clear(shoppingCartService.getByUser(testUser));
        logger.info("checking shopping cart for emptiness after it was cleared: "
                + shoppingCartService.getByUser(testUser));

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        shoppingCartService.registerNewShoppingCart(visitor);
        shoppingCartService.addSession(movieSession3, visitor);
        orderService.completeOrder(shoppingCartService.getByUser(visitor).getTickets(), visitor);
        shoppingCartService.addSession(movieSession, visitor);
        shoppingCartService.addSession(movieSession4, visitor);
        orderService.completeOrder(shoppingCartService.getByUser(visitor).getTickets(), visitor);
        List<Order> ordersHistory = orderService.getOrderHistory(visitor);
        logger.info("checking orders history for visitor user: ");
        for (Order singleOrder: ordersHistory) {
            logger.info(singleOrder);
        }
    }
}
