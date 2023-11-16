package edu.icet.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.icet.Model.Customer;
import edu.icet.Model.Items;
import edu.icet.Model.Supplier;
import edu.icet.Model.tm.CustomerTm;
import edu.icet.Model.tm.ItemsTm;
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


public class ItemFormController implements Initializable {

    public JFXTextField txtItemSearch;

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
    public JFXTextField txtSupName;

    public JFXTreeTableView<ItemsTm> tblItems;

    public void btnItemSaveOnAction(ActionEvent actionEvent) {
        Items items = new Items(
                lblItemId.getText(),
                txtItemName.getText(),
                (String) cmbSupplierId.getValue(),
                (String) comItemType.getValue(),
                Double.parseDouble(txtItemUnitPrice.getText()),
                Integer.parseInt(txtItemQty.getText())
        );

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO items VALUES(?,?,?,?,?,?)");
            psTm.setString(1, items.getItemCode());
            psTm.setString(2, items.getName());
            psTm.setString(3, items.getItemType());
            psTm.setDouble(4, items.getUnitPrice());
            psTm.setInt(5, items.getQtyHand());
            psTm.setString(6, items.getSupId());

            if (psTm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Items Saved..!").show();
                clearField();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong..!").show();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnItemUpdateOnAction(ActionEvent actionEvent) {
        Items items = new Items(
                lblItemId.getText(),
                txtItemName.getText(),
                (String) cmbSupplierId.getValue(),
                (String) comItemType.getValue(),
                Double.parseDouble(txtItemUnitPrice.getText()),
                Integer.parseInt(txtItemQty.getText())
        );

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("UPDATE items SET name=? , itemType=?, unitPrice=?, qtyOnHand=?, supId=? WHERE itemCode=?");
            psTm.setString(1, items.getItemCode());
            psTm.setString(2, items.getName());
            psTm.setString(3, items.getItemType());
            psTm.setDouble(4, items.getUnitPrice());
            psTm.setInt(5, items.getQtyHand());
            psTm.setString(6, items.getSupId());

            if (psTm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Items Details Updated..!").show();
                clearField();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong..!").show();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void clearField() {
        generateId();
        txtItemName.clear();
        txtItemQty.clear();
        txtItemUnitPrice.clear();
        txtItemSearch.clear();
        cmbSupplierId.setValue("");
        comItemType.setValue(null);
        txtSupName.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colSupId.setCellValueFactory(new TreeItemPropertyValueFactory<>("supId"));
        colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemType"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new TreeItemPropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        generateId();
        setItems(comItemType);
        loadSuppliersId();
        loadTable();

        cmbSupplierId.setOnAction(actionEvent -> {
            setSupplierName();
        });

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
            }
        });

        txtItemSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tblItems.setPredicate(new Predicate<TreeItem<ItemsTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemsTm> supplierTmTreeItem) {
                        boolean flag = supplierTmTreeItem.getValue().getItemCode().contains(newValue) ||
                                supplierTmTreeItem.getValue().getName().contains(newValue) ||
                                supplierTmTreeItem.getValue().getItemType().contains(newValue);
                        return flag;
                    }
                });
            }
        });


    }

    private void setData(TreeItem<ItemsTm> value) {
        lblItemId.setText(value.getValue().getItemCode());
        txtItemName.setText(value.getValue().getName());
        cmbSupplierId.setValue(value.getValue().getSupId());
        comItemType.setValue(value.getValue().getItemType());
        txtItemQty.setText(String.valueOf(value.getValue().getQtyOnHand()));
        txtItemUnitPrice.setText(String.valueOf(value.getValue().getUnitPrice()));
    }

    private void setSupplierName() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT supName FROM supplier WHERE supId=?");
            psTm.setString(1, cmbSupplierId.getValue().toString());
            ResultSet resultSet = psTm.executeQuery();


            if (resultSet.next()) {
                txtSupName.setText(resultSet.getString(1));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSuppliersId() {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT supId FROM supplier");
            ResultSet resultSet = psTm.executeQuery();

            ObservableList<String> supplierIds = FXCollections.observableArrayList();

            while (resultSet.next()) {
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
        ObservableList<String> observableList = FXCollections.observableArrayList("Gents", "Ladies", "Kids");
        comItemType.setItems(observableList);
    }


    private void loadTable() {
        ObservableList<ItemsTm> tmList = FXCollections.observableArrayList();

        try {
            List<Items> list = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT * from Items");
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()) {
                list.add(new Items(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(6),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                ));
            }

            for (Items items : list) {
                JFXButton btn = new JFXButton("Delete");
                btn.setBackground(Background.fill(Color.rgb(227, 92, 92)));
                btn.setTextFill(Color.rgb(255, 255, 255));
                btn.setStyle("-fx-font-weight: BOLD");


                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst = connection.prepareStatement("DELETE FROM items WHERE itemCode=?");
                        pst.setString(1, items.getItemCode());
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + items.getItemCode() + " items? ", ButtonType.YES, ButtonType.NO).showAndWait();
                        if (buttonType.get() == ButtonType.YES) {
                            if (pst.executeUpdate() > 0) {
                                new Alert(Alert.AlertType.INFORMATION, "Items Deleted..!").show();
                                loadTable();
                                generateId();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Something went wrong..!").show();
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                });

                tmList.add(new ItemsTm(
                        items.getItemCode(),
                        items.getName(),
                        items.getSupId(),
                        items.getItemType(),
                        items.getUnitPrice(),
                        items.getQtyHand(),
                        btn
                ));

            }
            TreeItem<ItemsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblItems.setRoot(treeItem);
            tblItems.setShowRoot(false);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("Select itemCode FROM items ORDER BY itemCode DESC LIMIT 1");
            ResultSet resultSet = psTm.executeQuery();
            if (resultSet.next()) {
                int num = Integer.parseInt(resultSet.getString(1).split("[P]")[1]);
                num++;
                lblItemId.setText(String.format("P%03d", num));
            } else {
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


}
