package ee.carlrobert.note;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "note")
class NoteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String author;
  private String message;

  Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  String getAuthor() {
    return author;
  }

  void setAuthor(String author) {
    this.author = author;
  }

  String getMessage() {
    return message;
  }

  void setMessage(String message) {
    this.message = message;
  }
}
