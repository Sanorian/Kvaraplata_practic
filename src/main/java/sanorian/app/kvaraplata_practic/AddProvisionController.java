package sanorian.app.kvaraplata_practic;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        ArrayList<Service> services = DataBaseConnect.getServices();
        ArrayList<String> servicesNames = new ArrayList<>();
        assert services != null;
        services.forEach(service -> {
            servicesNames.add(service.getName());
        });
        servicesList = new ListView<>(FXCollections.observableArrayList(servicesNames));
    }
    @FXML
    protected void addProvision(){
        final String[] selectedService = new String[1];
        MultipleSelectionModel<String> lvSelModel = servicesList.getSelectionModel();
        lvSelModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal) {
                String selItems = "";
                ObservableList<String> selected = servicesList.getSelectionModel().getSelectedItems();
                for (int i = 0; i < selected.size(); i++) {
                    selItems += "\n      " + selected.get(i);
                }
                selectedService[0] = selItems;
            }
        });
        if (estateID==null || selectedService[0]==null){
            Alert alert = new Alert(Alert.AlertType.NONE, "Вы должны выбрать и недвижимость, и услугу", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        ArrayList<Service> services = DataBaseConnect.getServices();
        assert services != null;
        services.removeIf(s -> !s.getName().contains(selectedService[0]));
        DataBaseConnect.addProvision(services.get(0).getId(), estateID);
    }
    @FXML
    protected void searchEstate(){
        Estate estate = DataBaseConnect.getEstateByAddress(city.getText(), street.getText(), building.getText(), apartment.getText());
        if (estate==null){
            Alert alert = new Alert(Alert.AlertType.NONE, "Такая недвижимость отсутствует", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        estateID = estate.getId();
    }

}