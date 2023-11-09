package edu.icet.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.icet.Model.Customer;
import edu.icet.Model.Items;
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


public class ItemFormController implements Initializable {


    public JFXTextField txtItemSearch;
    public JFXTreeTableView tblItems;
    public TreeTableColumn colId;
    public TreeTableColumn colName;
    public TreeTableColumn colSupId;
    public TreeTableColumn colType;
    public TreeTableColumn colQtyOnHand;
    public TreeTableColumn colUnitPrice;
    public TreeTableColumn colOption;
    public JFXTextField txtItemName;
    public JFXTextField txtItemQty;
    public JFXTextField txtItemUnitPrice;
    public Label lblItemId;
    public Button btnItemUpdate;
    public Button btnItemSave;
    public Button btnItemClear;
    public JFXComboBox comItemType;
    public JFXComboBox cmbSupplierId;

    public void btnItemSaveOnAction(ActionEvent actionEvent) {

    }


    public void btnItemUpdateOnAction(ActionEvent actionEvent) {


    }


    private void clearField() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateId();
        setItems(comItemType);
        loadSuppliersId();


    }

    private void loadSuppliersId() {
        Connection connection=null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm=connection.prepareStatement("SELECT supId FROM supplier");
            ResultSet resultSet = psTm.executeQuery();

            ObservableList<String> supplierIds=FXCollections.observableArrayList();

            while (resultSet.next()){
                supplierIds.add(resultSet.getString(1));
            }

            cmbSupplierId.setItems(supplierIds);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setItems(JFXComboBox<String> comItemType) {
        ObservableList<String> observableList = FXCollections.observableArrayList("Gents","Ladies","Kids");
        comItemType.setItems(observableList);
    }


    private void loadTable() {


    }

    private void generateId() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement psTm=connection.prepareStatement("Select itemCode FROM items ORDER BY itemCode DESC LIMIT 1");
            ResultSet resultSet = psTm.executeQuery();
            if(resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[P]")[1]);
                num++;
                lblItemId.setText(String.format("P%03d",num));
            }else {
                lblItemId.setText("P001");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    public void btnItemClearOnAction(ActionEvent actionEvent) {
        clearField();
    }


    public void comItemTypeOnAction(ActionEvent actionEvent) {

    }


}
