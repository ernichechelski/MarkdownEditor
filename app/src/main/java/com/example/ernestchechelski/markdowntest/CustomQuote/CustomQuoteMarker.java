package com.example.ernestchechelski.markdowntest.CustomQuote;

/**
 * Created by ernest.chechelski on 8/22/2017.
 */

public class CustomQuoteMarker {
    public Integer chapter, verse;

    public CustomQuoteMarker(Integer chapter, Integer verse) {
        this.chapter = chapter;
        this.verse = verse;
    }

    public Integer getChapter() {
        return chapter;
    }

    public Integer getVerse() {
        return verse;
    }
}
