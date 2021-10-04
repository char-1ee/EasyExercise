package com.example.myapplication.utils;

public class StringUtil {
    private StringUtil() {}

    public static String removeEmptyLines(String source) {
        return source.replaceAll("(?m)^[ \t]*\r?\n", "");
    }

    public static String removeSuffixIfExists(String source, String suffix) {
        if (source.endsWith(suffix)) {
            return source.substring(0, source.lastIndexOf(suffix));
        }
        return source;
    }
}
