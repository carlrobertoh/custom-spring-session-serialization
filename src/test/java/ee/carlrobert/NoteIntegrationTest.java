package ee.carlrobert;

import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

class NoteIntegrationTest extends IntegrationTest {

  @Test
  void shouldFindAllNotes() {
    var session = authenticateAndGetSession();
    createNote(1L, "TEST_AUTHOR_1", "TEST_MESSAGE_1");
    createNote(2L, "TEST_AUTHOR_2", "TEST_MESSAGE_2");

    var response = get("/v1/notes", getSessionHeaders(session));

    assertJsonArray(response)
        .extracting("author", "message")
        .containsExactly(
            tuple("TEST_AUTHOR_1", "TEST_MESSAGE_1"),
            tuple("TEST_AUTHOR_2", "TEST_MESSAGE_2"));
  }

  @Test
  void shouldThrowExceptionWhenUnauthorized() {
    getExpectingStatus("/v1/notes", HttpStatus.UNAUTHORIZED);
  }

  private void createNote(Long id, String author, String message) {
    executeSql(template -> template.update("""
        INSERT INTO note(id, author, message)
        VALUES (?, ?, ?)""", id, author, message));
  }

  private HttpHeaders getSessionHeaders(String session) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("cookie", session);
    return headers;
  }
}
