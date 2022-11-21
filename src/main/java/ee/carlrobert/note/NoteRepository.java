package ee.carlrobert.note;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface NoteRepository extends JpaRepository<NoteEntity, Long> {

  List<Note> findAllProjectedBy();
}
