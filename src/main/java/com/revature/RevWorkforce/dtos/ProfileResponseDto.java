package com.revature.RevWorkforce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private Integer accountId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String birthday;
}
