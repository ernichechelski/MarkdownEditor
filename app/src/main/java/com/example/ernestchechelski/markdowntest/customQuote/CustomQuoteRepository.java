package com.example.ernestchechelski.markdowntest.customQuote;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

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
 *
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
            Log.e(TAG,"Error with getHtmlStringByParsedString ",e);
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
            Log.v(TAG,"getHtmlString()");
            Log.d(TAG,"Getting file");
            AssetManager mgr = context.getAssets();
            String htmlContentInStringFormat;
            String htmlFilename = resourceCatalogName+"/"+CustomQuoteBooks.getBookByCode(bookCode).getFullChapterCode(chapter)+".xhtml";
            InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
            htmlContentInStringFormat = StreamToString(in);
            Log.d(TAG,"Content of file loaded"+htmlContentInStringFormat);
            Log.v(TAG,"with value: "+htmlContentInStringFormat);

            Log.d(TAG,"Parsing content");
            Document document = Jsoup.parse(htmlContentInStringFormat);

            Log.d(TAG,"Cleaning parsed content");
            document.getElementsByTag("head").remove();
            document.getElementsByTag("a").remove();
            document.getElementsByTag("strong").remove();
            document.getElementsByTag("span").select(".w_ch").remove();
            for (Element e: document.getElementsByTag("span")){
                if(e.id().contains("footnotesource")){
                    e.remove();
                }
            }

            Log.v(TAG,"Content after cleanup: "+document.toString());
            String id = "chapter"+chapter+ "_verse"+verse;
            Log.d(TAG,"Generated selector:"+id);
            Log.d(TAG,"Retrieving element");

            Element e = document.getElementById(id);
            String result= "";
            Node parent = e.parent();
            int childsCount = parent.childNodes().size();
            for(int x = e.siblingIndex()+1 ; x<childsCount;x++){
                Node node =  parent.childNode(x);
                if (node instanceof TextNode){
                    TextNode textNode = (TextNode) node;
                    result += textNode.text();
                }
                else {
                    String nodeId = node.attr("id");
                    if(nodeId.contains("chapter")){
                        break;
                    }
                }
            }
            Log.d(TAG,"Result string " + result);
            in.close();
            return result;

        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
            Log.e(TAG,"Error with parsing",e);
            return null;
        }
    }

   private static String StreamToString(InputStream in) throws IOException {
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
