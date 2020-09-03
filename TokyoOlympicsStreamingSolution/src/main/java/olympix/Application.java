package olympix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    static boolean loggedIn = false;
    static String username = null;
    static String errorMessage = null;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
