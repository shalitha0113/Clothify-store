package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.icet.Model.Customer;
import edu.icet.Model.tm.CustomerTm;
import edu.icet.db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class CustomerFormController implements Initializable {
    public Button btnCusSearch;

    public Button btnCusSave;
    public Button btnCusClear;
    public JFXTextField txtCusSearch;
    public JFXTreeTableView<CustomerTm> tblCustomer;
    public TreeTableColumn colId;
    public TreeTableColumn colName;
    public TreeTableColumn colAddress;
    public TreeTableColumn colTp;
    public TreeTableColumn colEmail;
    public TreeTableColumn colOption;
    public JFXTextField txtCusName;
    public JFXTextField txtCusTp;
    public JFXTextField txtCusAddress;
    public JFXTextField txtCusEmail;
    public Label lblCusId;
    public Button btnCusUpdate;

    public void btnCusSearchOnAction(ActionEvent actionEvent) {


    }



    public void btnCusSaveOnAction(ActionEvent actionEvent) {
        Customer customer=new Customer(
                lblCusId.getText(),
                txtCusName.getText(),
                txtCusAddress.getText(),
                txtCusTp.getText(),
                txtCusEmail.getText()
        );
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement psTm=connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?)");
            psTm.setString(1,customer.getId());
            psTm.setString(2,customer.getName());
            psTm.setString(3,customer.getAddress());
            psTm.setString(4,customer.getTp());
            psTm.setString(5,customer.getEmail());

            if (psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved..!").show();
                clearField();
                loadTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnCusUpdateOnAction(ActionEvent actionEvent) {
        Customer customer=new Customer(
                lblCusId.getText(),
                txtCusName.getText(),
                txtCusAddress.getText(),
                txtCusTp.getText(),
                txtCusEmail.getText()
        );
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("UPDATE customer SET name=? , address=?, tp=?, email=? WHERE id=?");
            psTm.setString(1,customer.getName());
            psTm.setString(2,customer.getAddress());
            psTm.setString(3,customer.getTp());
            psTm.setString(4,customer.getEmail());
            psTm.setString(5,customer.getId());
            if(psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Details Updated..!").show();
                clearField();
                loadTable();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Something went wrong..!").show();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnCusClearOnAction(ActionEvent actionEvent) {
        clearField();
    }

    private void clearField() {
        generateId();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusTp.clear();
        txtCusEmail.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
        colTp.setCellValueFactory(new TreeItemPropertyValueFactory<>("tp"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        generateId();
        loadTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue!=null){
                setData(newValue);
            }
        });

        txtCusSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tblCustomer.setPredicate(new Predicate<TreeItem<CustomerTm>>() {
                    @Override
                    public boolean test(TreeItem<CustomerTm> customerTmTreeItem) {
                        boolean flag = customerTmTreeItem.getValue().getId().contains(newValue) ||
                                customerTmTreeItem.getValue().getName().contains(newValue) ||
                                customerTmTreeItem.getValue().getAddress().contains(newValue);

                        return flag;
                    }
                });
            }
        });

    }


    private void setData(TreeItem<CustomerTm> value) {
        lblCusId.setText(value.getValue().getId());
        txtCusName.setText(value.getValue().getName());
        txtCusAddress.setText(value.getValue().getAddress());
        txtCusTp.setText(value.getValue().getTp());
        txtCusEmail.setText(value.getValue().getEmail());

    }

    private void loadTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        try {
            List<Customer> list=new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm=connection.prepareStatement("SELECT * from Customer");
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()){
                list.add(new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }

            for (Customer customer:list){
                JFXButton btn=new JFXButton("Delete");
                btn.setBackground(Background.fill(Color.rgb(227,92,92)));
                btn.setTextFill(Color.rgb(255,255,255));
                btn.setStyle("-fx-font-weight: BOLD");


                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst= connection.prepareStatement("DELETE FROM customer WHERE id=?");
                        pst.setString(1,customer.getId());
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + customer.getId() + " customer ? ", ButtonType.YES, ButtonType.NO).showAndWait();
                        if (buttonType.get() == ButtonType.YES){
                            if (pst.executeUpdate()>0){
                                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted..!").show();
                                loadTable();
                                generateId();
                            }else{
                                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                });

                tmList.add(new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getTp(),
                        customer.getEmail(),
                        btn
                ));

            }
            TreeItem<CustomerTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblCustomer.setRoot(treeItem);
            tblCustomer.setShowRoot(false);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void generateId() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("Select id FROM customer ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[C]")[1]);
                num++;
                lblCusId.setText(String.format("C%03d",num));
            }else {
                lblCusId.setText("C001");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
