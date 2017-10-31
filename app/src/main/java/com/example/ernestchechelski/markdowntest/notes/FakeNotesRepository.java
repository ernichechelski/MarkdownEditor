package com.example.ernestchechelski.markdowntest.notes;

import com.example.ernestchechelski.markdowntest.notes.domain.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ErnestChechelski on 09.09.2017.
 */

public class FakeNotesRepository implements NotesRepository {


    static List<Note> notes = new ArrayList<>();
    static Long idCounter = 0L;
    static {
        Note note = new Note("Example first note", "Example first text");
        note.setId(idCounter);
        notes.add(note);
        idCounter++;

        Note note2 = new Note("Example second note", "Example second text");
        note2.setId(idCounter);
        notes.add(note2);
        idCounter++;
    }


    @Override
    public Note getNote(Long id) {
        for(Note n:notes) {
            if(n.getId().equals(id)){
                return n;
            }
        }

       return getSampleNote();
    }

    @Override
    public Note createBlankNote() {
        return new Note("","");
    }

    @Override
    public void deleteNote(Note note) {
        notes.remove(note);
    }

    @Override
    public void saveNote(Note note) {
        note.setId(idCounter);
        notes.add(note);
    }

    @Override
    public List<Note> getNotes() {
       return notes;
    }

    private Note getSampleNote(){
        Note note = new Note("Sample note", "Sample text");
        note.setId(idCounter);
        return note;
    }

}
