package sg.edu.ntu.scse.cz2006.ontology.easyexercise.utils;

/**
 * String utilities.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * Removes all empty lines in the source string.
     *
     * @param source the string with empty lines to remove
     * @return the new string
     */
    public static String removeEmptyLines(String source) {
        return source.replaceAll("(?m)^[ \t]*\r?\n", "");
    }

    /**
     * Removes a suffix from a string, if it exists.
     *
     * @param source the string, possibly with a suffix to remove
     * @param suffix the suffix to remove
     * @return the resulting string
     */
    public static String removeSuffixIfExists(String source, String suffix) {
        if (source.endsWith(suffix)) {
            return source.substring(0, source.lastIndexOf(suffix));
        }
        return source;
    }
}