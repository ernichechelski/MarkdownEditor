package com.example.ernestchechelski.markdowntest;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import com.example.ernestchechelski.markdowntest.barAction.BarAction;
import com.example.ernestchechelski.markdowntest.barAction.BarActionAdapter;
import com.example.ernestchechelski.markdowntest.customQuote.CustomQuoteBook;
import com.example.ernestchechelski.markdowntest.customQuote.CustomQuoteBooks;
import com.example.ernestchechelski.markdowntest.customQuote.CustomQuoteExtension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = "EditorActivity";

    WebView webView;
    EditText editText;
    Parser parser;
    HtmlRenderer renderer;

    private RecyclerView recyclerView;
    private BarActionAdapter mAdapter;
    private List<BarAction> rawTags = new ArrayList<>();
    private List<BarAction> autoTags = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setUI();
        setMarkdown();
        loadRawTags();
        refresh();
        //loadTestData();
        //loadTestAsset();
        loadTestVerse();

    }

    private void loadTestAsset() {
        try {
            Log.d(TAG,"Loading assets");
            AssetManager mgr = getBaseContext().getAssets();
            String htmlContentInStringFormat;
            String htmlFilename = "Bible/1001061105.xhtml";
            InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
            htmlContentInStringFormat = StreamToString(in);
            Log.d(TAG,"String loaded"+htmlContentInStringFormat);
            Document document = Jsoup.parse(htmlContentInStringFormat);
            Elements elements = document.select("#p2");
            Log.d(TAG,"Element with content loaded" + elements.html());
            loadHTML(elements.html());

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
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
    private void setMarkdown() {
        Log.v(TAG,"setMarkdown()");
        MutableDataSet options = new MutableDataSet()
                .set(Parser.REFERENCES_KEEP, KeepType.LAST)
                .set(HtmlRenderer.INDENT_SIZE, 2)
                .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
                .set(CustomQuoteExtension.USE_IMAGE_URLS,true)
                .set(Parser.EXTENSIONS, Arrays.asList(CustomQuoteExtension.create(this,"Bible")));

        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
        // uncomment to set optional extensions
        //options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

    }

    private void setUI() {
        Log.d(TAG,"setUI()");
        setContentView(R.layout.activity_main);
        this.setTitle("Markdown Editor");
        webView = (WebView) this.findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);
        editText = (EditText) this.findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.buttons_recycler_view);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        mAdapter = new BarActionAdapter(rawTags);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        webView.getSettings().setJavaScriptEnabled(true);
        //editText.setText("This is *Sparta*");
        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                refresh();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
        });
    }

    private void loadRawTags() {

        BarAction.parentAdapter = mAdapter;
        Log.d(TAG,"loadRawTags()");
        BarAction barAction = new BarAction("M");
        barAction.addChild(new BarAction("#", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("#");
            }
        }));
        barAction.addChild(new BarAction("*", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("*");
            }
        }));

        barAction.addChild(new BarAction("_", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("_");
            }
        }));
        barAction.addChild(new BarAction("[]()", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selection = editText.getSelectionStart();
                insertTextInSelection("[]()");
                editText.setSelection(selection + 1);

            }
        }));




        BarAction booksBarAction = new BarAction("B");
        BarAction genesisBarAction= new BarAction("Ge");
        final CustomQuoteBook book = CustomQuoteBooks.books.get("Ge");
        for(int x=0; x<10;x++){

            BarAction chapterBarAction= new BarAction(x+"");
            for(int y=0; y<10;y++){
                final int finalX = x;
                final int finalY = y;
                BarAction verseBarAction = new BarAction(y + "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        insertTextInSelection("|Ge" + finalX +":"+ finalY +"|");
                    }
                });
                chapterBarAction.addChild(verseBarAction);
            }
            genesisBarAction.addChild(chapterBarAction);
        }


        booksBarAction.addChild(genesisBarAction);

        mAdapter.addAction(booksBarAction);
        mAdapter.addAction(barAction);

    }




    private void insertTextInSelection(String textToInsert) {
        insertTextInSelection(textToInsert,0);
    }
    private void insertTextInSelection(String textToInsert,Integer shift) {
        int start = Math.max(editText.getSelectionStart()+shift, 0);
        int end = Math.max(editText.getSelectionEnd()+shift, 0);
        editText.getText().replace(Math.min(start, end), Math.max(start, end),
                textToInsert, 0, textToInsert.length());
    }


    private void refresh(){
        Log.d(TAG, "refresh()");
        Node document = parser.parse(editText.getText().toString());
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        loadHTML(html);
        System.out.println(html);
        Log.d(TAG, "refresh()=" + html);
    }

    private void loadHTML(String html) {
        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
    }

    private void loadTestData(){
        editText.setText("An h1 header\n" +
                "============\n" +
                "\n" +
                "Paragraphs are separated by a blank line.\n" +
                "\n" +
                "2nd paragraph. *Italic*, **bold**, and `monospace`. Itemized lists\n" +
                "look like:\n" +
                "\n" +
                "  * this one\n" +
                "  * that one\n" +
                "  * the other one\n" +
                "\n" +
                "Note that --- not considering the asterisk --- the actual text\n" +
                "content starts at 4-columns in.\n" +
                "\n" +
                "> Block quotes are\n" +
                "> written like so.\n" +
                ">\n" +
                "> They can span multiple paragraphs,\n" +
                "> if you like.\n" +
                "\n" +
                "Use 3 dashes for an em-dash. Use 2 dashes for ranges (ex., \"it's all\n" +
                "in chapters 12--14\"). Three dots ... will be converted to an ellipsis.\n" +
                "Unicode is supported. ☺\n" +
                "\n");refresh();
    }

    private void loadTestVerse(){
        editText.setText("|Ge1:1|");refresh();
    }




}