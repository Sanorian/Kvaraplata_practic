module sanorian.app.kvaraplata_practic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens sanorian.app.kvaraplata_practic to javafx.fxml;
    exports sanorian.app.kvaraplata_practic;
}