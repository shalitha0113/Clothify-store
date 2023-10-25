package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.icet.Model.Customer;
import edu.icet.Model.Supplier;
import edu.icet.Model.tm.CustomerTm;
import edu.icet.Model.tm.SupplierTm;
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


public class SuppliersFormController implements Initializable {

    public TreeTableColumn colId;
    public TreeTableColumn colName;
    public TreeTableColumn colAddress;
    public TreeTableColumn colTp;
    public TreeTableColumn colEmail;
    public TreeTableColumn colOption;

    public Button btnCusUpdate;
    public JFXTextField txtSupName;
    public JFXTextField txtSupTp;
    public JFXTextField txtSupAddress;
    public JFXTextField txtSupEmail;
    public Label lblSupId;
    public Button btnSupSave;
    public JFXTextField txtSupSearch;
    public JFXTreeTableView<SupplierTm> tblSupplier;
    public Button btnCusClear;


    private void clearField() {
        generateId();
        txtSupName.clear();
        txtSupAddress.clear();
        txtSupTp.clear();
        txtSupEmail.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("supId"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("supName"));
        colAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("supAddress"));
        colTp.setCellValueFactory(new TreeItemPropertyValueFactory<>("supTp"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("supEmail"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        generateId();
        loadTable();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue!=null){
                setData(newValue);
            }
        });

        txtSupSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tblSupplier.setPredicate(new Predicate<TreeItem<SupplierTm>>() {
                    @Override
                    public boolean test(TreeItem<SupplierTm> supplierTmTreeItem) {
                        boolean flag = supplierTmTreeItem.getValue().getSupId().contains(newValue) ||
                                supplierTmTreeItem.getValue().getSupName().contains(newValue) ||
                                supplierTmTreeItem.getValue().getSupAddress().contains(newValue);

                        return flag;
                    }
                });
            }
        });

    }


    private void setData(TreeItem<SupplierTm> value) {
        lblSupId.setText(value.getValue().getSupId());
        txtSupName.setText(value.getValue().getSupName());
        txtSupAddress.setText(value.getValue().getSupAddress());
        txtSupTp.setText(value.getValue().getSupTp());
        txtSupEmail.setText(value.getValue().getSupEmail());

    }

    private void loadTable() {
        ObservableList<SupplierTm> tmList = FXCollections.observableArrayList();

        try {
            List<Supplier> list=new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm=connection.prepareStatement("SELECT * from Supplier");
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()){
                list.add(new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }

            for (Supplier supplier:list){
                JFXButton btn=new JFXButton("Delete");
                btn.setBackground(Background.fill(Color.rgb(227,92,92)));
                btn.setTextFill(Color.rgb(255,255,255));
                btn.setStyle("-fx-font-weight: BOLD");


                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst= connection.prepareStatement("DELETE FROM supplier WHERE id=?");
                        pst.setString(1,supplier.getSupId());
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + supplier.getSupId() + " supplier ? ", ButtonType.YES, ButtonType.NO).showAndWait();
                        if (buttonType.get() == ButtonType.YES){
                            if (pst.executeUpdate()>0){
                                new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted..!").show();
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

                tmList.add(new SupplierTm(
                        supplier.getSupId(),
                        supplier.getSupName(),
                        supplier.getSupAddress(),
                        supplier.getSupTp(),
                        supplier.getSupEmail(),
                        btn
                ));

            }
            TreeItem<SupplierTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblSupplier.setRoot(treeItem);
            tblSupplier.setShowRoot(false);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void generateId() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement psTm=connection.prepareStatement("Select supId FROM supplier ORDER BY supId DESC LIMIT 1");
            ResultSet resultSet = psTm.executeQuery();
            if(resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[S]")[1]);
                num++;
                lblSupId.setText(String.format("S%03d",num));
            }else {
                lblSupId.setText("S001");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnSupUpdateOnAction(ActionEvent actionEvent) {
        Supplier supplier=new Supplier(
                lblSupId.getText(),
                txtSupName.getText(),
                txtSupAddress.getText(),
                txtSupTp.getText(),
                txtSupEmail.getText()
        );
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("UPDATE supplier SET supName=? , supAddress=?, supTp=?, supEmail=? WHERE supId=?");
            psTm.setString(1,supplier.getSupId());
            psTm.setString(2,supplier.getSupName());
            psTm.setString(3,supplier.getSupAddress());
            psTm.setString(4,supplier.getSupTp());
            psTm.setString(5,supplier.getSupEmail());

            if(psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Details Updated..!").show();
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

    public void btnSupSaveOnAction(ActionEvent actionEvent) {
        Supplier supplier=new Supplier(
                lblSupId.getText(),
                txtSupName.getText(),
                txtSupAddress.getText(),
                txtSupTp.getText(),
                txtSupEmail.getText()
        );

        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement psTm=connection.prepareStatement("INSERT INTO supplier VALUES(?,?,?,?,?)");
            psTm.setString(1,supplier.getSupId());
            psTm.setString(2,supplier.getSupName());
            psTm.setString(3,supplier.getSupAddress());
            psTm.setString(4,supplier.getSupTp());
            psTm.setString(5,supplier.getSupEmail());

            if (psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Saved..!").show();
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

    public void btnSupClearOnAction(ActionEvent actionEvent) {
        clearField();
    }

    public List<String> getSupplierId() throws SQLException {
        List<String> ids = new ArrayList<>();
        ResultSet rst = null;
        try {
            rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier").executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );

        }
        return ids;
    }
}
