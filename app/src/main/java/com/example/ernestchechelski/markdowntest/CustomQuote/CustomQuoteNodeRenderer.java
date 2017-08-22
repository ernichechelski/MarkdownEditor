package com.example.ernestchechelski.markdowntest.CustomQuote;

import android.content.Context;
import android.util.Log;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.internal.EmojiCheatSheet;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ernest.chechelski on 8/21/2017.
 */


public class CustomQuoteNodeRenderer implements NodeRenderer {
    private static final String TAG = "CustomQuoteNodeRenderer";
    private final String rootImagePath;
    private final boolean useImageURL;
    private final String attrImageSize;
    private final String attrAlign;
    private Context context;

    public CustomQuoteNodeRenderer(DataHolder options) {
        this.rootImagePath = options.get(CustomQuoteExtension.ROOT_IMAGE_PATH);
        this.useImageURL = options.get(CustomQuoteExtension.USE_IMAGE_URLS);
        this.attrImageSize = options.get(CustomQuoteExtension.ATTR_IMAGE_SIZE);
        this.attrAlign = options.get(CustomQuoteExtension.ATTR_ALIGN);
    }

    public CustomQuoteNodeRenderer(DataHolder options,Context context) {
        this.rootImagePath = options.get(CustomQuoteExtension.ROOT_IMAGE_PATH);
        this.useImageURL = options.get(CustomQuoteExtension.USE_IMAGE_URLS);
        this.attrImageSize = options.get(CustomQuoteExtension.ATTR_IMAGE_SIZE);
        this.attrAlign = options.get(CustomQuoteExtension.ATTR_ALIGN);
        this.context = context;
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

    private void render(CustomQuote node, NodeRendererContext context, HtmlWriter html) {
        Log.d(TAG,"render()");
        CustomQuote customQuote = (CustomQuote) node;
        CustomQuoteCheetSheet.CustomQuoteShortcut shortcut = CustomQuoteCheetSheet.shortCutMap.get(customQuote.getText().toString());
        if (shortcut == null) {
            Log.d(TAG,"render() shortcut is null");
            // output as text
            html.text(":");
            context.renderChildren(node);
            html.text(":");
        } else {
            Log.d(TAG,"render() processing shortcut");
            ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, useImageURL ? shortcut.url : rootImagePath + shortcut.image, null);

            html.text(":");
            LocalAssetQuoteRepository repository = new LocalAssetQuoteRepository(this.context);
            String quoteString = repository.getQuote("", new CustomQuoteMarker(1,1),new CustomQuoteMarker(1,2)).toString();
            Log.d(TAG,"render(): " + quoteString);
            BasedSequence seq = BasedSequence.NULL.append(quoteString);
            context.renderChildren(node);
            html.text(":");

//            html.attr("src", resolvedLink.getUrl());
//            //html.attr("alt", "emoji " + shortcut.category + ":" + shortcut.name);
//            if (!attrImageSize.isEmpty()) html.attr("height", attrImageSize).attr("width", attrImageSize);
//            if (!attrAlign.isEmpty()) html.attr("align", attrAlign);
//            html.withAttr(resolvedLink);
//            html.tagVoid("img");
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new CustomQuoteNodeRenderer(options);
        }

        public NodeRenderer create(final DataHolder options, final Context context) {
            return new CustomQuoteNodeRenderer(options,context);
        }
    }
}

