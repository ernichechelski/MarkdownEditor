package com.example.ernestchechelski.markdowntest.CustomQuote;

import android.content.Context;
import android.util.Log;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.emoji.internal.EmojiDelimiterProcessor;
import com.vladsch.flexmark.ext.emoji.internal.EmojiJiraRenderer;
import com.vladsch.flexmark.ext.emoji.internal.EmojiNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Created by ernest.chechelski on 8/21/2017.
 */


public class CustomQuoteExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private static final String TAG = "CustomQuoteExtension";
    public static final DataKey<String> ATTR_ALIGN = new DataKey<String>("ATTR_ALIGN", "absmiddle");
    public static final DataKey<String> ATTR_IMAGE_SIZE = new DataKey<String>("ATTR_IMAGE_SIZE", "20");
    public static final DataKey<String> ROOT_IMAGE_PATH = new DataKey<String>("ROOT_IMAGE_PATH", "/img/");
    public static final DataKey<Boolean> USE_IMAGE_URLS = new DataKey<Boolean>("USE_IMAGE_URLS", false);
    private Context context;

    private CustomQuoteExtension(Context context) {
        Log.d(TAG,"CustomQuoteExtension()");
        this.context = context;
    }

    public static Extension create(Context context) {
        return new CustomQuoteExtension(context);
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new CustomQuoteDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new CustomQuoteNodeRenderer.Factory());
        }
    }
}