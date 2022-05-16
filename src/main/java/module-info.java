module genspark.humansvsgoblins {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;

    exports genspark.humansvsgoblins;
    opens genspark.humansvsgoblins to javafx.fxml;
}