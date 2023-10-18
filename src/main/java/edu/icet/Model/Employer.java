package edu.icet.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employer {
    private String empId;
    private String name;
    private String nic;
    private String dob;
    private String address;
    private String position;
    private String bankAccountNo;
    private String bank;
    private String contactNo;
}
