import org.apache.log4j.Logger;

public class Application {
    private static final Logger LOGGER = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("This is debug message. Web server is up, application started.");
        }
        System.out.println("Hello world");
    }
}
