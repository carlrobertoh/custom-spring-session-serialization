package ee.carlrobert.configuration;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        .authorizeRequests(authorizeRequests ->
            authorizeRequests.antMatchers("/swagger-ui/*", "/docs/json/*", "/docs/json", "/docs")
                .permitAll()
                .anyRequest()
                .authenticated())
        .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
        .exceptionHandling(exceptionHandling ->
            exceptionHandling.authenticationEntryPoint((req, resp, ex) ->
                resp.sendError(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase())))
        .build();
  }
}
