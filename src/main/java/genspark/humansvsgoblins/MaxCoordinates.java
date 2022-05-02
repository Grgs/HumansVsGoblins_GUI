package genspark.humansvsgoblins;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MaxCoordinates {
    public static int maxRows;
    public static int maxCols;

    public void getProperties() {
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
