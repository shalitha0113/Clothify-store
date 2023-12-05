package edu.icet.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import edu.icet.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemId;

    @FXML
    private TreeTableColumn<?, ?> colAmount;

    @FXML
    private TreeTableColumn<?, ?> colDescription;

    @FXML
    private TreeTableColumn<?, ?> colItemId;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    @FXML
    private TreeTableColumn<?, ?> colQty;

    @FXML
    private TreeTableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private JFXTreeTableView<?> tblOrders;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtItemDescription;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        generateId();
        loadCustomerId();
        loadItemId();

        cmbCustomerId.setOnAction(actionEvent -> {
            setCustomerName();
        });

        cmbItemId.setOnAction(actionEvent -> {
            setItemDescription();
        });
    }



    private void setItemDescription() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT * FROM items WHERE itemCode=?");
            psTm.setString(1,cmbItemId.getValue().toString());
            ResultSet resultSet = psTm.executeQuery();

            if(resultSet.next()){
                txtItemDescription.setText(resultSet.getString(2));
                lblQtyOnHand.setText(resultSet.getString(5));
                lblUnitPrice.setText(String.format("%.2f",resultSet.getDouble(4)));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCustomerName() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT name FROM customer WHERE id=?");
            psTm.setString(1,cmbCustomerId.getValue().toString());
            ResultSet resultSet = psTm.executeQuery();

            if(resultSet.next()){
                txtCustomerName.setText(resultSet.getString(1));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadItemId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT itemCode FROM items");
            ResultSet resultSet = psTm.executeQuery();

            ObservableList<String> itemsIDs = FXCollections.observableArrayList();

            while (resultSet.next()){
                itemsIDs.add(resultSet.getString(1));
            }
            cmbItemId.setItems(itemsIDs);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadCustomerId() {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT id FROM customer");
            ResultSet resultSet = psTm.executeQuery();

            ObservableList<String> customerIDs = FXCollections.observableArrayList();

            while (resultSet.next()){
                customerIDs.add(resultSet.getString(1));
            }
            cmbCustomerId.setItems(customerIDs);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void addCartButtonOnAction(ActionEvent event) {

    }

    @FXML
    void clearButtonOnAction(ActionEvent event) {
        clearField();
    }

    private void clearField() {
//        generateId();
        cmbCustomerId.setValue("");
        cmbItemId.setValue("");
        txtCustomerName.clear();
        txtItemDescription.clear();
        lblQtyOnHand.setText("");
        lblUnitPrice.setText("");
        txtQty.clear();
        txtSearch.clear();
    }

    @FXML
    void placeOrderButtonOnAction(ActionEvent event) {

    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {

    }

    private void generateId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("Select id FROM orders ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = psTm.executeQuery();
            if (resultSet.next()) {
                int num = Integer.parseInt(resultSet.getString(1).split("[S]")[1]);
                num++;
                lblOrderId.setText(String.format("S%03d", num));
            } else {
                lblOrderId.setText("S001");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
