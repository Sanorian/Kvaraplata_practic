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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class HelloController {
    private String city, street, building, apartment;
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
                        reloadTable();
                    }
                });
            }
        });
        cm.getItems().add(deleteItem);
        MenuItem changeItem = new MenuItem("Изменить формулу");
        cm.getItems().add(changeItem);
        changeItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ProvisionTable selectedProvision = table.getSelectionModel().getSelectedItem();
                if (selectedProvision==null){
                    Alert alert = new Alert(Alert.AlertType.NONE, "Вы не выбрали контракт", ButtonType.OK);
                    alert.setTitle("Контракт!!!");
                    alert.showAndWait();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.NONE, "Вы уверены, что хотите изменить формулу?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Изменение формулы");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        Service service = DataBaseConnect.getService(selectedProvision.getServiceID());
                        assert service != null;
                        TextInputDialog tid = new TextInputDialog(service.getFormula());
                        tid.setTitle("Изменение формулы");
                        tid.setHeaderText("В формуле используйте только h (hot water - горячая вода), c (cold water - холодная вода), s (space - площадь квартиры), p (people - количество людей, проживающих в квартире)");
                        tid.showAndWait().ifPresent(formula->{
                            DataBaseConnect.updateServiceFormula(service.getId(), formula);
                            reloadTable();
                        });
                    }
                });
            }
        });
        table.setContextMenu(cm);
    }
    @FXML
    protected void searchProvision() {
        if (cityName.getText().isEmpty() || streetName.getText().isEmpty() || buildingNumber.getText().isEmpty() || apartmentNumber.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.NONE, "Введите полный адрес", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        city = cityName.getText();
        street = streetName.getText();
        building = buildingNumber.getText();
        apartment = apartmentNumber.getText();

        reloadTable();
    }
    @FXML
    protected void addService() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("addservice.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("Добавить услугу");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    protected void addEstate() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("addestate.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("Добавить недвижимость");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    protected void addProvision() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("addprovision.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("Добавить контракт");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    protected void changeEstate(){
        if (cityName.getText().isEmpty() || streetName.getText().isEmpty() || buildingNumber.getText().isEmpty() || apartmentNumber.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.NONE, "Введите полный адрес", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Estate estate = DataBaseConnect.getEstateByAddress(cityName.getText(), streetName.getText(), buildingNumber.getText(), apartmentNumber.getText());
        if (estate==null){
            Alert alert1 = new Alert(Alert.AlertType.NONE, "Такой недвижимости нет", ButtonType.OK);
            alert1.showAndWait();
            return;
        }
        AtomicReference<String> newCity = new AtomicReference<>(estate.getCity());
        AtomicReference<String> newStreet = new AtomicReference<>(estate.getStreet());
        AtomicReference<String> newBuilding = new AtomicReference<>(estate.getBuilding());
        AtomicReference<String> newApartment = new AtomicReference<>(String.valueOf(estate.getApartment()));
        AtomicReference<String> newHotWater = new AtomicReference<>(String.valueOf(estate.getHotWater()));
        AtomicReference<String> newColdWater = new AtomicReference<>(String.valueOf(estate.getColdWater()));
        AtomicReference<String> newEstateSpace = new AtomicReference<>(String.valueOf(estate.getEstateSpace()));
        AtomicReference<String> newNumberOfRegistredPeople = new AtomicReference<>(String.valueOf(estate.getNumberOfRegistredPeople()));
        TextInputDialog tid1 = new TextInputDialog(newCity.get());
        tid1.setTitle("Изменение недвижимости");
        tid1.setHeaderText("Город");
        tid1.showAndWait().ifPresent(value->{
            newCity.set(value);
        });
        TextInputDialog tid2 = new TextInputDialog(newStreet.get());
        tid2.setTitle("Изменение недвижимости");
        tid2.setHeaderText("Улица");
        tid2.showAndWait().ifPresent(value->{
            newStreet.set(value);
        });
        TextInputDialog tid3 = new TextInputDialog(newBuilding.get());
        tid3.setTitle("Изменение недвижимости");
        tid3.setHeaderText("Здание");
        tid3.showAndWait().ifPresent(value->{
            newBuilding.set(value);
        });
        TextInputDialog tid4 = new TextInputDialog(newApartment.get());
        tid4.setTitle("Изменение недвижимости");
        tid4.setHeaderText("Квартира");
        tid4.showAndWait().ifPresent(value->{
            newApartment.set(value);
        });
        TextInputDialog tid5 = new TextInputDialog(newHotWater.get());
        tid5.setTitle("Изменение недвижимости");
        tid5.setHeaderText("Горячая вода");
        tid5.showAndWait().ifPresent(value->{
            newHotWater.set(value);
        });
        TextInputDialog tid6 = new TextInputDialog(newColdWater.get());
        tid6.setTitle("Изменение недвижимости");
        tid6.setHeaderText("Холодная вода");
        tid6.showAndWait().ifPresent(value->{
            newColdWater.set(value);
        });
        TextInputDialog tid7 = new TextInputDialog(newEstateSpace.get());
        tid7.setTitle("Изменение недвижимости");
        tid7.setHeaderText("Площадь квартиры");
        tid7.showAndWait().ifPresent(value->{
            newEstateSpace.set(value);
        });
        TextInputDialog tid8 = new TextInputDialog(newNumberOfRegistredPeople.get());
        tid8.setTitle("Изменение недвижимости");
        tid8.setHeaderText("Количество зарегистрированных людей");
        tid8.showAndWait().ifPresent(value->{
            newNumberOfRegistredPeople.set(value);
        });
        DataBaseConnect.updateEstateByID(estate.getId(), newCity.get(), newStreet.get(), newBuilding.get(), newApartment.get(), newHotWater.get(), newColdWater.get(), newEstateSpace.get(), newNumberOfRegistredPeople.get());
        reloadTable();
    }
    private void reloadTable(){
        table.getColumns().clear();
        ArrayList<Provision> provisions = DataBaseConnect.getProvisionByAddress(city, street, building, apartment);
        ArrayList<ProvisionTable> provisionTable = new ArrayList<>();
        AtomicReference<Double> cost = new AtomicReference<>(0.0);

        provisions.forEach(provision -> {
            cost.updateAndGet(v -> v + provision.getCost());
            Service service = DataBaseConnect.getService(provision.getServiceID());
            assert service != null;
            provisionTable.add(new ProvisionTable(service.getName(), provision.getCost(), service.getFormula(), provision.getId(), provision.getServiceID()));
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
}