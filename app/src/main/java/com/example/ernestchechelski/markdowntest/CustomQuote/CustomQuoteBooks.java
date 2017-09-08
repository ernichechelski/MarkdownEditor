package com.example.ernestchechelski.markdowntest.CustomQuote;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ernest.chechelski on 9/1/2017.
 */

public class CustomQuoteBooks {

    public static Map<String,CustomQuoteBook> books;


    static {
        books = new HashMap<String, CustomQuoteBook>();
        books.put("Ge",new CustomQuoteBook("Genesis","05"));
        books.put("Ex",new CustomQuoteBook("Exodus","06"));
        books.put("Le",new CustomQuoteBook("Leviticus","07"));
        books.put("Nu",new CustomQuoteBook("Numbers","08"));
        books.put("De",new CustomQuoteBook("Deuteronomy","09"));
        books.put("Jos",new CustomQuoteBook("Joshua","10"));
        books.put("Jg",new CustomQuoteBook("Judges","11"));
        books.put("Ru",new CustomQuoteBook("Ruth","12"));
        books.put("1Sa",new CustomQuoteBook("1 Samuel","13"));
        books.put("2Sa",new CustomQuoteBook("2 Samuel","14"));
        books.put("1Ki",new CustomQuoteBook("1 Kings","15"));
        books.put("2Ki",new CustomQuoteBook("2 Kings","16"));
        books.put("1Chr",new CustomQuoteBook("1 Chronicles","17"));
        books.put("2Chr",new CustomQuoteBook("2 Chronicles","18"));
        books.put("Ezr",new CustomQuoteBook("Ezra","19"));
        books.put("Ne",new CustomQuoteBook("Nehemiah","20"));
        books.put("Es",new CustomQuoteBook("Esther","21"));
        books.put("Job",new CustomQuoteBook("Job","22"));
        books.put("Ps",new CustomQuoteBook("Psalms","23"));
        books.put("Pr",new CustomQuoteBook("Proverbs","24"));
        books.put("Ec",new CustomQuoteBook("Ecclesiastes","25"));
        books.put("Ca",new CustomQuoteBook("Song of Salomon (Canticles)","26"));
        books.put("Isa",new CustomQuoteBook("Isaiah","27"));
        books.put("Jer",new CustomQuoteBook("Jeremiah","28"));
        books.put("La",new CustomQuoteBook("Lamentations","29"));
        books.put("Eze",new CustomQuoteBook("Ezekiel","30"));
        books.put("Da",new CustomQuoteBook("Daniel","31"));
        books.put("Ho",new CustomQuoteBook("Hosea","32"));
        books.put("Joe",new CustomQuoteBook("Joel","33"));
        books.put("Am",new CustomQuoteBook("Amos","34"));
        books.put("Ob",new CustomQuoteBook("Abadiah","35"));
        books.put("Jon",new CustomQuoteBook("Jonah","36"));
        books.put("Mic",new CustomQuoteBook("Micah","37"));
        books.put("Na",new CustomQuoteBook("Nahum","38"));
        books.put("Hab",new CustomQuoteBook("Habakkuk","39"));
        books.put("Zep",new CustomQuoteBook("Zephaniah","40"));
        books.put("Hag",new CustomQuoteBook("Haggai","41"));
        books.put("Zec",new CustomQuoteBook("Zechariah","42"));
        books.put("Mal",new CustomQuoteBook("Malachi","43"));
        //new testament
        books.put("Mt",new CustomQuoteBook("Matthew","44"));
        books.put("Mr",new CustomQuoteBook("Mark","45"));
        books.put("Lu",new CustomQuoteBook("Luke","46"));
        books.put("Joh",new CustomQuoteBook("John","47"));
        books.put("Ac",new CustomQuoteBook("Acts","48"));
        books.put("Ro",new CustomQuoteBook("Romans","49"));
        books.put("1Co",new CustomQuoteBook("1 Corinthians","50"));
        books.put("2Co",new CustomQuoteBook("2 Corinthians","51"));
        books.put("Ga",new CustomQuoteBook("Galatians","52"));
        books.put("Eph",new CustomQuoteBook("Ephesians","53"));
        books.put("Philippians",new CustomQuoteBook("Php","54"));
        books.put("Col",new CustomQuoteBook("Collosians","55"));
        books.put("1Th",new CustomQuoteBook("1 Thessalonians","56"));
        books.put("2Th",new CustomQuoteBook("2 Thessalonians","57"));
        books.put("1Ti",new CustomQuoteBook("1 Timothy","58"));
        books.put("2Ti",new CustomQuoteBook("2 Timothy","59"));
        books.put("Tit",new CustomQuoteBook("Titus","60"));
        books.put("Phm",new CustomQuoteBook("Philemon","61"));
        books.put("Heb",new CustomQuoteBook("Hebrews","62"));
        books.put("Jas",new CustomQuoteBook("James","63"));
        books.put("1Pe",new CustomQuoteBook("1 Peter","64"));
        books.put("2Pe",new CustomQuoteBook("2 Peter","65"));
        books.put("1Jo",new CustomQuoteBook("1 John","66"));
        books.put("2Jo",new CustomQuoteBook("2 John","67"));
        books.put("3Jo",new CustomQuoteBook("3 John","68"));
        books.put("Jude",new CustomQuoteBook("Jude","69"));
        books.put("Re",new CustomQuoteBook("Revelation","70"));
    }
    public static CustomQuoteBook getBookByCode(String abbreviation){
        return books.get(abbreviation);
    }
}
