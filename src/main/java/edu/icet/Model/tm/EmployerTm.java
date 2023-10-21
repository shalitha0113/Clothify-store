package edu.icet.Model.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployerTm extends RecursiveTreeObject<EmployerTm> {
    private String empId;
    private String name;
    private String nic;
    private LocalDate dob;
    private String address;
    private String position;
    private String bankAccountNo;
    private String bank;
    private String contactNo;
    private JFXButton btn;
}
