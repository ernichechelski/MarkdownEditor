package com.example.ernestchechelski.markdowntest.CustomQuote;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ernest.chechelski on 8/21/2017.
 */


public class CustomQuoteCheetSheet {
    public static class CustomQuoteShortcut {
        public final String name;
        public final String image;
        public final String url;
        public final String category;

        public CustomQuoteShortcut(String name, String image, String url, String category) {
            this.name = name;
            this.image = image;
            this.url = url;
            this.category = category;
        }
    }

    public static final HashMap<String, CustomQuoteShortcut> shortCutMap = new HashMap<String, CustomQuoteShortcut>();
    public static final HashMap<String, CustomQuoteShortcut> fileMap = new HashMap<String, CustomQuoteShortcut>();
    public static final HashMap<String, CustomQuoteShortcut> gitHubUrlMap = new HashMap<String, CustomQuoteShortcut>();

    static {
        shortCutMap.put("smile", new CustomQuoteShortcut("smile", "smile.png", "https://assets-cdn.github.com/images/icons/emoji/unicode/1f604.png", "people"));

    }

    public static void wantFileUrlMaps() {
        synchronized (fileMap) {
            if (fileMap.isEmpty()) {
                // create a file map to EmojiShortcut
                for (Map.Entry<String, CustomQuoteShortcut> entry : shortCutMap.entrySet()) {
                    if (!fileMap.containsKey(entry.getValue().image)) fileMap.put(entry.getValue().image, entry.getValue());
                    if (entry.getValue().url != null && !gitHubUrlMap.containsKey(entry.getValue().url)) gitHubUrlMap.put(entry.getValue().url, entry.getValue());
                }
            }
        }
    }

    public static CustomQuoteShortcut getImageShortcut(String imageURI) {
        wantFileUrlMaps();
        CustomQuoteShortcut shortcut = gitHubUrlMap.get(imageURI);
        if (shortcut == null) {
            // try just the file name
            String fileName = new File(imageURI).getName();
            shortcut = fileMap.get(fileName);
        }
        return shortcut;
    }
}