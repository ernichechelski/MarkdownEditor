package com.example.ernestchechelski.markdowntest.CustomQuote;

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
    public static final DataKey<String> ATTR_ALIGN = new DataKey<String>("ATTR_ALIGN", "absmiddle");
    public static final DataKey<String> ATTR_IMAGE_SIZE = new DataKey<String>("ATTR_IMAGE_SIZE", "20");
    public static final DataKey<String> ROOT_IMAGE_PATH = new DataKey<String>("ROOT_IMAGE_PATH", "/img/");
    public static final DataKey<Boolean> USE_IMAGE_URLS = new DataKey<Boolean>("USE_IMAGE_URLS", false);

    private CustomQuoteExtension() {
    }

    public static Extension create() {
        return new CustomQuoteExtension();
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
