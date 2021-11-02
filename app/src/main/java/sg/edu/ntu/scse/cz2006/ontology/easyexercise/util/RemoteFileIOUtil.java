package sg.edu.ntu.scse.cz2006.ontology.easyexercise.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Remote file IO utilities.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class RemoteFileIOUtil {
    private static final String lineSeparator = System.lineSeparator();

    private RemoteFileIOUtil() {
    }

    /**
     * Reads contents of the file stored in the URL.
     *
     * @param urlString the URL of the remote file
     * @return the contents of the file
     */
    public static String readFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder stringBuilder = new StringBuilder();
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                stringBuilder.append(line);
                stringBuilder.append(lineSeparator);
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
