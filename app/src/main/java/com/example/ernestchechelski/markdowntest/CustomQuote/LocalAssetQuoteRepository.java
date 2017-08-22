package com.example.ernestchechelski.markdowntest.CustomQuote;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by ernest.chechelski on 8/22/2017.
 */

public class LocalAssetQuoteRepository implements CustomQuoteRepository {
    private static final String TAG = "LocalAsQtRepo";

    private Document htmlDocument;
    private Context context;


    public LocalAssetQuoteRepository(Context context){
        this.context = context;
    }

    @Override
    public Element getQuote(String bookCode, CustomQuoteMarker start, CustomQuoteMarker end) {
        Element el = null;
        try {
            Log.d(TAG,"Loading assets");
            AssetManager mgr = context.getAssets();
            String htmlContentInStringFormat;
            String htmlFilename = "TestBible/1001061105.xhtml";
            InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
            htmlContentInStringFormat = StreamToString(in);
            Log.d(TAG,"String loaded"+htmlContentInStringFormat);
            Document document = Jsoup.parse(htmlContentInStringFormat);
            Elements elements = document.select("#p2");
            Log.d(TAG,"Element with content loaded" + elements.html());
            el = new Element(elements.html());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  el;
    }

    public static String StreamToString(InputStream in) throws IOException {
        Log.d(TAG,"StreamToString");
        if(in == null) {
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
        }
        return writer.toString();
    }
}
