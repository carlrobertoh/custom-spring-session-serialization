package ee.carlrobert.web;

import ee.carlrobert.note.Note;
import ee.carlrobert.note.NoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Note")
class NoteController {

  private final NoteService noteService;

  NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  @GetMapping("v1/notes")
  public ResponseEntity<List<Note>> findAll() {
    return new ResponseEntity<>(noteService.findAll(), HttpStatus.OK);
  }
}
