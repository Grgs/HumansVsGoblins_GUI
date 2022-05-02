module genspark.humansvsgoblins_gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens genspark.humansvsgoblins_gui to javafx.fxml;
    exports genspark.humansvsgoblins_gui;
}