package edu.icet.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    private String orderId;
    private String date;
    private double total;
    private String empId;
    private String Id;
}
