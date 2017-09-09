package com.example.ernestchechelski.markdowntest.notes.domain;

import com.orm.SugarRecord;

/**
 * Created by ErnestChechelski on 09.09.2017.
 */

public class Note extends SugarRecord<Note> {

    private String name;
    private String content;

    public Note() {
    }


    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
