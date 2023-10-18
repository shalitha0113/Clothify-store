package edu.icet.controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import edu.icet.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class employerFormController implements Initializable {

    public Label lblEmpId;
    @FXML
    private Button btnEmpClear;

    @FXML
    private Button btnEmpSave;

    @FXML
    private Button btnEmpUpdate;

    @FXML
    private TreeTableColumn<?, ?> colEmpAccNo;

    @FXML
    private TreeTableColumn<?, ?> colEmpAddress;

    @FXML
    private TreeTableColumn<?, ?> colEmpBank;

    @FXML
    private TreeTableColumn<?, ?> colEmpDob;

    @FXML
    private TreeTableColumn<?, ?> colEmpId;

    @FXML
    private TreeTableColumn<?, ?> colEmpName;

    @FXML
    private TreeTableColumn<?, ?> colEmpNic;

    @FXML
    private TreeTableColumn<?, ?> colEmpPosition;

    @FXML
    private TreeTableColumn<?, ?> colEmpTp;

    @FXML
    private JFXTreeTableView<?> tblEmployer;

    @FXML
    private JFXTextField txtEmpAcc;

    @FXML
    private JFXTextField txtEmpAddress;

    @FXML
    private JFXTextField txtEmpBank;

    @FXML
    private DatePicker txtEmpDob;

    @FXML
    private JFXTextField txtEmpName;

    @FXML
    private JFXTextField txtEmpNic;

    @FXML
    private JFXTextField txtEmpPosition;

    @FXML
    private JFXTextField txtEmpSearch;

    @FXML
    private JFXTextField txtEmpTp;

    @FXML
    void btnEmpClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmpSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmpUpdateOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateEmpId();
        loadEmpTable();
    }

    private void loadEmpTable() {

    }

    private void generateEmpId() {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("Select empId FROM Employer ORDER BY empId DESC LIMIT 1");
            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[E]")[1]);
                num++;
                lblEmpId.setText(String.format("E%03d",num));
            }else {
                lblEmpId.setText("E001");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
