package eu.javablog.example;

import eu.javablog.example.domain.Note;
import eu.javablog.example.services.NoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest({"server.port=9999"})
public class SimpleIntegTest {
    private RestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private NoteRepository noteRepository;

    @Before
    public void setUp() throws Exception {
        this.noteRepository.deleteAll();
        this.noteRepository.save(new Note("Test Content 1"));
        this.noteRepository.save(new Note("Test Content 2"));
    }

    @Test
    public void shouldReturnAllNotes() throws Exception {
        List<Note> notes = restTemplate.exchange(
                "http://localhost:9999/api/notes/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {}).getBody();

        assertThat(notes, hasSize(2));
        assertThat(notes.get(0).getContent(), is("Test Content 1"));
        assertThat(notes.get(1).getContent(), is("Test Content 2"));
    }

    @Test
    public void shouldAddNewNote() throws Exception {
        Note newNote = restTemplate.exchange(
                "http://localhost:9999/api/notes/",
                HttpMethod.POST,
                new HttpEntity<>("Test Content 3"),
                Note.class).getBody();

        assertThat(newNote.getContent(), is("Test Content 3"));
    }
}


