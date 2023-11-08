package com.pashonokk.marketplace.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String username;
    private String phoneNumber;
    private byte[] photo;
}
