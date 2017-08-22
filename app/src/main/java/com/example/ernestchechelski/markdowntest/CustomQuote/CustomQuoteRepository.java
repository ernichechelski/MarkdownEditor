package com.example.ernestchechelski.markdowntest.CustomQuote;

import android.content.Context;

import org.jsoup.nodes.Element;

/**
 * Created by ernest.chechelski on 8/22/2017.
 */

public interface CustomQuoteRepository {

    public Element getQuote(String bookCode, CustomQuoteMarker start, CustomQuoteMarker end);

}
