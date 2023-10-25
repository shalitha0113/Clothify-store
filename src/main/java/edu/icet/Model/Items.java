package edu.icet.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Items {
    private String itemCode;
    private String name;
    private String supId;
    private String itemType;
    private double unitPrice;
    private int qtyHand;
}
