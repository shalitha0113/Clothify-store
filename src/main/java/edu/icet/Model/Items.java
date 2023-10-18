package edu.icet.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Items {
    private String itemCode;
    private String supId;
    private String description;
    private double unitPrice;
    private int qtyHand;
    private double sellPrice;
    private double buyPrice;
    private String categoryId;

}
