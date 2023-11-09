package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    public AnchorPane rootPane;
    public JFXButton btnItems;
    public JFXButton btnSalesReturn;
    public JFXButton btnEmployers;
    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnSuppliers;

    @FXML
    private JFXButton btnUsers;

    @FXML
    void customerBtnOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/customerForm.fxml");
        assert resource != null;
        Parent load = (Parent) FXMLLoader.load(resource);
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().add(load);

    }

    @FXML
    void dashboardBtnOnAction(ActionEvent event) {

    }

    @FXML
    void ordersBtnOnAction(ActionEvent event) {

    }




    public void btnItemsOnAction(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/itemForm.fxml");
        assert resource != null;
        Parent load = null;
        try {
            load = (Parent) FXMLLoader.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().add(load);

    }


    @FXML
    void suppliersBtnOnAction(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/suppliersForm.fxml");
        assert resource != null;
        Parent load = null;
        try {
            load = (Parent) FXMLLoader.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().add(load);

    }

    @FXML
    void usersBtnOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnSalesReturnOnAction(ActionEvent actionEvent) {

    }

    public void btnEmployersOnAction(ActionEvent actionEvent) {
        URL resource = this.getClass().getResource("/view/employerForm.fxml");
        assert resource != null;
        Parent load = null;
        try {
            load = (Parent) FXMLLoader.load(resource);
        } catch (IOException e) {

        }
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().add(load);


    }
}
