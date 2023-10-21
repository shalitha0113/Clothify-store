package edu.icet.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Supplier {
    private String supId;
    private String supName;
    private String supAddress;
    private String supTp;
    private String supEmail;
}
