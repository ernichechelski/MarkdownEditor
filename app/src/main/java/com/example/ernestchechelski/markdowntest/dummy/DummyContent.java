package com.example.ernestchechelski.markdowntest.dummy;

import com.example.ernestchechelski.markdowntest.notes.FakeNotesRepository;
import com.example.ernestchechelski.markdowntest.notes.NotesRepository;
import com.example.ernestchechelski.markdowntest.notes.SugarNotesRepository;
import com.example.ernestchechelski.markdowntest.notes.domain.Note;
import com.example.ernestchechelski.markdowntest.notes.domain.NotesRepositoryFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Note> ITEMS = new ArrayList<Note>();

    static {
        // Add some sample items.
        NotesRepository repository = NotesRepositoryFactory.getNotesRepository();
        ITEMS.addAll(repository.getNotes());
    }

}
