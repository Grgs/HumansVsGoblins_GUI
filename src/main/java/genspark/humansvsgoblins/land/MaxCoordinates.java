package genspark.humansvsgoblins.land;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Delineates the size of the maximum number of rows and columns available on land. The size of Land.
 */
public class MaxCoordinates {
    /**
     * Maximum number of rows or y values on land.
     */
    public static int maxRows;
    /**
     * Maximum number of columns or x values on land.
     */
    public static int maxCols;

    /**
     * Set maxRows and maxCols from the game properties file.
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
