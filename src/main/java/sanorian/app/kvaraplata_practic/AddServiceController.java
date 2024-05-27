package sanorian.app.kvaraplata_practic;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AddServiceController {
    @FXML
    TextField name;
    @FXML
    TextArea formula;
    @FXML
    protected void addService(){
        if (name.getText().equals("") || formula.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.NONE, "Все поля должны быть заполнены", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        ArrayList<Service> services = DataBaseConnect.getServices();
        ArrayList<String> serviceNames = new ArrayList<>();
        assert services != null;
        services.forEach(service -> {
            serviceNames.add(service.getName());
        });
        if (serviceNames.contains(name.getText())){
            Alert alert = new Alert(Alert.AlertType.NONE, "Название услуги должно быть уникально. Исправьте", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        DataBaseConnect.addService(name.getText(), formula.getText());
    }
}