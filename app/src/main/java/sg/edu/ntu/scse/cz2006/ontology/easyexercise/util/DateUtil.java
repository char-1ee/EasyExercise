package sg.edu.ntu.scse.cz2006.ontology.easyexercise.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date utilities.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class DateUtil {
    private DateUtil() {
        // Disallows instantiation
    }

    /**
     * Gets a date from the string, with the given pattern.
     *
     * @param source  the input string containing the date
     * @param pattern the pattern of the input
     * @return the date result
     */
    public static Date getDateFromString(String source, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Converts a date to a string according to the given pattern.ƒ
     *
     * @param source  the date to be converted
     * @param pattern tha pattern of the output date
     * @return the string result
     */
    public static String convertDateToString(Date source, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(source);
    }
}
