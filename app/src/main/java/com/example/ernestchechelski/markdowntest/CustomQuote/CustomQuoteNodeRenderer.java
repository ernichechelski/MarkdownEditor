package com.example.ernestchechelski.markdowntest.CustomQuote;

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

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ernest.chechelski on 8/21/2017.
 */


public class CustomQuoteNodeRenderer implements NodeRenderer {
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

    private void render(CustomQuote node, NodeRendererContext context, HtmlWriter html) {
        CustomQuote emoji = (CustomQuote) node;
        CustomQuoteCheetSheet.CustomQuoteShortcut shortcut = CustomQuoteCheetSheet.shortCutMap.get(emoji.getText().toString());
        if (shortcut == null) {
            // output as text
            html.text(":");
            context.renderChildren(node);
            html.text(":");
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, useImageURL ? shortcut.url : rootImagePath + shortcut.image, null);

            html.attr("src", resolvedLink.getUrl());
            html.attr("alt", "emoji " + shortcut.category + ":" + shortcut.name);
            if (!attrImageSize.isEmpty()) html.attr("height", attrImageSize).attr("width", attrImageSize);
            if (!attrAlign.isEmpty()) html.attr("align", attrAlign);
            html.withAttr(resolvedLink);
            html.tagVoid("img");
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new CustomQuoteNodeRenderer(options);
        }
    }
}

