package sanorian.app.kvaraplata_practic;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

public class HelloController {
    public TableView table;
    @FXML
    private TextField cityName;
    @FXML
    private TextField streetName;
    @FXML
    private TextField buildingNumber;
    @FXML
    private TextField apartmentNumber;
    @FXML
    void initialize(){

    }
    @FXML
    protected void searchProvision() {
        ArrayList<Provision> provisions = DataBaseConnect.getProvisionByAddress(cityName.getText(), streetName.getText(), buildingNumber.getText(), apartmentNumber.getText());

        ArrayList<ProvisionTable> provisionTable = new ArrayList<>();

        for (Provision provision : provisions){
            provisionTable.add(new ProvisionTable(DataBaseConnect.getService(provision.getServiceID()).getName(), provision.getCost()));
        }

        TableColumn nameColumn = new TableColumn("Название услуги");
        TableColumn costColumn = new TableColumn("Стоимость");
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProvisionTable, String>("name"));
        costColumn.setCellValueFactory(new PropertyValueFactory<ProvisionTable, Double>("cost"));
        table.getColumns().addAll(nameColumn, costColumn);
        table.setItems(FXCollections.observableList(provisionTable));
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