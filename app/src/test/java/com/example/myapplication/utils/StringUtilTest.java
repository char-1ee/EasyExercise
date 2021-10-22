package com.example.myapplication.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * String utility test.
 *
 * @author Zhong Ruoyu
 */
public class StringUtilTest {
    @Test
    public void removeEmptyLinesTest() {
        final String original = "\n\nThe quick\n\nbrown fox\n\n\njumps over \n\nthe lazy dog.\n\n";
        final String expected = "The quick\nbrown fox\njumps over \nthe lazy dog.\n";
        assertEquals(expected, StringUtil.removeEmptyLines(original));
    }

    @Test
    public void removeSuffixIfExistsTest() {
        final String suffix = "world";
        final String originalWithSuffix = "helloWORLDHELLOworld";
        final String originalWithoutSuffix = "HELLOworldhelloWORLD";
        final String expectedWithSuffix = "helloWORLDHELLO";
        assertEquals(expectedWithSuffix, StringUtil.removeSuffixIfExists(originalWithSuffix, suffix));
        assertEquals(originalWithoutSuffix, StringUtil.removeSuffixIfExists(originalWithoutSuffix, suffix));
    }
}
