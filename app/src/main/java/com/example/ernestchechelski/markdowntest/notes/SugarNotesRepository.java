package com.example.ernestchechelski.markdowntest.notes;

import android.util.Log;

import com.example.ernestchechelski.markdowntest.notes.domain.Note;

import java.util.List;

/**
 * Created by ErnestChechelski on 09.09.2017.
 */

public class SugarNotesRepository implements NotesRepository {
    public static String TAG = SugarNotesRepository.class.getSimpleName();
    @Override
    public Note getNote(Long id) {
        return Note.findById(Note.class, id);
    }

    @Override
    public Note createBlankNote() {
        Note book = new Note("","");
        book.save();
        return book;
    }

    @Override
    public void deleteNote(Note note) {
        Note book = Note.findById(Note.class, note.getId());
        book.delete();
    }

    @Override
    public void saveNote(Note note) {
        Log.d(TAG,"Saving note with content"+note.getContent());
        note.save();
    }

    @Override
    public List<Note> getNotes() {
        return Note.listAll(Note.class);
    }
}
