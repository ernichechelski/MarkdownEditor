package com.example.ernestchechelski.markdowntest.notes;

import com.example.ernestchechelski.markdowntest.notes.domain.Note;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ErnestChechelski on 09.09.2017.
 */

public class FakeNotesRepository implements NotesRepository {
    @Override
    public Note getNote(Long id) {
       return new Note("Example note","Example text");
    }

    @Override
    public Note createBlankNote() {
        return new Note("","");
    }

    @Override
    public void deleteNote(Note note) {

    }

    @Override
    public void saveNote(Note note) {

    }

    @Override
    public List<Note> getNotes() {
       return Arrays.asList(new Note("Example first note","Example first text"),new Note("Example second note","Example second text"));
    }
}
