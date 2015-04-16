package eu.javablog.example.api;

import eu.javablog.example.domain.Note;
import eu.javablog.example.services.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class NoteResource {

    private NoteRepository noteRepository;

    @Autowired
    public NoteResource(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @RequestMapping("/notes/")
    public List<Note> getNotes() {
        return this.noteRepository.findAll();
    }

    @RequestMapping(value = "/notes/", method = RequestMethod.POST)
    public ResponseEntity<Note> addNote(@RequestBody String content) {
        return new ResponseEntity<>(this.noteRepository.save(new Note(content)), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/notes/{id}/", method = RequestMethod.DELETE)
    public ResponseEntity removeNote(@PathVariable("id") Long noteId) {
        this.noteRepository.delete(noteId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
