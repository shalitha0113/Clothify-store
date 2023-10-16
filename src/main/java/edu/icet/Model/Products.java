package edu.icet.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Products {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyHand;

}
