module sanorian.app.kvaraplata_practic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.scripting;


    opens sanorian.app.kvaraplata_practic to javafx.fxml;
    exports sanorian.app.kvaraplata_practic;
}