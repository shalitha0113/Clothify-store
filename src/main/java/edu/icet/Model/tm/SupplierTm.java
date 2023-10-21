package edu.icet.Model.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SupplierTm extends RecursiveTreeObject<SupplierTm> {
    private String supId;
    private String supName;
    private String supAddress;
    private String supTp;
    private String supEmail;
    private JFXButton btn;
}
