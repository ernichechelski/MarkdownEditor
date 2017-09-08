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
 * Created by ernest.chechelski on 9/8/2017.
 */

public class CustomQuoteRepository {

    public static String TAG = CustomQuoteRepository.class.getName();

    Context context;

    public CustomQuoteRepository(Context context) {
        this.context = context;
    }

    public String getHtmlString(String bookCode,Integer chapter, Integer verse){

        try {
            Log.d(TAG,"Loading assets");
            AssetManager mgr = context.getAssets();
            String htmlContentInStringFormat;
            String htmlFilename = "TestBible/"+CustomQuoteBooks.getBookByCode(bookCode).getFullChapterCode(chapter)+".xhtml";
            InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
            htmlContentInStringFormat = StreamToString(in);
            Log.d(TAG,"String loaded"+htmlContentInStringFormat);
            Document document = Jsoup.parse(htmlContentInStringFormat);
            //Clean doc from links
            document.select("a").remove();

            String id = "chapter"+chapter+ "_verse"+verse;
            Log.d(TAG,"Selector generated:"+id);

            Element elements =  document.getElementById(id).parent();
            Log.d(TAG,"Element with content loaded" + elements.outerHtml());
            in.close();
            return elements.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

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
