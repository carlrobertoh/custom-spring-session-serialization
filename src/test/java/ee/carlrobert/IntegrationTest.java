package ee.carlrobert;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import ee.carlrobert.mixin.ApiClientTestMixin;
import ee.carlrobert.mixin.AssertionTestMixin;
import ee.carlrobert.mixin.DatabaseTestMixin;
import ee.carlrobert.mixin.AuthenticationMixin;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
abstract class IntegrationTest implements
    ApiClientTestMixin,
    AssertionTestMixin,
    DatabaseTestMixin,
    AuthenticationMixin {

  @BeforeEach
  void beforeEachTest() {
    cleanupDatabase("note", "spring_session", "spring_session_attributes");
  }
}
