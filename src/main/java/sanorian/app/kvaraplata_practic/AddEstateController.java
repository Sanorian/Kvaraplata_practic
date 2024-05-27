package sanorian.app.kvaraplata_practic;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class AddEstateController {
    @FXML
    TextField city;
    @FXML
    TextField street;
    @FXML
    TextField building;
    @FXML
    TextField apartment;
    @FXML
    TextField coldWater;
    @FXML
    TextField hotWater;
    @FXML
    TextField space;
    @FXML
    TextField peopleCount;
    @FXML
    protected void addEstate(){
        if (city.getText().equals("") || street.getText().equals("") || building.getText().equals("") || apartment.getText().equals("") || coldWater.getText().equals("") || hotWater.getText().equals("") || space.getText().equals("") || peopleCount.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.NONE, "Надо заполнить все поля", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        DataBaseConnect.addEstate(city.getText(), street.getText(), building.getText(), Integer.valueOf(apartment.getText()),  Double.valueOf(hotWater.getText()), Double.valueOf(coldWater.getText()), Double.valueOf(space.getText()), Integer.valueOf(peopleCount.getText()));
    }
}