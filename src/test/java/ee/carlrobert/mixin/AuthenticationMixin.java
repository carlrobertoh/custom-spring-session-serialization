package ee.carlrobert.mixin;

import static ee.carlrobert.mixin.ApiClientTestMixin.Private.testRestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public interface AuthenticationMixin extends ApiClientTestMixin {

  class Private {
    static InMemoryUserDetailsManager inMemoryUserDetailsManager;
  }

  default String authenticateAndGetSession() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("username", "user");
    map.add("password", getGeneratedPassword());

    return testRestTemplate.exchange(
            "/login",
            HttpMethod.POST,
            new HttpEntity<>(map, headers),
            String.class)
        .getHeaders()
        .getFirst("Set-Cookie");
  }

  private String getGeneratedPassword() {
    return Private.inMemoryUserDetailsManager
        .loadUserByUsername("user")
        .getPassword().replaceAll("\\{noop}", "");
  }

  @Autowired
  default void setDependencies(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
    Private.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
  }
}
