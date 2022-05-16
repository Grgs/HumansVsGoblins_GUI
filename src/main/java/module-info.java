module genspark.humansvsgoblins {
    requires javafx.controls;
    requires javafx.fxml;

    exports genspark.humansvsgoblins;
    opens genspark.humansvsgoblins to javafx.fxml;
}