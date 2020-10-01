import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;

public class Application {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie movie = new Movie();
        movie.setTitle("Fast & Furious");
        movie.setDescription("action");
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);
    }
}
