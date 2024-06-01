package sanorian.app.kvaraplata_practic;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddServiceController {
    @FXML
    TextField name;
    @FXML
    TextArea formula;
    @FXML
    protected void addService(){
        if (name.getText().isEmpty() || formula.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Все поля должны быть заполнены", ButtonType.OK);
            alert.setTitle("Недозаполненность");
            alert.showAndWait();
            return;
        }
        if (!DataBaseConnect.getServicesByName(name.getText()).isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.NONE, "Название услуги должно быть уникально. Исправьте", ButtonType.OK);
            alert1.setTitle("Неуникальность");
            alert1.showAndWait();
            return;
        }
        DataBaseConnect.addService(name.getText(), formula.getText());
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }
}