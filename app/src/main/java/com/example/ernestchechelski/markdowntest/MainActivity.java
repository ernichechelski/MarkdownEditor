package com.example.ernestchechelski.markdowntest;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
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

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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
        loadAnotherTags();
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
        Log.d(TAG,"loadRawTags()");

        BarAction barAction = new BarAction("Hashes");



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
        mAdapter.addAction(barAction);


    }

    private void loadAnotherTags() {
        Log.d(TAG,"loadAnotherTags()");

        BarAction barAction = new BarAction("XD");

        for(int x=0;x<10;x++){
            final int finalX = x;
            barAction.addChild(new BarAction(x+"", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertTextInSelection("XD"+ finalX);
                }
            }));
        }
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
    public class BarAction {
        public String name;
        public Boolean expanded;
        public BarActionAdapter parentAdapter;
        public List<BarAction> children;
        public BarAction parent;
        public View.OnClickListener onClickListener;

        public BarAction(String name, View.OnClickListener onClickListener) {
            this.expanded =false;
            this.name = name;
            this.onClickListener = onClickListener;
            children = new ArrayList<>();
        }

        public BarAction(String name) {
            this.expanded =false;
            this.name = name;
            this.onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"Default onClickListener for BarAction:" + BarAction.this.toString());
                }
            };
            children = new ArrayList<>();
        }


        public void addChild(BarAction barAction){
            if(children.isEmpty()){
                expanded = true;
                barAction.parent = this;
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expanded = !expanded;
                        if(expanded) {
                            parentAdapter.notifyItemRangeInserted(parentAdapter.getOuterArray().indexOf(BarAction.this)+1,BarAction.this.children.size());
                        } else {
                            parentAdapter.notifyItemRangeRemoved(parentAdapter.getOuterArray().indexOf(BarAction.this)+1,BarAction.this.children.size());
                        }

                    }
                };
            }

            children.add(barAction);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public View.OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public String toString() {
            return "BarAction{" +
                    "name='" + name + '\'' +
                    ", expanded=" + expanded +
                    ", parentAdapter=" + parentAdapter +
                    ", children=" + children +
                    ", parent=" + parent +
                    ", onClickListener=" + onClickListener +
                    '}';
        }
    }

    public class BarActionAdapter extends RecyclerView.Adapter<BarActionAdapter.MyViewHolder> {

        private List<BarAction> items;
        int lastPosition = -1;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public Button button;

            public MyViewHolder(View view) {
                super(view);
                button = (Button) view.findViewById(R.id.button);
            }

        }

        public void addAction(BarAction barAction){
            barAction.parentAdapter =this;
            items.add(barAction);
        }

        public void addAction(BarAction barAction,BarAction afterItem){
            items.add(items.indexOf(afterItem)+1,barAction);
        }




        public BarActionAdapter(List<BarAction> items) {
            this.items = items;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.buttons_recycle_view_button, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            BarAction movie = getOuterArray().get(position);
            holder.button.setText(movie.getName());
            holder.button.setOnClickListener(movie.getOnClickListener());

        }

        private List<BarAction> getOuterArray(){
            List<BarAction> result = new ArrayList<>();
            result.addAll(iterateArray(items));
            return result;
        }

        private List<BarAction> iterateArray(List<BarAction> array){
            List<BarAction> result = new ArrayList<>();
            for(BarAction b:array){
                result.add(b);
                if(b.expanded) result.addAll(iterateArray(b.children));
            }
            return result;
        }

        @Override
        public int getItemCount() {
            return getOuterArray().size();
        }
    }

}