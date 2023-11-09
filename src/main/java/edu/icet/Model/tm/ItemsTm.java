package edu.icet.Model.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ItemsTm  extends RecursiveTreeObject<ItemsTm> {
    private String itemCode;
    private String name;
    private String supId;
    private String itemType;
    private double unitPrice;
    private int qtyHand;
}
