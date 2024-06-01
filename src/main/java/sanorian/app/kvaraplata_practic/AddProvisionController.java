package sanorian.app.kvaraplata_practic;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddProvisionController {
    Integer estateID = null;
    @FXML
    TextField city;
    @FXML
    TextField street;
    @FXML
    TextField building;
    @FXML
    TextField apartment;
    @FXML
    ListView<String> servicesList;
    @FXML
    void initialize(){
        servicesList.setItems(FXCollections.observableArrayList(DataBaseConnect.getServiceNames()));
    }
    @FXML
    protected void addProvision(){
        String selectedItem = servicesList.getSelectionModel().getSelectedItem();
        if (estateID==null || selectedItem==null){
            Alert alert = new Alert(Alert.AlertType.NONE, "Вы должны выбрать и недвижимость, и услугу", ButtonType.OK);
            alert.setTitle("Недовыбор");
            alert.showAndWait();
            return;
        }
        ArrayList<Service> services = DataBaseConnect.getServices();
        assert services != null;
        services.removeIf(s -> !s.getName().contains(selectedItem));
        DataBaseConnect.addProvision(services.get(0).getId(), estateID);
        Stage stage = (Stage) city.getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void searchEstate(){
        Estate estate = DataBaseConnect.getEstateByAddress(city.getText(), street.getText(), building.getText(), apartment.getText());
        if (estate==null){
            Alert alert = new Alert(Alert.AlertType.NONE, "Такая недвижимость отсутствует", ButtonType.OK);
            alert.setTitle("Отсутствие недвижимость");
            alert.showAndWait();
            return;
        }
        estateID = estate.getId();
    }
}