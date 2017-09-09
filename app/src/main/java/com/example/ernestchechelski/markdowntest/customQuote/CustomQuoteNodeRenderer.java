package com.example.ernestchechelski.markdowntest.customQuote;

import android.util.Log;

import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ernest.chechelski on 8/21/2017.
 */


public class CustomQuoteNodeRenderer implements NodeRenderer {
    public static String TAG = CustomQuoteNodeRenderer.class.getName();
    private final String rootImagePath;
    private final boolean useImageURL;
    private final String attrImageSize;
    private final String attrAlign;

    public CustomQuoteNodeRenderer(DataHolder options) {
        this.rootImagePath = options.get(CustomQuoteExtension.ROOT_IMAGE_PATH);
        this.useImageURL = options.get(CustomQuoteExtension.USE_IMAGE_URLS);
        this.attrImageSize = options.get(CustomQuoteExtension.ATTR_IMAGE_SIZE);
        this.attrAlign = options.get(CustomQuoteExtension.ATTR_ALIGN);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<CustomQuote>(CustomQuote.class, new CustomNodeRenderer<CustomQuote>() {
            @Override
            public void render(CustomQuote node, NodeRendererContext context, HtmlWriter html) {
                CustomQuoteNodeRenderer.this.render(node, context, html);
            }
        }));
        return set;
    }

    private void render(final CustomQuote node, NodeRendererContext context, HtmlWriter html) {
        CustomQuote customQuoteNode = (CustomQuote) node;
        Log.d(TAG,"Render");
        CustomQuoteRepository customQuoteRepository = new CustomQuoteRepository(CustomQuoteExtension.context,CustomQuoteExtension.resourceCatalogName);
        String parsedQuote = customQuoteRepository.getHtmlStringByParsedString(customQuoteNode.getText().toString());
        if(parsedQuote == null){
            Log.d(TAG,"Null parsed quote");
            html.text("|");
            html.text(node.getText().toUpperCase());
            html.text("|");
        }
        else {
            Log.d(TAG, "parsed quote" + parsedQuote);
            html.text(parsedQuote);
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new CustomQuoteNodeRenderer(options);
        }
    }
}

