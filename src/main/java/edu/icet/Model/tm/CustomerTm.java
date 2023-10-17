package edu.icet.Model.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTm extends RecursiveTreeObject<CustomerTm> {
    private String id;
    private String name;
    private String address;
    private String tp;
    private String email;
    private JFXButton btn;
}
