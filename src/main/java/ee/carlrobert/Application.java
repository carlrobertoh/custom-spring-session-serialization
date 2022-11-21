package ee.carlrobert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ee.carlrobert")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
