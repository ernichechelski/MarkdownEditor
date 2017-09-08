package com.example.ernestchechelski.markdowntest.CustomQuote;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ernest.chechelski on 9/8/2017.
 */

public class CustomQuoteRepository {

    public static String TAG = CustomQuoteRepository.class.getName();

    private String resourceCatalogName;
    private Context context;

    public CustomQuoteRepository(Context context,String resourceCatalogName) {
        this.context = context;
        this.resourceCatalogName = resourceCatalogName;
    }


    public String getHtmlStringByParsedString(String text){

        try{
            String input = text;
            String bookCode = getBookcode(input);
            List<String> params = Arrays.asList(getChapterAndVerseString(input).split(":"));
            Integer chapter = Integer.valueOf(params.get(0));
            Integer verse = Integer.valueOf(params.get(1));
            return getHtmlString(bookCode,chapter,verse);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private String getBookcode(String input) {
        Pattern pattern = Pattern.compile("[A-Za-z]+");

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {     // find the next match
            String match = matcher.group();
            Log.d(TAG,"find() found the pattern \"" + match
                    + "\" starting at index " + matcher.start()
                    + " and ending at index " + matcher.end());
            return match;
        }

        // Use method matches()
        if (matcher.matches()) {
            String match = matcher.group();
            Log.d(TAG,"matches() found the pattern \"" + match
                    + "\" starting at index " + matcher.start()
                    + " and ending at index " + matcher.end());
            return match;
        } else {
            Log.d(TAG,"matches() found nothing");
        }

        // Use method lookingAt()
        if (matcher.lookingAt()) {
            String match = matcher.group();
            Log.d(TAG,"lookingAt() found the pattern \"" + match
                    + "\" starting at index " + matcher.start()
                    + " and ending at index " + matcher.end());
            return match;
        } else {
            Log.d(TAG,"lookingAt() found nothing");
        }            // Use method find()
        return matcher.group();
    }

    private String getChapterAndVerseString(String input) {
        Pattern pattern = Pattern.compile("[\\d]+:[\\d]+");

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {     // find the next match

            String match = matcher.group();
            Log.d(TAG,"find() found the pattern \"" + match
                    + "\" starting at index " + matcher.start()
                    + " and ending at index " + matcher.end());
            return match;
        }

        // Use method matches()
        if (matcher.matches()) {
            String match = matcher.group();
            Log.d(TAG,"matches() found the pattern \"" + match
                    + "\" starting at index " + matcher.start()
                    + " and ending at index " + matcher.end());
            return match;
        } else {
            Log.d(TAG,"matches() found nothing");
        }

        // Use method lookingAt()
        if (matcher.lookingAt()) {
            String match = matcher.group();
            Log.d(TAG,"lookingAt() found the pattern \"" + match
                    + "\" starting at index " + matcher.start()
                    + " and ending at index " + matcher.end());
            return match;
        } else {
            Log.d(TAG,"lookingAt() found nothing");
        }            // Use method find()
        return matcher.group();
    }

    public String getHtmlString(String bookCode,Integer chapter, Integer verse){
        try {
            Log.d(TAG,"Loading assets");
            AssetManager mgr = context.getAssets();
            String htmlContentInStringFormat;
            String htmlFilename = resourceCatalogName+"/"+CustomQuoteBooks.getBookByCode(bookCode).getFullChapterCode(chapter)+".xhtml";
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
            return elements.text().toString();
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
