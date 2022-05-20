module genspark.humansvsgoblins {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;

    exports genspark.humansvsgoblins;
    opens genspark.humansvsgoblins to javafx.fxml;
}