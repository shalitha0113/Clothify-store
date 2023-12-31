package edu.icet.Model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employer {
    private String empId;
    private String name;
    private String nic;
    private LocalDate dob;
    private String address;
    private String position;
    private String bankAccountNo;
    private String bank;
    private String contactNo;
}
