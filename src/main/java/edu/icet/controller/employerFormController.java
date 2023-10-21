package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.icet.Model.Customer;
import edu.icet.Model.Employer;
import edu.icet.Model.tm.CustomerTm;
import edu.icet.Model.tm.EmployerTm;
import edu.icet.db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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


public class employerFormController implements Initializable {

    public Label lblEmpId;
    public TreeTableColumn colEmpOption;
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
    private JFXTreeTableView<EmployerTm> tblEmployer;

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
        clearField();
    }

    @FXML
    void btnEmpSaveOnAction(ActionEvent event) {
        Employer employer=new Employer(
                lblEmpId.getText(),
                txtEmpName.getText(),
                txtEmpNic.getText(),
                txtEmpDob.getValue(),
                txtEmpAddress.getText(),
                txtEmpPosition.getText(),
                txtEmpBank.getText(),
                txtEmpAcc.getText(),
                txtEmpTp.getText()
        );
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement psTm=connection.prepareStatement("INSERT INTO employer VALUES(?,?,?,?,?,?,?,?,?)");
            psTm.setString(1,employer.getEmpId());
            psTm.setString(2,employer.getName());
            psTm.setString(3,employer.getNic());
            psTm.setString(4,String.valueOf(employer.getDob()));
            psTm.setString(5,employer.getAddress());
            psTm.setString(6,employer.getPosition());
            psTm.setString(7,employer.getBankAccountNo());
            psTm.setString(8,employer.getBank());
            psTm.setString(9,employer.getContactNo());

            if (psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Employer Saved..!").show();
                clearField();
                loadEmpTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnEmpUpdateOnAction(ActionEvent event) {
        Employer employer=new Employer(
                lblEmpId.getText(),
                txtEmpName.getText(),
                txtEmpNic.getText(),
                txtEmpDob.getValue(),
                txtEmpAddress.getText(),
                txtEmpPosition.getText(),
                txtEmpBank.getText(),
                txtEmpAcc.getText(),
                txtEmpTp.getText()
        );
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("UPDATE employer SET name=?, nic=?, dob=?, address=?, position=?, bank=?,  bankAccountNo=?, contactNo=? WHERE empId=?");
            psTm.setString(1,employer.getEmpId());
            psTm.setString(2,employer.getName());
            psTm.setString(3,employer.getNic());
            psTm.setString(4,String.valueOf(employer.getDob()));
            psTm.setString(5,employer.getAddress());
            psTm.setString(6,employer.getPosition());
            psTm.setString(7,employer.getBankAccountNo());
            psTm.setString(8,employer.getBank());
            psTm.setString(9,employer.getContactNo());
            if(psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Employer Details Updated..!").show();
                clearField();
                loadEmpTable();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Something went wrong..!").show();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearField() {
        generateEmpId();
        txtEmpName.clear();
        txtEmpNic.clear();
        txtEmpDob.setValue(null);
        txtEmpAddress.clear();
        txtEmpPosition.clear();
        txtEmpAcc.clear();
        txtEmpBank.clear();
        txtEmpTp.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmpId.setCellValueFactory(new TreeItemPropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colEmpNic.setCellValueFactory(new TreeItemPropertyValueFactory<>("nic"));
        colEmpAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
        colEmpDob.setCellValueFactory(new TreeItemPropertyValueFactory<>("dob"));
        colEmpTp.setCellValueFactory(new TreeItemPropertyValueFactory<>("contactNo"));
        colEmpPosition.setCellValueFactory(new TreeItemPropertyValueFactory<>("position"));
        colEmpBank.setCellValueFactory(new TreeItemPropertyValueFactory<>("bank"));
        colEmpAccNo.setCellValueFactory(new TreeItemPropertyValueFactory<>("bankAccountNo"));
        colEmpOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        generateEmpId();
        loadEmpTable();

        tblEmployer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue!=null){
                setData(newValue);
            }
        });

        txtEmpSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tblEmployer.setPredicate(new Predicate<TreeItem<EmployerTm>>() {
                    @Override
                    public boolean test(TreeItem<EmployerTm> employerTmTreeItem) {
                        boolean flag = employerTmTreeItem.getValue().getEmpId().contains(newValue) ||
                                employerTmTreeItem.getValue().getName().contains(newValue);

                        return flag;
                    }
                });
            }
        });
    }

    private void setData(TreeItem<EmployerTm> value) {
        lblEmpId.setText(value.getValue().getEmpId());
        txtEmpName.setText(value.getValue().getName());
        txtEmpNic.setText(value.getValue().getNic());
        txtEmpAddress.setText(value.getValue().getAddress());
        txtEmpDob.setValue(value.getValue().getDob());
        txtEmpTp.setText(value.getValue().getContactNo());
        txtEmpPosition.setText(value.getValue().getPosition());
        txtEmpBank.setText(value.getValue().getBank());
        txtEmpAcc.setText(value.getValue().getBankAccountNo());
    }

    private void loadEmpTable() {

        ObservableList<EmployerTm> tmEmpList = FXCollections.observableArrayList();

        try {
            List<Employer> list=new ArrayList<>();
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT * from employer");
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()){
                list.add(new Employer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                ));

            }

            for (Employer employer:list){
                JFXButton btn=new JFXButton("Delete");
                btn.setBackground(Background.fill(Color.rgb(227,92,92)));
                btn.setTextFill(Color.rgb(255,255,255));
                btn.setStyle("-fx-font-weight: BOLD");


                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst= connection.prepareStatement("DELETE FROM employer WHERE id=?");
                        pst.setString(1,employer.getEmpId());
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + employer.getEmpId() + " employer ? ", ButtonType.YES, ButtonType.NO).showAndWait();
                        if (buttonType.get() == ButtonType.YES){
                            if (pst.executeUpdate()>0){
                                new Alert(Alert.AlertType.INFORMATION,"Employer Deleted..!").show();
                                loadEmpTable();
                                generateEmpId();
                            }else{
                                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                });

                tmEmpList.add(new EmployerTm(
                        employer.getEmpId(),
                        employer.getName(),
                        employer.getNic(),
                        employer.getDob(),
                        employer.getAddress(),
                        employer.getPosition(),
                        employer.getBank(),
                        employer.getBankAccountNo(),
                        employer.getContactNo(),
                        btn
                ));

            }
            TreeItem<EmployerTm> treeItem = new RecursiveTreeItem<>(tmEmpList, RecursiveTreeObject::getChildren);
            tblEmployer.setRoot(treeItem);
            tblEmployer.setShowRoot(false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
