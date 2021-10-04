package com.example.myapplication.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class XMLUtil {
    private XMLUtil() {}

    public static Document getXMLDocumentFromString(String xmlString) {
        return Jsoup.parse(xmlString);
    }
}
