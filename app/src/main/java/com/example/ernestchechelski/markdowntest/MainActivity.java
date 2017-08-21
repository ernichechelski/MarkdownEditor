package com.example.ernestchechelski.markdowntest;

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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.ernestchechelski.markdowntest.CustomQuote.CustomQuoteExtension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    WebView webView;
    EditText editText;
    Parser parser;
    HtmlRenderer renderer;

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<BarAction> rawTags = new ArrayList<>();
    private List<BarAction> autoTags = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Markdown Editor");
        webView = (WebView) this.findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);
        editText = (EditText) this.findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.buttons_recycler_view);
        mAdapter = new MoviesAdapter(rawTags);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        loadRawTags();

        //editText.setText("This is *Sparta*");
        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
               refresh();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
        });
        MutableDataSet options = new MutableDataSet()
                .set(Parser.REFERENCES_KEEP, KeepType.LAST)
                .set(HtmlRenderer.INDENT_SIZE, 2)
                .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
                .set(EmojiExtension.USE_IMAGE_URLS,true)

                .set(Parser.EXTENSIONS, Arrays.asList(EmojiExtension.create(), CustomQuoteExtension.create()));

        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
        refresh();
        //loadTestData();
        // uncomment to set optional extensions
        //options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");


    }

    private void loadRawTags() {
        rawTags.add(new BarAction("#", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("#");
            }
        }));
        rawTags.add(new BarAction("*", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("*");
            }
        }));
        rawTags.add(new BarAction("_", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("_");
            }
        }));
        rawTags.add(new BarAction("[]()", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selection = editText.getSelectionStart();
                insertTextInSelection("[]()");
                editText.setSelection(selection + 1);

            }
        }));


        rawTags.add(new BarAction(">", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection(">");
            }
        }));
    }
    private void loadAutoTags() {
        rawTags.add(new BarAction("H1", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("#");
            }
        }));
        rawTags.add(new BarAction("H2", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("##");
            }
        }));
        rawTags.add(new BarAction("H3", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("###");
            }
        }));
        rawTags.add(new BarAction("H4", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("####");
            }
        }));
        rawTags.add(new BarAction("H5", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTextInSelection("#####");
            }
        }));
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
        Node document = parser.parse(editText.getText().toString());
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        System.out.println(html);
        Log.v(TAG, "Refresh()=" + html);
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
    public class BarAction {
        public String name;
        public View.OnClickListener onClickListener;

        public BarAction(String name, View.OnClickListener onClickListener) {
            this.name = name;
            this.onClickListener = onClickListener;
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
    }


    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

        private List<BarAction> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public Button button;

            public MyViewHolder(View view) {
                super(view);
                button = (Button) view.findViewById(R.id.button);
            }

        }


        public MoviesAdapter(List<BarAction> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.buttons_recycle_view_button, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            BarAction movie = moviesList.get(position);
            holder.button.setText(movie.getName());
            holder.button.setOnClickListener(movie.getOnClickListener());
        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }

}
