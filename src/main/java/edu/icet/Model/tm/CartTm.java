package edu.icet.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartTm {
    private String itemId;
    private String itemDesc;
    private double unitPrice;
    private int qtyOnHand;
    private double amount;
    private JFXButton btn;

}
