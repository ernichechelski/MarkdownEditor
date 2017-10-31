package com.example.ernestchechelski.markdowntest.notes.domain;

import com.example.ernestchechelski.markdowntest.notes.FakeNotesRepository;
import com.example.ernestchechelski.markdowntest.notes.NotesRepository;

/**
 * Created by ernest.chechelski on 10/31/2017.
 */

public class NotesRepositoryFactory {
    public static NotesRepository getNotesRepository(){
        return new FakeNotesRepository();
    }
}
