module genspark.humansvsgoblins {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;

    exports genspark.humansvsgoblins;
    opens genspark.humansvsgoblins to javafx.fxml;
    exports genspark.humansvsgoblins.pieces;
    opens genspark.humansvsgoblins.pieces to javafx.fxml;
    exports genspark.humansvsgoblins.land;
    opens genspark.humansvsgoblins.land to javafx.fxml;
}