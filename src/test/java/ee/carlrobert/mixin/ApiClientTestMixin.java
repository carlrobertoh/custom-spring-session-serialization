package ee.carlrobert.mixin;

import static ee.carlrobert.mixin.ApiClientTestMixin.Private.testRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface ApiClientTestMixin {

  class Private {
    static TestRestTemplate testRestTemplate;
  }

  @Autowired
  default void setDependencies(TestRestTemplate testRestTemplate) {
    Private.testRestTemplate = testRestTemplate;
  }

  default <T> T exchange(String url, HttpMethod method, HttpStatus status, HttpEntity<?> entity, Class<T> responseType) {
    var response = testRestTemplate.exchange(url, method, entity, responseType);
    assertThat(response.getStatusCode())
        .withFailMessage("Expecting <%s> to equal <%s>.", response.getStatusCode(), status)
        .isEqualTo(status);
    return response.getBody();
  }

  default String get(String url, HttpHeaders headers) {
    return exchange(url, HttpMethod.GET, HttpStatus.OK, new HttpEntity<>(null, headers), String.class);
  }

  default String getExpectingStatus(String url, HttpStatus status) {
    return exchange(url, HttpMethod.GET, status, null, String.class);
  }
}
