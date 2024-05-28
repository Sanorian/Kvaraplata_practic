package sanorian.app.kvaraplata_practic;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class HelloController {

    public TableView<ProvisionTable> table;
    @FXML
    private TextField cityName;
    @FXML
    private TextField streetName;
    @FXML
    private TextField buildingNumber;
    @FXML
    private TextField apartmentNumber;
    @FXML
    private Label allCost;
    @FXML
    void initialize(){
        ContextMenu cm = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Удалить");
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ProvisionTable selectedProvision = table.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.NONE, "Вы уверены, что хотите удалить этот контракт?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Удаление контракта");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        DataBaseConnect.deleteProvision(selectedProvision.getID());
                    }
                });
            }
        });
        cm.getItems().add(deleteItem);
        MenuItem changeItem = new MenuItem("Изменить");
        cm.getItems().add(changeItem);
        changeItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ProvisionTable selectedProvision = table.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.NONE, "Вы уверены, что хотите изменить этот контракт?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Изменение контракта");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        // изменение 
                    }
                });
            }
        });
        table.setContextMenu(cm);
    }
    @FXML
    protected void searchProvision() {
        // проверка на вводы
        if (cityName.getText().equals("") || streetName.getText().equals("") || buildingNumber.getText().equals("") || apartmentNumber.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.NONE, "Введите полный адрес", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        ArrayList<Provision> provisions = DataBaseConnect.getProvisionByAddress(cityName.getText(), streetName.getText(), buildingNumber.getText(), apartmentNumber.getText());
        ArrayList<ProvisionTable> provisionTable = new ArrayList<>();
        AtomicReference<Double> cost = new AtomicReference<>(0.0);

        provisions.forEach(provision -> {
            cost.updateAndGet(v -> v + provision.getCost());
            Service service = DataBaseConnect.getService(provision.getServiceID());
            assert service != null;
            provisionTable.add(new ProvisionTable(service.getName(), provision.getCost(), service.getFormula(), provision.getId()));
        });

        TableColumn nameColumn = new TableColumn("Название услуги");
        TableColumn costColumn = new TableColumn("Стоимость");
        TableColumn formulaColumn = new TableColumn("Формула");
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProvisionTable, String>("name"));
        costColumn.setCellValueFactory(new PropertyValueFactory<ProvisionTable, Double>("cost"));
        formulaColumn.setCellValueFactory(new PropertyValueFactory<ProvisionTable, String>("formula"));
        table.getColumns().addAll(nameColumn, formulaColumn, costColumn);
        table.setItems(FXCollections.observableList(provisionTable));
        allCost.setText(String.valueOf(cost.get()));
    }
    @FXML
    protected void addService() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("addservice.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Добавить услугу");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    protected void addEstate() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("addestate.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Добавить недвижимость");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    protected void addProvision() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("addprovision.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Добавить контракт");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}