package ee.carlrobert.note;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
class NoteServiceImpl implements NoteService {

  private final NoteRepository noteRepository;

  NoteServiceImpl(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  @Override
  public List<Note> findAll() {
    return noteRepository.findAllProjectedBy();
  }
}


