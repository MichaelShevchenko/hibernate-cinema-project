import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Application {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) throws AuthenticationException {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie movie1 = new Movie();
        movie1.setTitle("Fast & Furious");
        movie1.setDescription("action");
        movieService.add(movie1);
        movieService.getAll().forEach(System.out::println);
        Movie movie2 = new Movie();
        movie2.setTitle("Matrix");
        movie2.setDescription("Science fiction");
        movieService.add(movie2);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                                            .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("normal");
        cinemaHallService.add(cinemaHall);
        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(35);
        cinemaHall1.setDescription("luxury");
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.getAll().forEach(System.out::println);

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
        movieSessionService.findAvailableSessions(2L, LocalDate.now()).forEach(System.out::println);

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
        System.out.println(userService.findByEmail("diagram@mit.com"));

        AuthenticationService authenticationService = (AuthenticationService) injector
                                                    .getInstance(AuthenticationService.class);
        authenticationService.register("ranger@gmail.com", "M@rtia1Arts");
        System.out.println(authenticationService.login("texas.ranger@gmail.com", "M@rtia1Arts"));
    }
}
