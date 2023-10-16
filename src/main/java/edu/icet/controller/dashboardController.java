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
    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnProducts;

    @FXML
    private JFXButton btnStore;

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

    @FXML
    void productBtnOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/productForm.fxml");
        assert resource != null;
        Parent load = (Parent) FXMLLoader.load(resource);
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().add(load);
    }

    @FXML
    void storesBtnOnAction(ActionEvent event) {

    }

    @FXML
    void suppliersBtnOnAction(ActionEvent event) {

    }

    @FXML
    void usersBtnOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
