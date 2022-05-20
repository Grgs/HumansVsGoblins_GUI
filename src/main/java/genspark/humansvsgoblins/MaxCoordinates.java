package genspark.humansvsgoblins;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Delineates the size of the maximum number of rows and columns available on land. The size of Land.
 */
public class MaxCoordinates {
    /**
     * maximum number of land rows.
     */
    public static int maxRows;
    /**
     * maximum number of land columns.
     */
    public static int maxCols;

    /**
     * get maxRows and maxCols from the game properties file.
     */
    public static void getProperties() {
        try (FileReader fileReader = new FileReader("game.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            maxCols = Integer.parseInt((String) properties.get("maxCols"));
            maxRows = Integer.parseInt((String) properties.get("maxRows"));
        } catch (IOException e) {
            maxCols = 9;
            maxRows = 9;
        }
    }
}
