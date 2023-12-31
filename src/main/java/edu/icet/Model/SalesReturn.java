package edu.icet.Model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SalesReturn {
    private String returnId;
    private String orderId;
    private String date;
    private double total;
}
