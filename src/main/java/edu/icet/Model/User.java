package edu.icet.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String username;
    private String password;
    private String userEmail;
    private String userType = "Default";
}
