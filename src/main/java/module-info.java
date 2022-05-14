module genspark.humansvsgoblins_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;

    exports genspark.humansvsgoblins_gui;
    opens genspark.humansvsgoblins_gui to javafx.fxml;
}