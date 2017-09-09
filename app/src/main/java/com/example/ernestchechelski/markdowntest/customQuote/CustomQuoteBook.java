package com.example.ernestchechelski.markdowntest.customQuote;

/**
 * Created by ernest.chechelski on 9/8/2017.
 */

public class CustomQuoteBook {
    public String name;
    private String code;

    public static String baseCode = "10010611";
    public CustomQuoteBook(String name, String code) {
        this.name = name;
        this.code = code;
    }
    public String getFullCode(){
        return baseCode + this.code;
    }

    public String getFullChapterCode(Integer chapter){
        return getFullCode()+ getSplitString(chapter);
    }

    private String getSplitString(Integer chapter){
        if (chapter.equals(1)){
            return "";
        } else {
            return "-split"+chapter.toString();
        }
    }

}
